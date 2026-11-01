package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.ResourceScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Exercises the complete non-GWT screen ownership chain with tracked resources. */
public class ScreenLifecycleIntegrationTest {

    private static final int RESOURCES_PER_SCREEN = 3;

    @Test
    public void repeatedNavigationKeepsOnlyCurrentScreenResourcesAlive() {
        Fixture fixture = new Fixture();

        for (int transition = 0; transition < 100; transition++) {
            TestId id = transition % 2 == 0 ? TestId.ROOM : TestId.CORRIDOR;
            TrackedStagedScreen next = fixture.navigateAndEnter(id);

            assertSame(next.screen, fixture.display.getScreen());
            assertEquals(RESOURCES_PER_SCREEN, fixture.resources.liveCount);
            assertEquals(1, fixture.containers.attachedCount);

            for (TrackedStagedScreen created : fixture.factory.created) {
                if (created == next) {
                    assertFalse(created.isDestroyed());
                    assertEquals(RESOURCES_PER_SCREEN,
                            created.resources.resourceCount());
                } else {
                    assertTrue(created.isDestroyed());
                    assertEquals(0, created.resources.resourceCount());
                    assertEquals(1, created.destroyCount);
                }
            }
        }

        fixture.engine.stop();
        fixture.engine.stop();

        assertEquals(ScreenCoordinator.State.STOPPED,
                fixture.coordinator.getState());
        assertEquals(0, fixture.resources.liveCount);
        assertEquals(fixture.resources.createdCount,
                fixture.resources.destroyedCount);
        assertEquals(0, fixture.containers.attachedCount);
        assertNull(fixture.display.getScreen());
        assertAllDestroyedExactlyOnce(fixture.factory.created);
    }

    @Test
    public void readinessTimeoutReleasesPendingAndCurrentResources() {
        Fixture fixture = new Fixture(100.0);
        fixture.navigateAndEnter(TestId.ROOM);

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        TrackedStagedScreen pending = fixture.factory.complete(
                TestId.CORRIDOR, false, false);
        fixture.display.update(1_000.0);
        fixture.display.update(1_100.0);

        assertEquals(1, fixture.failures.size());
        assertEquals(ScreenCoordinator.State.STOPPED,
                fixture.coordinator.getState());
        assertTrue(pending.isDestroyed());
        assertEquals(0, fixture.resources.liveCount);
        assertEquals(fixture.resources.createdCount,
                fixture.resources.destroyedCount);
        assertEquals(0, fixture.containers.attachedCount);
        assertNull(fixture.display.getScreen());
        assertAllDestroyedExactlyOnce(fixture.factory.created);
    }

    @Test
    public void enterFailurePerformsFatalCleanupOfPromotedScreen() {
        Fixture fixture = new Fixture();
        fixture.navigateAndEnter(TestId.ROOM);

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        TrackedStagedScreen failing = fixture.factory.complete(
                TestId.CORRIDOR, true, true);
        fixture.display.update(fixture.nextTimestamp());
        fixture.display.update(fixture.nextTimestamp());

        assertEquals(1, fixture.failures.size());
        assertTrue(failing.isDestroyed());
        assertEquals(0, fixture.resources.liveCount);
        assertEquals(fixture.resources.createdCount,
                fixture.resources.destroyedCount);
        assertEquals(0, fixture.containers.attachedCount);
        assertNull(fixture.display.getScreen());
        assertAllDestroyedExactlyOnce(fixture.factory.created);
    }

    @Test
    public void resultArrivingAfterEngineStopIsImmediatelyReleased() {
        Fixture fixture = new Fixture();

        fixture.coordinator.navigateTo(TestId.ROOM);
        fixture.engine.stop();
        TrackedStagedScreen late = fixture.factory.complete(
                TestId.ROOM, true, false);

        assertTrue(late.isDestroyed());
        assertEquals(1, late.destroyCount);
        assertEquals(0, late.resources.resourceCount());
        assertEquals(0, fixture.resources.liveCount);
        assertEquals(fixture.resources.createdCount,
                fixture.resources.destroyedCount);
        assertEquals(0, fixture.containers.attachedCount);
        assertTrue(fixture.failures.isEmpty());
    }

    @Test
    public void engineStopReleasesCurrentAndPendingScreensTogether() {
        Fixture fixture = new Fixture();
        fixture.navigateAndEnter(TestId.ROOM);
        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        TrackedStagedScreen pending = fixture.factory.complete(
                TestId.CORRIDOR, false, false);
        fixture.display.update(fixture.nextTimestamp());

        fixture.engine.stop();

        assertTrue(pending.isDestroyed());
        assertEquals(0, fixture.resources.liveCount);
        assertEquals(fixture.resources.createdCount,
                fixture.resources.destroyedCount);
        assertEquals(0, fixture.containers.attachedCount);
        assertNull(fixture.display.getScreen());
        assertAllDestroyedExactlyOnce(fixture.factory.created);
    }

    private static void assertAllDestroyedExactlyOnce(
            List<TrackedStagedScreen> screens) {
        for (TrackedStagedScreen screen : screens) {
            assertTrue(screen.isDestroyed());
            assertEquals(1, screen.destroyCount);
            assertEquals(0, screen.resources.resourceCount());
        }
    }

