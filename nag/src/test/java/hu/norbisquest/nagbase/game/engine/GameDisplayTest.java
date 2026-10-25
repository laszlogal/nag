package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.core.ResourceScope;
import hu.norbisquest.nagbase.game.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameDisplayTest {

    @Test
    public void replacementKeepsIncomingScreenAndDestroysPreviousScreen() {
        GameDisplay display = new GameDisplay();
        FakeScreen first = new FakeScreen();
        FakeScreen second = new FakeScreen();
        FakeStagedScreen firstStaged = staged(first);
        FakeStagedScreen secondStaged = staged(second);

        display.replaceScreen(firstStaged);
        display.replaceScreen(secondStaged);

        assertTrue(firstStaged.isDestroyed());
        assertEquals(1, first.destroyCount);
        assertFalse(secondStaged.isDestroyed());
        assertSame(second, display.getScreen());

        display.update(1.0);
        assertEquals(1, second.showCount);

        display.destroy();
        assertTrue(secondStaged.isDestroyed());
        assertEquals(1, second.destroyCount);
    }

    @Test
    public void stagedScreenDestructionIsIdempotent() {
        FakeScreen screen = new FakeScreen();
        FakeStagedScreen staged = staged(screen);

        staged.destroy();
        staged.destroy();

        assertTrue(staged.isDestroyed());
        assertEquals(1, screen.destroyCount);
    }

    @Test
    public void transitionPausesOutgoingScreenUntilReplacement() {
        GameDisplay display = new GameDisplay();
        FakeScreen outgoing = new FakeScreen();
        display.replaceScreen(staged(outgoing));
        display.update(1.0);
        display.update(2.0);
        assertEquals(1, outgoing.executeCount);

        display.pauseScreen();
        display.update(3.0);
        assertFalse(outgoing.inputEnabled);
        assertEquals(1, outgoing.executeCount);

        FakeScreen incoming = new FakeScreen();
        display.replaceScreen(staged(incoming));
        display.update(4.0);
        display.update(5.0);
        assertEquals(1, incoming.showCount);
        assertEquals(1, incoming.executeCount);
        assertTrue(incoming.inputEnabled);
    }

    private FakeStagedScreen staged(FakeScreen screen) {
        ResourceScope scope = new ResourceScope();
        scope.register(screen);
        return new FakeStagedScreen(screen, scope);
    }

    private static final class FakeStagedScreen implements StagedScreen {
        private final FakeScreen screen;
        private final ResourceScope resources;
        private boolean destroyed;

        private FakeStagedScreen(FakeScreen screen, ResourceScope resources) {
            this.screen = screen;
            this.resources = resources;
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate(boolean clearCurrentContent) {
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            resources.destroy();
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static final class FakeScreen implements GameScreen {
        private boolean destroyed;
        private int destroyCount;
        private int showCount;
        private int executeCount;
        private boolean inputEnabled = true;
        private ScreenContext context;

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
            showCount++;
        }

        @Override
        public boolean isReady() {
            return !destroyed;
        }

        @Override
        public Stage.Id getId() {
            return null;
        }

        @Override
        public void update(double timestamp) {
            executeCount++;
        }

        @Override
        public boolean prepare() {
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
