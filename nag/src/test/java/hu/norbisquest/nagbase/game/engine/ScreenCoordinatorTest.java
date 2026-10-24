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
    public void successfulTransitionInstallsScreenAndCommitsState() {
        Fixture fixture = new Fixture();
        FakeStagedScreen previous = new FakeStagedScreen(TestId.ROOM);
        FakeStagedScreen next = new FakeStagedScreen(TestId.CORRIDOR);
        fixture.host.current = previous;

        fixture.coordinator.changeScreen(TestId.CORRIDOR);
        fixture.factory.succeed(0, next);

        assertEquals(ScreenCoordinator.State.READY, fixture.coordinator.getState());
        assertSame(next.screen, fixture.host.getScreen());
        assertTrue(previous.isDestroyed());
        assertEquals(1, next.screen.loadCount);
        assertSame(fixture.coordinator, next.screen.listener);
        assertTrue(next.activated);
        assertTrue(next.clearCurrentContent);
        assertEquals(1, fixture.stateStore.commits.size());
        assertEquals(TestId.ROOM, fixture.stateStore.commits.get(0).referer);
        assertEquals(TestId.CORRIDOR, fixture.stateStore.commits.get(0).current);
        assertEquals(0, fixture.failures.causes.size());
    }

    @Test
    public void factoryFailureIsTerminal() {
        Fixture fixture = new Fixture();
        RuntimeException failure = new RuntimeException("chunk failed");

        fixture.coordinator.changeScreen(TestId.CORRIDOR);
        fixture.factory.fail(0, failure);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertSame(failure, fixture.failures.causes.get(0));
        assertEquals(TestId.CORRIDOR, fixture.failures.ids.get(0));
        assertEquals(0, fixture.stateStore.commits.size());
    }

    @Test
    public void rejectedLoadDestroysStagedScreenAndFailsFatally() {
        Fixture fixture = new Fixture();
        FakeStagedScreen rejected = new FakeStagedScreen(TestId.CORRIDOR);
        rejected.screen.loadResult = false;

        fixture.coordinator.changeScreen(TestId.CORRIDOR);
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

        fixture.coordinator.changeScreen(TestId.CORRIDOR);
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

        fixture.coordinator.changeScreen(TestId.CORRIDOR);
        fixture.factory.succeed(0, rejected);

        assertTrue(rejected.isDestroyed());
        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertEquals(1, fixture.failures.causes.size());
    }

    @Test
    public void overlappingTransitionIsIgnoredAndOriginalCompletes() {
        Fixture fixture = new Fixture();
        FakeStagedScreen next = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.changeScreen(TestId.ROOM);
        fixture.coordinator.changeScreen(TestId.CORRIDOR);

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
        fixture.coordinator.changeScreen(TestId.CORRIDOR);
        assertEquals(2, fixture.factory.callbacks.size());
    }

    @Test
    public void stopDestroysLateResultWithoutShowingFatalError() {
        Fixture fixture = new Fixture();
        FakeStagedScreen late = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.changeScreen(TestId.ROOM);
        fixture.coordinator.stop();
        fixture.factory.succeed(0, late);

        assertEquals(ScreenCoordinator.State.STOPPED, fixture.coordinator.getState());
        assertTrue(late.isDestroyed());
        assertEquals(0, fixture.failures.causes.size());
    }

    @Test
    public void clearFlagIsPassedToActivation() {
        Fixture fixture = new Fixture();
        FakeStagedScreen next = new FakeStagedScreen(TestId.ROOM);

        fixture.coordinator.changeScreen(TestId.ROOM, false);
        fixture.factory.succeed(0, next);

        assertTrue(next.activated);
        assertFalse(next.clearCurrentContent);
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
        private boolean clearCurrentContent;
        private RuntimeException activationFailure;

        private FakeStagedScreen(Stage.Id id) {
            screen = new FakeScreen(id);
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate(boolean clearCurrentContent) {
            if (activationFailure != null) {
                throw activationFailure;
            }
            activated = true;
            this.clearCurrentContent = clearCurrentContent;
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
        private RuntimeException replaceFailure;
        private int pauseCount;

        @Override
        public void pauseScreen() {
            pauseCount++;
        }

        @Override
        public GameScreen getScreen() {
            return current == null ? null : current.getScreen();
        }

        @Override
        public void replaceScreen(StagedScreen next) {
            if (replaceFailure != null) {
                throw replaceFailure;
            }
            StagedScreen previous = current;
            current = next;
            if (previous != null) {
                previous.destroy();
            }
        }
    }

    private static final class RecordingStateStore implements ScreenStateStore {
        private final List<Commit> commits = new ArrayList<>();

        @Override
        public void commit(Stage.Id referer, Stage.Id current) {
            commits.add(new Commit(referer, current));
        }
    }

    private static final class RecordingFatalErrorHandler
            implements FatalErrorHandler {
        private final List<Stage.Id> ids = new ArrayList<>();
        private final List<Throwable> causes = new ArrayList<>();

        @Override
        public void show(Stage.Id failedScreen, Throwable cause) {
            ids.add(failedScreen);
            causes.add(cause);
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
        private ScreenListener listener;

        private FakeScreen(Stage.Id id) {
            this.id = id;
        }

        @Override
        public void setInputEnabled(boolean enabled) {
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
            destroyed = true;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
