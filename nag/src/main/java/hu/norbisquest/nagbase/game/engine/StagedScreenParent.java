package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.ResourceScope;

/** GWT adapter that builds a screen in a detached panel before activation. */
public final class StagedScreenParent implements ScreenParent {
    interface StagingContainer {
        FlowPanel getPanel();

        void activate();

        void clear();

        void detach();
    }

    private final StagingContainer stagingContainer;
    private final ScreenContext context;
    private final ScreenCreationCallback callback;
    private final ScreenCreationCompletion completion =
            new ScreenCreationCompletion();
    private ResourceScope buildingScope = new ResourceScope();

    public StagedScreenParent(FlowPanel activeParent, ScreenContext context,
                              ScreenCreationCallback callback) {
        this(createStagingContainer(activeParent), context, callback);
    }

    StagedScreenParent(StagingContainer stagingContainer,
                       ScreenContext context,
                       ScreenCreationCallback callback) {
        if (stagingContainer == null) {
            throw new IllegalArgumentException(
                    "stagingContainer must not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        this.stagingContainer = stagingContainer;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public <T extends Destroyable> T register(T resource) {
        if (completion.isCompleted() || buildingScope == null) {
            throw new IllegalStateException("Screen construction is complete");
        }

        buildingScope.register(resource);
        return resource;
    }

    @Override
    public FlowPanel getPanel() {
        return stagingContainer.getPanel();
    }

    @Override
    public void onScreenCreated(GameScreen screen) {
        if (completion.discardIfCompleted(screen)) {
            return;
        }
        if (screen == null) {
            onScreenCreationFailed(new IllegalStateException("Screen factory returned null"));
            return;
        }

        try {
            register(screen);
            screen.attach(context);
        } catch (Throwable cause) {
            onScreenCreationFailed(cause);
            return;
        }
        ResourceScope screenScope = buildingScope;
        buildingScope = null;
        completion.accept(screen);
        callback.onSuccess(new CreatedScreen(screen, screenScope));
    }

    @Override
    public void onScreenCreationFailed(Throwable cause) {
        if (!completion.fail()) {
            return;
        }

        Throwable failure = cause != null ? cause
                : new IllegalStateException("Screen creation failed without a cause");
        try {
            stagingContainer.clear();
        } catch (Throwable cleanupFailure) {
            addSuppressed(failure, cleanupFailure);
        }
        ResourceScope failedScope = buildingScope;
        buildingScope = null;
        try {
            if (failedScope != null) {
                failedScope.destroy();
            }
        } catch (Throwable cleanupFailure) {
            addSuppressed(failure, cleanupFailure);
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
        public void activate() {
            if (destroyed) {
                throw new IllegalStateException("Staged screen is destroyed");
            }
            if (activated) {
                throw new IllegalStateException("Staged screen is already active");
            }
            stagingContainer.activate();
            activated = true;
        }

        @Override
        public void destroy() {
            if (destroyed) {
                return;
            }
            destroyed = true;
            activated = false;
            Throwable firstFailure = null;
            try {
                stagingContainer.detach();
            } catch (Throwable failure) {
                firstFailure = failure;
            }
            try {
                stagingContainer.clear();
            } catch (Throwable failure) {
                if (firstFailure == null) {
                    firstFailure = failure;
                } else {
                    addSuppressed(firstFailure, failure);
                }
            }
            try {
                resources.destroy();
            } catch (Throwable failure) {
                if (firstFailure == null) {
                    firstFailure = failure;
                } else {
                    addSuppressed(firstFailure, failure);
                }
            }
            if (firstFailure != null) {
                rethrow(firstFailure);
            }
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private static StagingContainer createStagingContainer(
            FlowPanel activeParent) {
        if (activeParent == null) {
            throw new IllegalArgumentException("activeParent must not be null");
        }
        return new GwtStagingContainer(activeParent);
    }

    private static void addSuppressed(Throwable primary,
                                      Throwable suppressed) {
        try {
            primary.addSuppressed(suppressed);
        } catch (Throwable ignored) {
            // Cleanup must continue even if recording the failure fails.
        }
    }

    private static void rethrow(Throwable failure) {
        if (failure instanceof RuntimeException) {
            throw (RuntimeException) failure;
        }
        if (failure instanceof Error) {
            throw (Error) failure;
        }
        throw new RuntimeException(failure);
    }

    private static final class GwtStagingContainer
            implements StagingContainer {
        private final FlowPanel activeParent;
        private final FlowPanel stagingParent = new FlowPanel();

        private GwtStagingContainer(FlowPanel activeParent) {
            this.activeParent = activeParent;
            stagingParent.setSize("100%", "100%");
            stagingParent.getElement().getStyle()
                    .setPosition(Position.RELATIVE);
        }

        @Override
        public FlowPanel getPanel() {
            return stagingParent;
        }

        @Override
        public void activate() {
            activeParent.add(stagingParent);
        }

        @Override
        public void clear() {
            stagingParent.clear();
        }

        @Override
        public void detach() {
            stagingParent.removeFromParent();
        }
    }
}
