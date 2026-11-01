package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.core.Destroyable;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

public class StagedScreenParentTest {

    @Test
    public void successfulCreationTransfersWholeScopeToStagedScreen() {
        FakeStagingContainer container = new FakeStagingContainer();
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingResource auxiliary = new RecordingResource();
        RecordingScreen screen = new RecordingScreen();
        parent.register(auxiliary);

        parent.onScreenCreated(screen);

        assertNotNull(callback.screen);
        assertNull(callback.failure);
        assertEquals(1, screen.attachCount);
        assertFalse(auxiliary.isDestroyed());
        assertFalse(screen.isDestroyed());

        callback.screen.activate();
        assertEquals(1, container.activateCount);
        assertTrue(container.attached);

        callback.screen.destroy();
        callback.screen.destroy();

        assertEquals(1, container.detachCount);
        assertEquals(1, container.clearCount);
        assertFalse(container.attached);
        assertEquals(1, auxiliary.destroyCount);
        assertEquals(1, screen.destroyCount);
    }

    @Test
    public void creationFailureDestroysEverythingRegisteredSoFar() {
        FakeStagingContainer container = new FakeStagingContainer();
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingResource first = new RecordingResource();
        RecordingResource second = new RecordingResource();
        parent.register(first);
        parent.register(second);
        RuntimeException failure = new RuntimeException("creation failed");

        parent.onScreenCreationFailed(failure);

        assertSame(failure, callback.failure);
        assertNull(callback.screen);
        assertEquals(1, container.clearCount);
        assertEquals(1, first.destroyCount);
        assertEquals(1, second.destroyCount);
        assertThrows(IllegalStateException.class,
                () -> parent.register(new RecordingResource()));
    }

    @Test
    public void attachFailureAlsoDestroysScreenAndEarlierResources() {
        FakeStagingContainer container = new FakeStagingContainer();
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingResource auxiliary = new RecordingResource();
        RecordingScreen screen = new RecordingScreen();
        screen.attachFailure = new RuntimeException("attach failed");
        parent.register(auxiliary);

        parent.onScreenCreated(screen);

        assertSame(screen.attachFailure, callback.failure);
        assertEquals(1, auxiliary.destroyCount);
        assertEquals(1, screen.destroyCount);
        assertEquals(1, container.clearCount);
    }

    @Test
    public void lateDifferentScreenIsDestroyedWithoutTouchingAcceptedScreen() {
        FakeStagingContainer container = new FakeStagingContainer();
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingScreen accepted = new RecordingScreen();
        RecordingScreen late = new RecordingScreen();

        parent.onScreenCreated(accepted);
        parent.onScreenCreated(late);

        assertFalse(accepted.isDestroyed());
        assertEquals(1, late.destroyCount);

        callback.screen.destroy();
        assertEquals(1, accepted.destroyCount);
    }

    @Test
    public void domCleanupFailureDoesNotSkipResourceDestruction() {
        FakeStagingContainer container = new FakeStagingContainer();
        container.detachFailure = new RuntimeException("detach failed");
        container.clearFailure = new RuntimeException("clear failed");
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingResource auxiliary = new RecordingResource();
        RecordingScreen screen = new RecordingScreen();
        parent.register(auxiliary);
        parent.onScreenCreated(screen);

        RuntimeException thrown = assertThrows(
                RuntimeException.class, callback.screen::destroy);

        assertSame(container.detachFailure, thrown);
        assertEquals(1, thrown.getSuppressed().length);
        assertSame(container.clearFailure, thrown.getSuppressed()[0]);
        assertEquals(1, auxiliary.destroyCount);
        assertEquals(1, screen.destroyCount);
        assertTrue(callback.screen.isDestroyed());
    }

    @Test
    public void failedCreationStillDestroysResourcesWhenDomClearFails() {
        FakeStagingContainer container = new FakeStagingContainer();
        container.clearFailure = new RuntimeException("clear failed");
        RecordingCallback callback = new RecordingCallback();
        StagedScreenParent parent = parent(container, callback);
        RecordingResource auxiliary = new RecordingResource();
        parent.register(auxiliary);
        RuntimeException failure = new RuntimeException("creation failed");

        parent.onScreenCreationFailed(failure);

        assertSame(failure, callback.failure);
        assertEquals(1, failure.getSuppressed().length);
        assertSame(container.clearFailure, failure.getSuppressed()[0]);
        assertEquals(1, auxiliary.destroyCount);
    }

    private StagedScreenParent parent(FakeStagingContainer container,
                                      RecordingCallback callback) {
        return new StagedScreenParent(container, context(), callback);
    }

    private ScreenContext context() {
        GameGUI gui = (GameGUI) Proxy.newProxyInstance(
                GameGUI.class.getClassLoader(),
                new Class<?>[]{GameGUI.class},
                (proxy, method, args) -> defaultValue(method.getReturnType()));
        ScreenNavigation navigation = new ScreenNavigation() {
            @Override
            public void navigateTo(ScreenId id) {
            }

            @Override
            public void navigateTo(ScreenId id,
                                   hu.norbisquest.nagbase.common.engine.TransitionEffect effect) {
            }
        };
        return new ScreenContext(gui, navigation);
    }

    private Object defaultValue(Class<?> type) {
        if (type == void.class || !type.isPrimitive()) {
            return null;
        }
        if (type == boolean.class) {
            return false;
        }
        if (type == char.class) {
            return '\0';
        }
        if (type == long.class) {
            return 0L;
        }
        if (type == float.class) {
            return 0.0f;
        }
        if (type == double.class) {
            return 0.0d;
        }
        if (type == byte.class) {
            return (byte) 0;
        }
        if (type == short.class) {
            return (short) 0;
        }
        return 0;
    }

    private static final class FakeStagingContainer
            implements StagedScreenParent.StagingContainer {
        private int activateCount;
        private int clearCount;
        private int detachCount;
        private boolean attached;
        private RuntimeException clearFailure;
        private RuntimeException detachFailure;

        @Override
        public FlowPanel getPanel() {
            return null;
        }

        @Override
        public void activate() {
            activateCount++;
            attached = true;
        }

        @Override
        public void clear() {
            clearCount++;
            if (clearFailure != null) {
                throw clearFailure;
            }
        }

        @Override
        public void detach() {
            detachCount++;
            attached = false;
            if (detachFailure != null) {
                throw detachFailure;
            }
        }
    }

    private static class RecordingResource implements Destroyable {
        protected int destroyCount;

        @Override
        public void destroy() {
            destroyCount++;
        }

        @Override
        public boolean isDestroyed() {
            return destroyCount > 0;
        }
    }

    private static final class RecordingScreen extends RecordingResource
            implements GameScreen {
        private int attachCount;
        private RuntimeException attachFailure;

        @Override
        public ScreenId getId() {
            return null;
        }

        @Override
        public void attach(ScreenContext context) {
            attachCount++;
            if (attachFailure != null) {
                throw attachFailure;
            }
        }

        @Override
        public boolean prepare() {
            return true;
        }

        @Override
        public boolean isReady() {
            return !isDestroyed();
        }

        @Override
        public void enter() {
        }

        @Override
        public void update(double timestamp) {
        }

        @Override
        public void setInputEnabled(boolean enabled) {
        }
    }

    private static final class RecordingCallback
            implements ScreenCreationCallback {
        private StagedScreen screen;
        private Throwable failure;

        @Override
        public void onSuccess(StagedScreen screen) {
            this.screen = screen;
        }

        @Override
        public void onFailure(Throwable cause) {
            failure = cause;
        }
    }
}
