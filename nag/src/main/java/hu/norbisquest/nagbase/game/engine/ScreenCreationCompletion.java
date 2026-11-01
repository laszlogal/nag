package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;

/** Enforces exactly-once ownership transfer for an asynchronous screen creation. */
final class ScreenCreationCompletion {
    private GameScreen acceptedScreen;
    private boolean completed;

    boolean isCompleted() {
        return completed;
    }

    void accept(GameScreen screen) {
        if (completed) {
            throw new IllegalStateException("Screen creation is already complete");
        }
        if (screen == null) {
            throw new IllegalArgumentException("Accepted screen must not be null");
        }
        acceptedScreen = screen;
        completed = true;
    }

    boolean fail() {
        if (completed) {
            return false;
        }
        completed = true;
        return true;
    }

    boolean discardIfCompleted(GameScreen screen) {
        if (!completed) {
            return false;
        }
        if (screen != null && screen != acceptedScreen) {
            destroyLateScreen(screen);
        }
        return true;
    }

    private void destroyLateScreen(GameScreen screen) {
        try {
            screen.destroy();
        } catch (Throwable ignored) {
            // A bad late result must not damage the accepted transition.
        }
    }
}
