package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.TransitionEffect;
import hu.norbisquest.nagbase.core.ResourceScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameDisplayTest {

    @Test
    public void replacementKeepsIncomingScreenAndDestroysPreviousScreen() {
        List<TransitionEffect> effects = new ArrayList<>();
        GameDisplay display = new GameDisplay(effects::add);
        FakeScreen first = new FakeScreen();
        FakeScreen second = new FakeScreen();
        FakeStagedScreen firstStaged = staged(first);
        FakeStagedScreen secondStaged = staged(second);

        display.replaceScreen(firstStaged, TransitionEffect.FADE_IN);
        display.replaceScreen(secondStaged, TransitionEffect.SEAMLESS);

        assertTrue(firstStaged.isDestroyed());
        assertEquals(1, first.destroyCount);
        assertFalse(secondStaged.isDestroyed());
        assertSame(second, display.getScreen());

        display.update(1.0);
        assertEquals(1, second.showCount);
        assertEquals(List.of(TransitionEffect.SEAMLESS), effects);

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
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen outgoing = new FakeScreen();
        display.replaceScreen(staged(outgoing), TransitionEffect.FADE_IN);
        display.update(1.0);
        display.update(2.0);
        assertEquals(1, outgoing.executeCount);

        display.pauseScreen();
        display.update(3.0);
        assertFalse(outgoing.inputEnabled);
        assertEquals(1, outgoing.executeCount);

        FakeScreen incoming = new FakeScreen();
        display.replaceScreen(staged(incoming), TransitionEffect.FADE_IN);
        display.update(4.0);
        display.update(5.0);
        assertEquals(1, incoming.showCount);
        assertEquals(1, incoming.executeCount);
        assertTrue(incoming.inputEnabled);
    }

    @Test
    public void transitionIsPresentedOnlyWhenScreenEnters() {
        List<TransitionEffect> effects = new ArrayList<>();
        GameDisplay display = new GameDisplay(effects::add);
        FakeScreen incoming = new FakeScreen();
        incoming.ready = false;

        display.replaceScreen(staged(incoming), TransitionEffect.SEAMLESS);
        display.update(1.0);

        assertTrue(effects.isEmpty());
        assertEquals(0, incoming.showCount);

        incoming.ready = true;
        display.update(2.0);

        assertEquals(List.of(TransitionEffect.SEAMLESS), effects);
        assertEquals(1, incoming.showCount);
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
        public void activate() {
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
        private boolean ready = true;
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
            return ready && !destroyed;
        }

        @Override
        public ScreenId getId() {
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