    private static final class Fixture {
        private final ResourceLedger resources = new ResourceLedger();
        private final ContainerLedger containers = new ContainerLedger();
        private final ManualFactory factory =
                new ManualFactory(resources, containers);
        private final List<Throwable> failures = new ArrayList<>();
        private final GameDisplay display;
        private final ScreenCoordinator coordinator;
        private final Engine engine;
        private double timestamp;

        private Fixture() {
            this(GameDisplay.DEFAULT_READY_TIMEOUT_MS);
        }

        private Fixture(double readyTimeoutMs) {
            display = new GameDisplay(effect -> { }, readyTimeoutMs);
            coordinator = new ScreenCoordinator(
                    factory,
                    display,
                    (referer, current) -> { },
                    (failedScreen, cause) -> {
                        failures.add(cause);
                        display.destroy();
                    });
            GameLoop loop = new GameLoop(display, callback -> { }, 60);
            engine = new Engine(loop, display, coordinator);
        }

        private TrackedStagedScreen navigateAndEnter(TestId id) {
            coordinator.navigateTo(id);
            TrackedStagedScreen next = factory.complete(id, true, false);
            display.update(nextTimestamp());
            display.update(nextTimestamp());
            assertEquals(ScreenCoordinator.State.READY, coordinator.getState());
            return next;
        }

        private double nextTimestamp() {
            timestamp += 10.0;
            return timestamp;
        }
    }

    private enum TestId implements ScreenId {
        ROOM,
        CORRIDOR
    }

    private static final class ManualFactory implements ScreenFactory {
        private final ResourceLedger resources;
        private final ContainerLedger containers;
        private final List<TrackedStagedScreen> created = new ArrayList<>();
        private ScreenId requestedId;
        private ScreenCreationCallback callback;

        private ManualFactory(ResourceLedger resources,
                              ContainerLedger containers) {
            this.resources = resources;
            this.containers = containers;
        }

        @Override
        public void create(ScreenId id, ScreenNavigation navigation,
                           ScreenCreationCallback callback) {
            if (this.callback != null) {
                throw new IllegalStateException("Creation already pending");
            }
            requestedId = id;
            this.callback = callback;
        }

        private TrackedStagedScreen complete(ScreenId id, boolean ready,
                                             boolean failOnEnter) {
            assertSame(requestedId, id);
            assertNotNull(callback);
            TrackedStagedScreen result = new TrackedStagedScreen(
                    id, ready, failOnEnter, resources, containers);
            created.add(result);
            ScreenCreationCallback completedCallback = callback;
            callback = null;
            requestedId = null;
            completedCallback.onSuccess(result);
            return result;
        }
    }

    private static final class TrackedStagedScreen implements StagedScreen {
        private final TrackedScreen screen;
        private final ResourceScope resources = new ResourceScope();
        private final ContainerLedger containers;
        private boolean activated;
        private boolean destroyed;
        private int destroyCount;

        private TrackedStagedScreen(ScreenId id, boolean ready,
                                    boolean failOnEnter,
                                    ResourceLedger resourceLedger,
                                    ContainerLedger containers) {
            this.containers = containers;
            resources.register(new TrackedResource(resourceLedger));
            resources.register(new TrackedResource(resourceLedger));
            screen = new TrackedScreen(
                    id, ready, failOnEnter, resourceLedger);
            resources.register(screen);
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate() {
            if (destroyed || activated) {
                throw new IllegalStateException("Invalid activation");
            }
            activated = true;
            containers.attachedCount++;
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            destroyCount++;
            if (activated) {
                activated = false;
                containers.attachedCount--;
            }
            resources.destroy();
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static final class TrackedScreen implements GameScreen {
        private final ScreenId id;
        private final boolean ready;
        private final boolean failOnEnter;
        private final ResourceLedger resources;
        private boolean destroyed;

        private TrackedScreen(ScreenId id, boolean ready,
                              boolean failOnEnter,
                              ResourceLedger resources) {
            this.id = id;
            this.ready = ready;
            this.failOnEnter = failOnEnter;
            this.resources = resources;
            resources.createdCount++;
            resources.liveCount++;
        }

        @Override
        public ScreenId getId() {
            return id;
        }

        @Override
        public void attach(ScreenContext context) {
        }

        @Override
        public boolean prepare() {
            return true;
        }

        @Override
        public boolean isReady() {
            return ready && !destroyed;
        }

        @Override
        public void enter() {
            if (failOnEnter) {
                throw new IllegalStateException("enter failed");
            }
        }

        @Override
        public void update(double timestamp) {
        }

        @Override
        public void setInputEnabled(boolean enabled) {
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            resources.destroyedCount++;
            resources.liveCount--;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static final class TrackedResource implements Destroyable {
        private final ResourceLedger resources;
        private boolean destroyed;

        private TrackedResource(ResourceLedger resources) {
            this.resources = resources;
            resources.createdCount++;
            resources.liveCount++;
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            resources.destroyedCount++;
            resources.liveCount--;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static final class ResourceLedger {
        private int createdCount;
        private int destroyedCount;
        private int liveCount;
    }

    private static final class ContainerLedger {
        private int attachedCount;
    }
}
