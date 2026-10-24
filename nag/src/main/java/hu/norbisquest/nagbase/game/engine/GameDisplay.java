package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameDisplay extends NAGObject implements ScreenHost, LoopTarget {

    private StagedScreen stagedScreen;
    private boolean changed;
    private boolean paused;
    private final List<ChangeScreen> changeHandlers = new ArrayList<>();

    public GameDisplay() {
        changed = false;
    }

    void addChangeHandler(ChangeScreen handler) {
        if (!changeHandlers.contains(handler)) {
            changeHandlers.add(handler);
        }
    }


    @Override
    public void update(double timestamp) {
        if (paused) {
            return;
        }
        if (isScreenChanged()) {
            currentScreen().show();
            changed = false;
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
        if (isScreenValid()) {
            currentScreen().execute(timestamp);
        }
    }

    @Override
    protected void doDestroy() {
        StagedScreen current = stagedScreen;
        stagedScreen = null;
        changed = false;
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

    private boolean isScreenValid() {
        if (stagedScreen == null) {
            return false;
        }
        GameScreen screen = stagedScreen.getScreen();
        return screen != null && screen.isValid();
    }

    private boolean isScreenChanged() {
        return isScreenValid() && changed;
    }

    private void notifyScreenChanged(GameScreen screen) {
        changed = true;
        for (ChangeScreen handler: changeHandlers) {
            handler.onScreenChanged(screen);
        }
    }

    private void notifyStageChanged(Stage stage) {
        for (ChangeScreen handler: changeHandlers) {
            handler.onStageChanged(stage);
        }
    }

    @Override
    public void replaceScreen(StagedScreen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Staged screen must not be null");
        }
        if (screen.isDestroyed()) {
            throw new IllegalArgumentException("Staged screen is already destroyed");
        }

        StagedScreen previous = stagedScreen;
        stagedScreen = screen;
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

}
