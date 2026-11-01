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

        RecordingPresentation firstPresentation = present(
                display, firstStaged, TransitionEffect.FADE_IN);
        display.update(1.0);
        display.update(2.0);

        RecordingPresentation secondPresentation = present(
                display, secondStaged, TransitionEffect.SEAMLESS);
        display.update(3.0);

        assertTrue(firstStaged.isDestroyed());
        assertEquals(1, first.destroyCount);
        assertFalse(secondStaged.isDestroyed());
        assertSame(second, display.getScreen());
        assertEquals(1, firstPresentation.presentedCount);
        assertEquals(0, secondPresentation.presentedCount);

        display.update(4.0);
        assertEquals(1, second.showCount);
        assertEquals(1, secondPresentation.presentedCount);
        assertEquals(List.of(
                TransitionEffect.FADE_IN,
                TransitionEffect.SEAMLESS), effects);

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
        FakeStagedScreen outgoingStaged = staged(outgoing);
        present(display, outgoingStaged, TransitionEffect.FADE_IN);
        display.update(1.0);
        display.update(2.0);
        display.update(3.0);
        assertEquals(1, outgoing.executeCount);

        display.pauseScreen();
        display.update(4.0);
        assertFalse(outgoing.inputEnabled);
        assertEquals(1, outgoing.executeCount);

        FakeScreen incoming = new FakeScreen();
        incoming.ready = false;
        present(display, staged(incoming), TransitionEffect.FADE_IN);
        assertFalse(incoming.inputEnabled);
        display.update(5.0);
        assertSame(outgoing, display.getScreen());
        assertFalse(outgoingStaged.isDestroyed());
        assertFalse(incoming.inputEnabled);

        incoming.ready = true;
        display.update(6.0);
        display.update(7.0);
        display.update(8.0);
        assertTrue(outgoingStaged.isDestroyed());
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

        present(display, staged(incoming), TransitionEffect.SEAMLESS);
        display.update(1.0);

        assertTrue(effects.isEmpty());
        assertEquals(0, incoming.showCount);
        assertNull(display.getScreen());

        incoming.ready = true;
        display.update(2.0);

        assertTrue(effects.isEmpty());
        assertSame(incoming, display.getScreen());

        incoming.ready = false;
        display.update(3.0);

        assertEquals(List.of(TransitionEffect.SEAMLESS), effects);
        assertEquals(1, incoming.showCount);
    }

    @Test
    public void readinessTimeoutDestroysPendingScreenAndReportsFailure() {
        GameDisplay display = new GameDisplay(effect -> { }, 100.0);
        FakeScreen incoming = new FakeScreen();
        incoming.ready = false;
        FakeStagedScreen staged = staged(incoming);
        RecordingPresentation presentation = present(
                display, staged, TransitionEffect.FADE_IN);

        display.update(10.0);
        display.update(109.0);

        assertFalse(staged.isDestroyed());
        assertNull(presentation.failure);

        display.update(110.0);

        assertTrue(staged.isDestroyed());
        assertEquals(1, incoming.destroyCount);
        assertTrue(presentation.failure instanceof IllegalStateException);
        assertEquals(0, presentation.presentedCount);
        assertNull(display.getScreen());
    }

    @Test
    public void readinessExceptionDestroysPendingScreenAndReportsFailure() {
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen incoming = new FakeScreen();
        incoming.readinessFailure = new RuntimeException("readiness failed");
        FakeStagedScreen staged = staged(incoming);
        RecordingPresentation presentation = present(
                display, staged, TransitionEffect.FADE_IN);

        display.update(1.0);

        assertTrue(staged.isDestroyed());
        assertSame(incoming.readinessFailure, presentation.failure);
        assertEquals(0, presentation.presentedCount);
    }

    @Test
    public void activationFailureDestroysIncomingAndKeepsCurrentScreen() {
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen current = new FakeScreen();
        FakeStagedScreen currentStaged = staged(current);
        present(display, currentStaged, TransitionEffect.FADE_IN);
        display.update(1.0);
        display.update(2.0);

        FakeScreen incoming = new FakeScreen();
        FakeStagedScreen incomingStaged = staged(incoming);
        incomingStaged.activationFailure =
                new RuntimeException("activation failed");
        RecordingPresentation presentation = present(
                display, incomingStaged, TransitionEffect.FADE_IN);

        display.update(3.0);

        assertSame(current, display.getScreen());
        assertFalse(currentStaged.isDestroyed());
        assertTrue(incomingStaged.isDestroyed());
        assertSame(incomingStaged.activationFailure, presentation.failure);
    }

    @Test
    public void enterFailureIsReportedAndInputRemainsDisabled() {
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen incoming = new FakeScreen();
        incoming.enterFailure = new RuntimeException("enter failed");
        RecordingPresentation presentation = present(
                display, staged(incoming), TransitionEffect.FADE_IN);

        display.update(1.0);
        display.update(2.0);

        assertSame(incoming.enterFailure, presentation.failure);
        assertEquals(0, presentation.presentedCount);
        assertFalse(incoming.inputEnabled);
    }

    @Test
    public void destroyCleansUpPendingAndCurrentScreens() {
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen current = new FakeScreen();
        FakeStagedScreen currentStaged = staged(current);
        present(display, currentStaged, TransitionEffect.FADE_IN);
        display.update(1.0);
        display.update(2.0);

        FakeScreen pending = new FakeScreen();
        pending.ready = false;
        FakeStagedScreen pendingStaged = staged(pending);
        present(display, pendingStaged, TransitionEffect.FADE_IN);

        display.destroy();

        assertTrue(currentStaged.isDestroyed());
        assertTrue(pendingStaged.isDestroyed());
        assertEquals(1, current.destroyCount);
        assertEquals(1, pending.destroyCount);
    }

    @Test
    public void cancellationDestroysPromotedButUnenteredScreen() {
        GameDisplay display = new GameDisplay(effect -> { });
        FakeScreen incoming = new FakeScreen();
        FakeStagedScreen staged = staged(incoming);
        RecordingPresentation presentation = present(
                display, staged, TransitionEffect.FADE_IN);

        display.update(1.0);
        assertSame(incoming, display.getScreen());

        display.cancelPresentation();

        assertTrue(staged.isDestroyed());
        assertNull(display.getScreen());
        assertEquals(0, presentation.presentedCount);
        assertNull(presentation.failure);
    }

    private RecordingPresentation present(GameDisplay display,
                                          StagedScreen screen,
                                          TransitionEffect effect) {
        RecordingPresentation presentation = new RecordingPresentation();
        display.presentWhenReady(screen, effect, presentation);
        return presentation;
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
        private RuntimeException activationFailure;

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
            if (activationFailure != null) {
                throw activationFailure;
            }
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
        private RuntimeException readinessFailure;
        private RuntimeException enterFailure;
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
            if (enterFailure != null) {
                throw enterFailure;
            }
            showCount++;
        }

        @Override
        public boolean isReady() {
            if (readinessFailure != null) {
                throw readinessFailure;
            }
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

    private static final class RecordingPresentation
            implements ScreenPresentationCallback {
        private int presentedCount;
        private Throwable failure;

        @Override
        public void onPresented() {
            presentedCount++;
        }

        @Override
        public void onFailure(Throwable cause) {
            failure = cause;
        }
    }
}
