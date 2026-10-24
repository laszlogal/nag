package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.ResourceScope;

/** GWT adapter that builds a screen in a detached panel before activation. */
public final class StagedScreenParent implements ScreenParent {
    private final FlowPanel activeParent;
    private final FlowPanel stagingParent;
    private final ScreenCreationCallback callback;
    private ResourceScope buildingScope = new ResourceScope();
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
    public <T extends Destroyable> T register(T resource) {
        if (completed || buildingScope == null) {
            throw new IllegalStateException("Screen construction is complete");
        }

        buildingScope.register(resource);
        return resource;
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

        register(screen);
        ResourceScope screenScope = buildingScope;
        buildingScope = null;
        completed = true;
        callback.onSuccess(new CreatedScreen(screen, screenScope));
    }

    @Override
    public void onScreenCreationFailed(Throwable cause) {
        if (completed) {
            return;
        }
        completed = true;
        stagingParent.clear();

        Throwable failure = cause != null ? cause
                : new IllegalStateException("Screen creation failed without a cause");
        ResourceScope failedScope = buildingScope;
        buildingScope = null;
        try {
            if (failedScope != null) {
                failedScope.destroy();
            }
        } catch (Throwable cleanupFailure) {
            try {
                failure.addSuppressed(cleanupFailure);
            } catch (Throwable ignored) {
                // Reporting the original creation failure takes priority.
            }
        }
        callback.onFailure(failure);
    }

    private final class CreatedScreen implements StagedScreen {
        private final GameScreen screen;
        private final ResourceScope resources;
        private boolean activated;
        private boolean destroyed;

        private CreatedScreen(GameScreen screen, ResourceScope resources) {
            this.screen = screen;
            this.resources = resources;
        }

        @Override
        public GameScreen getScreen() {
            return screen;
        }

        @Override
        public void activate(boolean clearCurrentContent) {
            if (destroyed) {
                throw new IllegalStateException("Staged screen is destroyed");
            }
            if (activated) {
                throw new IllegalStateException("Staged screen is already active");
            }
            if (clearCurrentContent) {
                activeParent.clear();
            }
            activeParent.add(stagingParent);
            activated = true;
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            stagingParent.removeFromParent();
            stagingParent.clear();
            activated = false;
            resources.destroy();
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
