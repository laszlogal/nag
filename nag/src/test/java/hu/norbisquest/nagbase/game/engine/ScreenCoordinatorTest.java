package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.game.Stage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScreenCoordinatorTest {

    @Test
    public void successfulRequestCommitsAndDestroysPreviousScreenOnce() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        RecordingStateStore stateStore = new RecordingStateStore();
        FakeScreen previous = new FakeScreen(TestId.ROOM);
        FakeScreen next = new FakeScreen(TestId.CORRIDOR);
        host.screen = previous;
        ScreenCoordinator coordinator = new ScreenCoordinator(factory, host, stateStore);

        coordinator.changeScreen(TestId.CORRIDOR);
        FakeCandidate candidate = new FakeCandidate(next);
        factory.succeed(0, candidate);

        assertEquals(ScreenCoordinator.State.ACTIVE, coordinator.getState());
        assertSame(next, host.screen);
        assertEquals(1, previous.destroyCount);
        assertEquals(1, next.loadCount);
        assertSame(coordinator, next.listener);
        assertTrue(candidate.activated);
        assertTrue(candidate.clearCurrentContent);
        assertEquals(1, stateStore.commits.size());
        assertEquals(TestId.ROOM, stateStore.commits.get(0).referer);
        assertEquals(TestId.CORRIDOR, stateStore.commits.get(0).current);
    }

    @Test
    public void failedRequestPreservesActiveScreenAndSettings() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        RecordingStateStore stateStore = new RecordingStateStore();
        FakeScreen previous = new FakeScreen(TestId.ROOM);
        host.screen = previous;
        ScreenCoordinator coordinator = new ScreenCoordinator(factory, host, stateStore);

        coordinator.changeScreen(TestId.CORRIDOR);
        factory.fail(0, new RuntimeException("chunk failed"));

        assertEquals(ScreenCoordinator.State.ACTIVE, coordinator.getState());
        assertSame(previous, host.screen);
        assertEquals(0, previous.destroyCount);
        assertEquals(0, stateStore.commits.size());
        assertEquals(1, host.failures.size());
    }

    @Test
    public void newestRequestWinsAndLateScreenIsDiscarded() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        RecordingStateStore stateStore = new RecordingStateStore();
        ScreenCoordinator coordinator = new ScreenCoordinator(factory, host, stateStore);
        FakeScreen late = new FakeScreen(TestId.ROOM);
        FakeScreen newest = new FakeScreen(TestId.CORRIDOR);
        FakeCandidate lateCandidate = new FakeCandidate(late);

        coordinator.changeScreen(TestId.ROOM);
        coordinator.changeScreen(TestId.CORRIDOR);
        factory.succeed(1, new FakeCandidate(newest));
        factory.succeed(0, lateCandidate);

        assertSame(newest, host.screen);
        assertEquals(1, late.destroyCount);
        assertTrue(lateCandidate.discarded);
        assertEquals(1, stateStore.commits.size());
        assertEquals(TestId.CORRIDOR, stateStore.commits.get(0).current);
    }

    @Test
    public void rejectedLoadIsDestroyedAndDoesNotReplaceCurrentScreen() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        RecordingStateStore stateStore = new RecordingStateStore();
        FakeScreen previous = new FakeScreen(TestId.ROOM);
        FakeScreen rejected = new FakeScreen(TestId.CORRIDOR);
        rejected.loadResult = false;
        FakeCandidate candidate = new FakeCandidate(rejected);
        host.screen = previous;
        ScreenCoordinator coordinator = new ScreenCoordinator(factory, host, stateStore);

        coordinator.changeScreen(TestId.CORRIDOR);
        factory.succeed(0, candidate);

        assertSame(previous, host.screen);
        assertEquals(0, previous.destroyCount);
        assertEquals(1, rejected.destroyCount);
        assertTrue(candidate.discarded);
        assertEquals(0, stateStore.commits.size());
        assertEquals(1, host.failures.size());
    }

    @Test
    public void stopInvalidatesPendingResult() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        FakeCandidate candidate = new FakeCandidate(new FakeScreen(TestId.ROOM));
        ScreenCoordinator coordinator = new ScreenCoordinator(
                factory, host, new RecordingStateStore());

        coordinator.changeScreen(TestId.ROOM);
        coordinator.stop();
        factory.succeed(0, candidate);

        assertEquals(ScreenCoordinator.State.STOPPED, coordinator.getState());
        assertTrue(candidate.discarded);
        assertEquals(1, candidate.screen.destroyCount);
        assertSame(null, host.screen);
    }

    @Test
    public void invalidRequestInvalidatesPendingResult() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        FakeCandidate pending = new FakeCandidate(new FakeScreen(TestId.ROOM));
        ScreenCoordinator coordinator = new ScreenCoordinator(
                factory, host, new RecordingStateStore());

        coordinator.changeScreen(TestId.ROOM);
        coordinator.changeScreen(null);
        factory.succeed(0, pending);

        assertEquals(ScreenCoordinator.State.FAILED, coordinator.getState());
        assertTrue(pending.discarded);
        assertEquals(1, pending.screen.destroyCount);
        assertEquals(1, host.failures.size());
        assertSame(null, host.screen);
    }

    @Test
    public void duplicateSuccessCannotInstallSecondScreen() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        RecordingStateStore stateStore = new RecordingStateStore();
        FakeScreen first = new FakeScreen(TestId.ROOM);
        FakeCandidate duplicate = new FakeCandidate(new FakeScreen(TestId.CORRIDOR));
        ScreenCoordinator coordinator = new ScreenCoordinator(factory, host, stateStore);

        coordinator.changeScreen(TestId.ROOM);
        factory.succeed(0, new FakeCandidate(first));
        factory.succeed(0, duplicate);

        assertSame(first, host.screen);
        assertTrue(duplicate.discarded);
        assertEquals(1, duplicate.screen.destroyCount);
        assertEquals(1, stateStore.commits.size());
    }

    @Test
    public void clearFlagIsPassedToCandidateActivation() {
        ManualScreenFactory factory = new ManualScreenFactory();
        RecordingHost host = new RecordingHost();
        FakeCandidate candidate = new FakeCandidate(new FakeScreen(TestId.ROOM));
        ScreenCoordinator coordinator = new ScreenCoordinator(
                factory, host, new RecordingStateStore());

        coordinator.changeScreen(TestId.ROOM, false);
        factory.succeed(0, candidate);

        assertTrue(candidate.activated);
        assertFalse(candidate.clearCurrentContent);
    }

    private enum TestId implements Stage.Id {
        ROOM,
        CORRIDOR;

        @Override
        public Stage.Id toId(String name) {
            return valueOf(name);
        }
    }

    private static final class ManualScreenFactory implements ScreenFactory {
        private final List<ScreenCreationCallback> callbacks = new ArrayList<>();

        @Override
        public void create(Stage.Id id, ScreenCreationCallback callback) {
            callbacks.add(callback);
        }

        void succeed(int request, ScreenCandidate candidate) {
            callbacks.get(request).onSuccess(candidate);
        }

        void fail(int request, Throwable cause) {
            callbacks.get(request).onFailure(cause);
        }
    }

    private static final class FakeCandidate implements ScreenCandidate {
        private final FakeScreen screen;
        private boolean activated;
        private boolean discarded;
        private boolean clearCurrentContent;

        private FakeCandidate(FakeScreen screen) {
            this.screen = screen;
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate(boolean clearCurrentContent) {
            activated = true;
            this.clearCurrentContent = clearCurrentContent;
        }

        @Override
        public void discard() {
            discarded = true;
        }
    }

    private static final class RecordingHost implements ScreenHost {
        private GameScreen screen;
        private final List<Throwable> failures = new ArrayList<>();

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void replaceScreen(GameScreen next) {
            if (screen != null) {
                screen.destroy();
            }
            screen = next;
        }

        @Override
        public void onScreenCreationFailed(Stage.Id id, Throwable cause) {
            failures.add(cause);
        }
    }

    private static final class RecordingStateStore implements ScreenStateStore {
        private final List<Commit> commits = new ArrayList<>();

        @Override
        public void commit(Stage.Id referer, Stage.Id current) {
            commits.add(new Commit(referer, current));
        }
    }

    private static final class Commit {
        private final Stage.Id referer;
        private final Stage.Id current;

        private Commit(Stage.Id referer, Stage.Id current) {
            this.referer = referer;
            this.current = current;
        }
    }

    private static final class FakeScreen implements GameScreen {
        private final Stage.Id id;
        private boolean destroyed;
        private boolean loadResult = true;
        private int loadCount;
        private int destroyCount;
        private ScreenListener listener;

        private FakeScreen(Stage.Id id) {
            this.id = id;
        }

        @Override
        public void setGui(GameGUI gui) {
        }

        @Override
        public void show() {
        }

        @Override
        public boolean isValid() {
            return !destroyed;
        }

        @Override
        public Stage.Id getId() {
            return id;
        }

        @Override
        public ScreenListener getListener() {
            return listener;
        }

        @Override
        public void setListener(ScreenListener listener) {
            this.listener = listener;
        }

        @Override
        public void appear() {
        }

        @Override
        public void execute(double timestamp) {
        }

        @Override
        public boolean load() {
            loadCount++;
            return loadResult;
        }

        @Override
        public boolean unload() {
            return true;
        }

        @Override
        public void destroy() {
            if (!destroyed) {
                destroyed = true;
                destroyCount++;
            }
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
