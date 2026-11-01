package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.TransitionEffect;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameDisplay extends NAGObject implements ScreenHost, LoopTarget {

    static final double DEFAULT_READY_TIMEOUT_MS = 30_000.0;

    private StagedScreen stagedScreen;
    private PendingScreen pendingScreen;
    private final ScreenTransitionPresenter transitionPresenter;
    private final double readyTimeoutMs;
    private TransitionEffect transitionEffect;
    private ScreenPresentationCallback entryCallback;
    private boolean changed;
    private boolean paused;
    private final List<ScreenChangeListener> screenChangeListeners = new ArrayList<>();

    public GameDisplay(ScreenTransitionPresenter transitionPresenter) {
        this(transitionPresenter, DEFAULT_READY_TIMEOUT_MS);
    }

    GameDisplay(ScreenTransitionPresenter transitionPresenter,
                double readyTimeoutMs) {
        if (transitionPresenter == null) {
            throw new IllegalArgumentException(
                    "transitionPresenter must not be null");
        }
        if (readyTimeoutMs <= 0) {
            throw new IllegalArgumentException(
                    "readyTimeoutMs must be greater than zero");
        }
        this.transitionPresenter = transitionPresenter;
        this.readyTimeoutMs = readyTimeoutMs;
        changed = false;
    }

    void addScreenChangeListener(ScreenChangeListener listener) {
        if (!screenChangeListeners.contains(listener)) {
            screenChangeListeners.add(listener);
        }
    }


    @Override
    public void update(double timestamp) {
        if (pendingScreen != null) {
            updatePendingScreen(timestamp);
            return;
        }
        if (paused) {
            return;
        }
        if (changed) {
            enterCurrentScreen();
        } else {
            executeScreen(timestamp);
        }
    }

    @Override
    public void pauseScreen() {
        paused = true;
        GameScreen screen = currentScreen();
        if (screen != null) {
            screen.setInputEnabled(false);
        }
    }

    private GameScreen currentScreen() {
        return stagedScreen != null ? stagedScreen.getScreen() : null;
    }

    private void executeScreen(double timestamp) {
        GameScreen screen = currentScreen();
        if (screen != null) {
            screen.update(timestamp);
        }
    }

    @Override
    protected void doDestroy() {
        destroyPendingScreen();
        StagedScreen current = stagedScreen;
        stagedScreen = null;
        transitionEffect = null;
        entryCallback = null;
        changed = false;
        paused = false;
        screenChangeListeners.clear();
        if (current != null) {
            current.destroy();
        }
    }

    @Override
    public GameScreen getScreen() {
        return currentScreen();
    }

    public Stage getStage() {
        GameScreen screen = currentScreen();
        if (screen instanceof Stage) {
            return (Stage) screen;

        }
        return null;
    }

    private void notifyScreenChanged(GameScreen screen) {
        changed = true;
        for (ScreenChangeListener listener: screenChangeListeners) {
            listener.onScreenChanged(screen);
        }
    }

    private void notifyStageChanged(Stage stage) {
        for (ScreenChangeListener listener: screenChangeListeners) {
            listener.onStageChanged(stage);
        }
    }

    @Override
    public void presentWhenReady(StagedScreen screen, TransitionEffect effect,
                                 ScreenPresentationCallback callback) {
        if (screen == null) {
            throw new IllegalArgumentException("Staged screen must not be null");
        }
        if (screen.isDestroyed()) {
            throw new IllegalArgumentException("Staged screen is already destroyed");
        }
        if (effect == null) {
            throw new IllegalArgumentException("Transition effect must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (pendingScreen != null || entryCallback != null) {
            throw new IllegalStateException("A screen presentation is already in progress");
        }

        GameScreen gameScreen = screen.getScreen();
        if (gameScreen == null) {
            throw new IllegalArgumentException("Staged screen must contain a screen");
        }
        gameScreen.setInputEnabled(false);
        pendingScreen = new PendingScreen(screen, effect, callback);
    }

    @Override
    public void cancelPresentation() {
        destroyPendingScreen();
        destroyUnenteredScreen();
    }

    private void updatePendingScreen(double timestamp) {
        PendingScreen pending = pendingScreen;
        if (Double.isNaN(pending.startedAt)) {
            pending.startedAt = timestamp;
        }

        GameScreen screen = pending.screen.getScreen();
        boolean ready;
        try {
            if (screen.isDestroyed()) {
                throw new IllegalStateException(
                        "Screen was destroyed while waiting for readiness");
            }
            ready = screen.isReady();
        } catch (Throwable cause) {
            failPendingScreen(pending, cause);
            return;
        }

        if (ready) {
            promotePendingScreen(pending);
        } else if (timestamp - pending.startedAt >= readyTimeoutMs) {
            failPendingScreen(pending, new IllegalStateException(
                    "Screen readiness timed out: " + screen.getId()));
        }
    }

    private void promotePendingScreen(PendingScreen pending) {
        pendingScreen = null;
        try {
            pending.screen.activate();
            replaceScreen(pending.screen, pending.effect, pending.callback);
        } catch (Throwable cause) {
            if (stagedScreen != pending.screen) {
                destroy(pending.screen, cause);
            }
            pending.callback.onFailure(cause);
            return;
        }
    }

    private void failPendingScreen(PendingScreen pending, Throwable cause) {
        pendingScreen = null;
        destroy(pending.screen, cause);
        pending.callback.onFailure(cause);
    }

    private void destroyPendingScreen() {
        PendingScreen pending = pendingScreen;
        pendingScreen = null;
        if (pending != null) {
            destroy(pending.screen, null);
        }
    }

    private void replaceScreen(StagedScreen screen, TransitionEffect effect,
                               ScreenPresentationCallback callback) {

        StagedScreen previous = stagedScreen;
        stagedScreen = screen;
        transitionEffect = effect;
        entryCallback = callback;
        paused = false;
        GameScreen gameScreen = screen.getScreen();
        try {
            notifyScreenChanged(gameScreen);
            if (gameScreen instanceof Stage) {
                notifyStageChanged((Stage) gameScreen);
            }
        } finally {
            if (previous != null) {
                previous.destroy();
            }
        }
    }

    private void enterCurrentScreen() {
        ScreenPresentationCallback callback = entryCallback;
        try {
            transitionPresenter.present(transitionEffect);
            currentScreen().enter();
            currentScreen().setInputEnabled(true);
        } catch (Throwable cause) {
            changed = false;
            entryCallback = null;
            callback.onFailure(cause);
            return;
        }
        changed = false;
        entryCallback = null;
        callback.onPresented();
    }

    private void destroyUnenteredScreen() {
        if (entryCallback == null) {
            return;
        }
        entryCallback = null;
        changed = false;
        transitionEffect = null;
        StagedScreen unentered = stagedScreen;
        stagedScreen = null;
        if (unentered != null) {
            destroy(unentered, null);
        }
    }

    private void destroy(StagedScreen screen, Throwable primaryFailure) {
        try {
            screen.destroy();
        } catch (Throwable cleanupFailure) {
            if (primaryFailure != null) {
                try {
                    primaryFailure.addSuppressed(cleanupFailure);
                } catch (Throwable ignored) {
                    // Reporting the readiness failure takes priority.
                }
            }
        }
    }

    private static final class PendingScreen {
        private final StagedScreen screen;
        private final TransitionEffect effect;
        private final ScreenPresentationCallback callback;
        private double startedAt = Double.NaN;

        private PendingScreen(StagedScreen screen, TransitionEffect effect,
                              ScreenPresentationCallback callback) {
            this.screen = screen;
            this.effect = effect;
            this.callback = callback;
        }
    }

}
