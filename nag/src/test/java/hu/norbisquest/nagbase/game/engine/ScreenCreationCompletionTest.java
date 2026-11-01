package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.core.ResourceScope;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScreenCreationCompletionTest {

    @Test
    public void duplicateAcceptedScreenRemainsOwnedByItsScope() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen accepted = new FakeScreen();
        ResourceScope scope = new ResourceScope();
        scope.register(accepted);

        completion.accept(accepted);

        assertTrue(completion.discardIfCompleted(accepted));
        assertFalse(accepted.isDestroyed());

        scope.destroy();
        assertTrue(accepted.isDestroyed());
    }

    @Test
    public void differentButEqualLateScreenIsDestroyedByIdentity() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen accepted = new FakeScreen();
        FakeScreen late = new FakeScreen();

        completion.accept(accepted);

        assertTrue(accepted.equals(late));
        assertTrue(completion.discardIfCompleted(late));
        assertFalse(accepted.isDestroyed());
        assertTrue(late.isDestroyed());
    }

    @Test
    public void lateNullResultIsIgnored() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen accepted = new FakeScreen();
        completion.accept(accepted);

        assertTrue(completion.discardIfCompleted(null));
        assertFalse(accepted.isDestroyed());
    }

    @Test
    public void lateResultAfterFailureIsDestroyed() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen late = new FakeScreen();

        assertTrue(completion.fail());

        assertTrue(completion.discardIfCompleted(late));
        assertTrue(late.isDestroyed());
    }

    @Test
    public void lateCleanupFailureDoesNotEscapeOrAffectAcceptedScreen() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen accepted = new FakeScreen();
        FakeScreen late = new FakeScreen();
        late.destroyFailure = new RuntimeException("cleanup failed");
        completion.accept(accepted);

        assertTrue(completion.discardIfCompleted(late));
        assertFalse(accepted.isDestroyed());
        assertTrue(late.destroyAttempted);
    }

    @Test
    public void incompleteCreationDoesNotDiscardScreen() {
        ScreenCreationCompletion completion = new ScreenCreationCompletion();
        FakeScreen screen = new FakeScreen();

        assertFalse(completion.discardIfCompleted(screen));
        assertFalse(screen.isDestroyed());
    }

    private static final class FakeScreen implements GameScreen {
        private boolean destroyed;
        private boolean destroyAttempted;
        private RuntimeException destroyFailure;

        @Override
        public ScreenId getId() {
            return null;
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
            return !destroyed;
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

        @Override
        public void destroy() {
            destroyAttempted = true;
            if (destroyFailure != null) {
                throw destroyFailure;
            }
            destroyed = true;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof FakeScreen;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
