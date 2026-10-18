package hu.norbisquest.nagbase.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ResourceScopeTest {

    @Test
    public void emptyScopeCanBeDestroyedRepeatedly() {
        ResourceScope scope = new ResourceScope();

        scope.destroy();
        scope.destroy();

        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void destroysASingleRegisteredResource() {
        ResourceScope scope = new ResourceScope();
        RecordingResource resource = new RecordingResource("only", new ArrayList<>());
        scope.register(resource);

        scope.destroy();

        assertEquals(1, resource.destroyCount);
    }

    @Test
    public void destroysResourcesInReverseRegistrationOrder() {
        ResourceScope scope = new ResourceScope();
        List<String> order = new ArrayList<>();
        scope.register(new RecordingResource("first", order));
        scope.register(new RecordingResource("second", order));
        scope.register(new RecordingResource("third", order));

        scope.destroy();

        assertEquals(Arrays.asList("third", "second", "first"), order);
    }

    @Test
    public void repeatedDestroyDoesNotDestroyResourcesAgain() {
        ResourceScope scope = new ResourceScope();
        RecordingResource resource = new RecordingResource("resource", new ArrayList<>());
        scope.register(resource);

        scope.destroy();
        scope.destroy();

        assertEquals(1, resource.destroyCount);
    }

    @Test
    public void cleanupContinuesAfterAResourceThrowsAndRethrowsFirstFailure() {
        ResourceScope scope = new ResourceScope();
        List<String> order = new ArrayList<>();
        RecordingResource first = new RecordingResource("first", order);
        RecordingResource earlierFailure = new RecordingResource("earlier-failure", order);
        RecordingResource lastFailure = new RecordingResource("last-failure", order);
        RuntimeException firstCleanupFailure = new RuntimeException("first cleanup failure");
        RuntimeException secondCleanupFailure = new RuntimeException("second cleanup failure");
        earlierFailure.failure = secondCleanupFailure;
        lastFailure.failure = firstCleanupFailure;
        scope.register(first);
        scope.register(earlierFailure);
        scope.register(lastFailure);

        RuntimeException thrown = assertThrows(RuntimeException.class, scope::destroy);

        assertSame(firstCleanupFailure, thrown);
        assertEquals(Arrays.asList("last-failure", "earlier-failure", "first"), order);
        assertEquals(1, first.destroyCount);
        assertEquals(1, earlierFailure.destroyCount);
        assertEquals(1, lastFailure.destroyCount);
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void cleanupContinuesAfterAnErrorAndRethrowsTheSameError() {
        ResourceScope scope = new ResourceScope();
        List<String> order = new ArrayList<>();
        RecordingResource first = new RecordingResource("first", order);
        RecordingResource failing = new RecordingResource("failing", order);
        RecordingResource last = new RecordingResource("last", order);
        AssertionError failure = new AssertionError("expected cleanup error");
        failing.failure = failure;
        scope.register(first);
        scope.register(failing);
        scope.register(last);

        AssertionError thrown = assertThrows(AssertionError.class, scope::destroy);

        assertSame(failure, thrown);
        assertEquals(Arrays.asList("last", "failing", "first"), order);
        assertEquals(1, first.destroyCount);
        assertEquals(1, failing.destroyCount);
        assertEquals(1, last.destroyCount);
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void registrationAfterDestructionIsRejected() {
        ResourceScope scope = new ResourceScope();
        scope.destroy();

        assertThrows(IllegalStateException.class,
                () -> scope.register(new RecordingResource("late", new ArrayList<>())));
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void nullRegistrationIsRejected() {
        ResourceScope scope = new ResourceScope();

        assertThrows(IllegalArgumentException.class, () -> scope.register(null));
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void alreadyDestroyedResourceIsRejected() {
        ResourceScope scope = new ResourceScope();
        RecordingResource resource = new RecordingResource("destroyed", new ArrayList<>());
        resource.destroy();

        assertThrows(IllegalArgumentException.class, () -> scope.register(resource));
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void scopeCannotRegisterItself() {
        ResourceScope scope = new ResourceScope();

        assertThrows(IllegalArgumentException.class, () -> scope.register(scope));
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void registeringTheSameResourceInstanceTwiceIsRejected() {
        ResourceScope scope = new ResourceScope();
        RecordingResource resource = new RecordingResource("resource", new ArrayList<>());
        scope.register(resource);

        assertThrows(IllegalArgumentException.class, () -> scope.register(resource));
        assertEquals(1, scope.resourceCount());
    }

    @Test
    public void distinctButEqualResourcesAreOwnedAndDestroyedSeparately() {
        ResourceScope scope = new ResourceScope();
        List<String> order = new ArrayList<>();
        RecordingResource first = new RecordingResource("equal", order);
        RecordingResource second = new RecordingResource("equal", order);
        assertTrue(first.equals(second));

        scope.register(first);
        scope.register(second);
        scope.destroy();

        assertEquals(1, first.destroyCount);
        assertEquals(1, second.destroyCount);
        assertEquals(Arrays.asList("equal", "equal"), order);
    }

    @Test
    public void scopeIsClosedBeforeResourceCallbacksRun() {
        ResourceScope scope = new ResourceScope();
        RecordingResource resource = new RecordingResource("resource", new ArrayList<>());
        resource.onDestroy = () -> assertThrows(IllegalStateException.class,
                () -> scope.register(new RecordingResource("reentrant", new ArrayList<>())));
        scope.register(resource);

        scope.destroy();

        assertEquals(1, resource.destroyCount);
        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void destroyedScopeReleasesRegisteredReferences() {
        ResourceScope scope = new ResourceScope();
        scope.register(new RecordingResource("first", new ArrayList<>()));
        scope.register(new RecordingResource("second", new ArrayList<>()));

        scope.destroy();

        assertEquals(0, scope.resourceCount());
    }

    @Test
    public void scopeImplementsDestroyableAndCanOwnAChildScope() {
        assertTrue("ResourceScope must implement Destroyable",
                Destroyable.class.isAssignableFrom(ResourceScope.class));

        ResourceScope parent = new ResourceScope();
        ResourceScope child = new ResourceScope();
        RecordingResource childResource = new RecordingResource("child", new ArrayList<>());
        child.register(childResource);
        parent.register(child);

        parent.destroy();
        parent.destroy();

        assertEquals(1, childResource.destroyCount);
        assertEquals(0, child.resourceCount());
    }

    private static final class RecordingResource implements Destroyable {
        private final String name;
        private final List<String> destructionOrder;
        private int destroyCount;
        private Throwable failure;
        private Runnable onDestroy;

        private RecordingResource(String name, List<String> destructionOrder) {
            this.name = name;
            this.destructionOrder = destructionOrder;
        }

        @Override
        public void destroy() {
            destroyCount++;
            destructionOrder.add(name);
            if (onDestroy != null) {
                onDestroy.run();
            }
            if (failure != null) {
                if (failure instanceof RuntimeException) {
                    throw (RuntimeException) failure;
                }
                if (failure instanceof Error) {
                    throw (Error) failure;
                }
                throw new RuntimeException(failure);
            }
        }

        @Override
        public boolean isDestroyed() {
            return destroyCount > 0;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RecordingResource)) {
                return false;
            }
            RecordingResource that = (RecordingResource) other;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
