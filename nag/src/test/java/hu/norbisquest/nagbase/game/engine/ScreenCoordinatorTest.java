package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScreenCoordinatorTest {

    @Test
    public void successfulTransitionInstallsScreenAndCommitsState() {
        Fixture fixture = new Fixture();
        FakeStagedScreen previous = new FakeStagedScreen(TestId.ROOM);
        FakeStagedScreen next = new FakeStagedScreen(TestId.CORRIDOR);
        fixture.host.current = previous;

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.succeed(0, next);

        assertEquals(ScreenCoordinator.State.READY, fixture.coordinator.getState());
        assertSame(next.screen, fixture.host.getScreen());
        assertTrue(previous.isDestroyed());
        assertEquals(1, next.screen.loadCount);
        assertSame(fixture.coordinator, fixture.factory.navigations.get(0));
        assertTrue(next.activated);
        assertEquals(TransitionEffect.FADE_IN, fixture.host.transitionEffect);
        assertEquals(1, fixture.stateStore.commits.size());
        assertEquals(TestId.ROOM, fixture.stateStore.commits.get(0).referer);
        assertEquals(TestId.CORRIDOR, fixture.stateStore.commits.get(0).current);
        assertEquals(0, fixture.failures.causes.size());
    }

    @Test
    public void factoryFailureIsTerminal() {
        Fixture fixture = new Fixture();
        RuntimeException failure = new RuntimeException("chunk failed");

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.fail(0, failure);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertSame(failure, fixture.failures.causes.get(0));
        assertEquals(TestId.CORRIDOR, fixture.failures.ids.get(0));
        assertEquals(0, fixture.stateStore.commits.size());
    }

    @Test
    public void nullTransitionEffectFailsBeforeLoadingStarts() {
        Fixture fixture = new Fixture();

        fixture.coordinator.navigateTo(TestId.CORRIDOR, null);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertEquals(0, fixture.host.pauseCount);
        assertTrue(fixture.factory.callbacks.isEmpty());
        assertEquals(1, fixture.failures.causes.size());
        assertTrue(fixture.failures.causes.get(0)
                instanceof IllegalArgumentException);
    }

    @Test
    public void rejectedLoadDestroysStagedScreenAndFailsFatally() {
        Fixture fixture = new Fixture();
        FakeStagedScreen rejected = new FakeStagedScreen(TestId.CORRIDOR);
        rejected.screen.loadResult = false;

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.succeed(0, rejected);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertTrue(rejected.isDestroyed());
        assertNull(fixture.host.getScreen());
        assertEquals(1, fixture.failures.causes.size());
    }

    @Test
    public void activationFailureDestroysStagedScreen() {
        Fixture fixture = new Fixture();
        FakeStagedScreen rejected = new FakeStagedScreen(TestId.CORRIDOR);
        rejected.activationFailure = new RuntimeException("activation failed");

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.succeed(0, rejected);

        assertTrue(rejected.isDestroyed());
        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertEquals(1, fixture.failures.causes.size());
    }

    @Test
    public void hostRejectionDestroysUninstalledScreen() {
        Fixture fixture = new Fixture();
        FakeStagedScreen rejected = new FakeStagedScreen(TestId.CORRIDOR);
        fixture.host.replaceFailure = new RuntimeException("host rejected screen");

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.succeed(0, rejected);

        assertTrue(rejected.isDestroyed());
        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertEquals(1, fixture.failures.causes.size());
    }

    @Test
    public void overlappingTransitionIsIgnoredAndOriginalCompletes() {
        Fixture fixture = new Fixture();
        FakeStagedScreen next = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.navigateTo(TestId.ROOM);
        fixture.coordinator.navigateTo(TestId.CORRIDOR);

        assertEquals(1, fixture.host.pauseCount);
        assertEquals(1, fixture.factory.callbacks.size());
        assertEquals(ScreenCoordinator.State.LOADING, fixture.coordinator.getState());
        fixture.factory.succeed(0, next);

        assertEquals(ScreenCoordinator.State.READY, fixture.coordinator.getState());
        assertSame(next.screen, fixture.host.getScreen());
        assertFalse(next.isDestroyed());
        assertTrue(fixture.failures.causes.isEmpty());

        fixture.factory.succeed(0, next);
        assertFalse(next.isDestroyed());
        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        assertEquals(2, fixture.factory.callbacks.size());
    }

    @Test
    public void stateIsCommittedOnlyAfterPresentation() {
        Fixture fixture = new Fixture();
        fixture.host.autoPresent = false;
        FakeStagedScreen previous = new FakeStagedScreen(TestId.ROOM);
        FakeStagedScreen next = new FakeStagedScreen(TestId.CORRIDOR);
        fixture.host.current = previous;

        fixture.coordinator.navigateTo(TestId.CORRIDOR);
        fixture.factory.succeed(0, next);

        assertEquals(ScreenCoordinator.State.LOADING, fixture.coordinator.getState());
        assertSame(previous.screen, fixture.host.getScreen());
        assertFalse(previous.isDestroyed());
        assertTrue(fixture.stateStore.commits.isEmpty());

        fixture.host.presentPending();

        assertEquals(ScreenCoordinator.State.READY, fixture.coordinator.getState());
        assertSame(next.screen, fixture.host.getScreen());
        assertTrue(previous.isDestroyed());
        assertEquals(1, fixture.stateStore.commits.size());
    }

    @Test
    public void stopDestroysLateResultWithoutShowingFatalError() {
        Fixture fixture = new Fixture();
        FakeStagedScreen late = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.navigateTo(TestId.ROOM);
        fixture.coordinator.stop();
        fixture.factory.succeed(0, late);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertTrue(late.isDestroyed());
        assertEquals(0, fixture.failures.causes.size());
    }

    @Test
    public void stopDestroysScreenWaitingForReadiness() {
        Fixture fixture = new Fixture();
        fixture.host.autoPresent = false;
        FakeStagedScreen pending = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.navigateTo(TestId.ROOM);
        fixture.factory.succeed(0, pending);
        fixture.coordinator.stop();

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertTrue(pending.isDestroyed());
        assertNull(fixture.host.pending);
        assertTrue(fixture.stateStore.commits.isEmpty());
        assertTrue(fixture.failures.causes.isEmpty());
    }

    @Test
    public void transitionEffectIsPassedToHost() {
        Fixture fixture = new Fixture();
        FakeStagedScreen next = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.navigateTo(
                TestId.ROOM, TransitionEffect.SEAMLESS);
        fixture.factory.succeed(0, next);

        assertTrue(next.activated);
        assertEquals(TransitionEffect.SEAMLESS, fixture.host.transitionEffect);
    }

    private static final class Fixture {
        private final ManualScreenFactory factory = new ManualScreenFactory();
        private final RecordingHost host = new RecordingHost();
        private final RecordingStateStore stateStore = new RecordingStateStore();
        private final RecordingFatalErrorHandler failures =
                new RecordingFatalErrorHandler();
        private final ScreenCoordinator coordinator = new ScreenCoordinator(
                factory, host, stateStore, failures);
    }

    private enum TestId implements ScreenId {
        ROOM,
        CORRIDOR
    }

    private static final class ManualScreenFactory implements ScreenFactory {
        private final List<ScreenCreationCallback> callbacks = new ArrayList<>();
        private final List<ScreenNavigation> navigations = new ArrayList<>();

        @Override
        public void create(ScreenId id, ScreenNavigation navigation,
                           ScreenCreationCallback callback) {
            navigations.add(navigation);
            callbacks.add(callback);
        }

        private void succeed(int request, StagedScreen screen) {
            callbacks.get(request).onSuccess(screen);
        }

        private void fail(int request, Throwable cause) {
            callbacks.get(request).onFailure(cause);
        }
    }

    private static final class FakeStagedScreen implements StagedScreen {
        private final FakeScreen screen;
        private boolean activated;
        private boolean destroyed;
        private RuntimeException activationFailure;

        private FakeStagedScreen(ScreenId id) {
            screen = new FakeScreen(id);
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate() {
            if (activationFailure != null) {
                throw activationFailure;
            }
            activated = true;
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            screen.destroy();
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static final class RecordingHost implements ScreenHost {
        private StagedScreen current;
        private StagedScreen pending;
        private ScreenPresentationCallback presentationCallback;
        private RuntimeException replaceFailure;
        private int pauseCount;
        private TransitionEffect transitionEffect;
        private boolean autoPresent = true;

        @Override
        public void pauseScreen() {
            pauseCount++;
        }

        @Override
        public GameScreen getScreen() {
            return current == null ? null : current.getScreen();
        }

        @Override
        public void presentWhenReady(StagedScreen next, TransitionEffect effect,
                                     ScreenPresentationCallback callback) {
            if (replaceFailure != null) {
                throw replaceFailure;
            }
            pending = next;
            presentationCallback = callback;
            next.getScreen().setInputEnabled(false);
            transitionEffect = effect;
            if (autoPresent) {
                presentPending();
            }
        }

        @Override
        public void cancelPresentation() {
            StagedScreen cancelled = pending;
            pending = null;
            presentationCallback = null;
            if (cancelled != null) {
                cancelled.destroy();
            }
        }

        private void presentPending() {
            StagedScreen next = pending;
            ScreenPresentationCallback callback = presentationCallback;
            pending = null;
            presentationCallback = null;
            try {
                next.activate();
            } catch (Throwable cause) {
                next.destroy();
                callback.onFailure(cause);
                return;
            }
            StagedScreen previous = current;
            current = next;
            if (previous != null) {
                previous.destroy();
            }
            callback.onPresented();
        }
    }

    private static final class RecordingStateStore implements ScreenStateStore {
        private final List<Commit> commits = new ArrayList<>();

        @Override
        public void commit(ScreenId referer, ScreenId current) {
            commits.add(new Commit(referer, current));
        }
    }

    private static final class RecordingFatalErrorHandler
            implements FatalErrorHandler {
        private final List<ScreenId> ids = new ArrayList<>();
        private final List<Throwable> causes = new ArrayList<>();

        @Override
        public void show(ScreenId failedScreen, Throwable cause) {
            ids.add(failedScreen);
            causes.add(cause);
        }
    }

    private static final class Commit {
        private final ScreenId referer;
        private final ScreenId current;

        private Commit(ScreenId referer, ScreenId current) {
            this.referer = referer;
            this.current = current;
        }
    }

    private static final class FakeScreen implements GameScreen {
        private final ScreenId id;
        private boolean destroyed;
        private boolean loadResult = true;
        private boolean inputEnabled = true;
        private int loadCount;
        private ScreenContext context;

        private FakeScreen(ScreenId id) {
            this.id = id;
        }

        @Override
        public void setInputEnabled(boolean enabled) {
            inputEnabled = enabled;
        }

        @Override
        public void attach(ScreenContext context) {
            this.context = context;
        }

        @Override
        public void enter() {
        }

        @Override
        public boolean isReady() {
            return !destroyed;
        }

        @Override
        public ScreenId getId() {
            return id;
        }

        @Override
        public boolean prepare() {
            loadCount++;
            return loadResult;
        }

        @Override
        public void update(double timestamp) {
        }

        @Override
        public void destroy() {
            destroyed = true;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
