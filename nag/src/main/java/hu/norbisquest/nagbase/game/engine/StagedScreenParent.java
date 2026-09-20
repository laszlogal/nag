package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;

/** GWT adapter that builds a screen in a detached panel before activation. */
public final class StagedScreenParent implements ScreenParent {
    private final FlowPanel activeParent;
    private final FlowPanel stagingParent;
    private final ScreenCreationCallback callback;
    private boolean completed;

    public StagedScreenParent(FlowPanel activeParent, ScreenCreationCallback callback) {
        if (activeParent == null) {
            throw new IllegalArgumentException("activeParent must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        this.activeParent = activeParent;
        this.callback = callback;
        this.stagingParent = new FlowPanel();
        this.stagingParent.setSize("100%", "100%");
        this.stagingParent.getElement().getStyle().setPosition(Position.RELATIVE);
    }

    @Override
    public FlowPanel getPanel() {
        return stagingParent;
    }

    @Override
    public void onScreenCreated(GameScreen screen) {
        if (completed) {
            if (screen != null) {
                screen.destroy();
            }
            return;
        }
        if (screen == null) {
            onScreenCreationFailed(new IllegalStateException("Screen factory returned null"));
            return;
        }
        completed = true;
        callback.onSuccess(new Candidate(screen));
    }

    @Override
    public void onScreenCreationFailed(Throwable cause) {
        if (completed) {
            return;
        }
        completed = true;
        stagingParent.clear();
        callback.onFailure(cause != null ? cause
                : new IllegalStateException("Screen creation failed without a cause"));
    }

    private final class Candidate implements ScreenCandidate {
        private final GameScreen screen;
        private boolean activated;

        private Candidate(GameScreen screen) {
            this.screen = screen;
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate(boolean clearCurrentContent) {
            if (activated) {
                throw new IllegalStateException("Screen candidate is already active");
            }
            if (clearCurrentContent) {
                activeParent.clear();
            }
            activeParent.add(stagingParent);
            activated = true;
        }

        @Override
        public void discard() {
            stagingParent.removeFromParent();
            stagingParent.clear();
            activated = false;
        }
    }
}
