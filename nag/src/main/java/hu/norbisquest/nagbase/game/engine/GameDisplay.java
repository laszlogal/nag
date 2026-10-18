package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameDisplay extends NAGObject implements ScreenHost, LoopTarget {

    private ScreenHandle screenHandle;
    private boolean changed;
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
        if (isScreenChanged()) {
            currentScreen().show();
            changed = false;
        } else {
            executeScreen(timestamp);
        }
    }

    private GameScreen currentScreen() {
        return screenHandle != null ? screenHandle.getScreen() : null;
    }

    private void executeScreen(double timestamp) {
        if (isScreenValid()) {
            currentScreen().execute(timestamp);
        }
    }

    @Override
    protected void doDestroy() {
        ScreenHandle current = screenHandle;
        screenHandle = null;
        changed = false;
        if (current != null) {
            current.destroy();
        }
    }

    @Override
    public GameScreen getScreen() {
        return screenHandle != null ? screenHandle.getScreen() : null;
    }

    public Stage getStage() {
        GameScreen screen = currentScreen();
        if (screen instanceof Stage) {
            return (Stage) screen;

        }
        return null;
    }

    private boolean isScreenValid() {
        if (screenHandle == null) {
            return false;
        }
        GameScreen screen = screenHandle.getScreen();
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
    public void replaceScreen(ScreenHandle handle) {
        if (handle == null) {
            throw new IllegalArgumentException("Screen handle must not be null");
        }
        if (handle.isDestroyed()) {
            throw new IllegalArgumentException("Screen handle is already destroyed");
        }

        ScreenHandle previous = screenHandle;
        screenHandle = handle;
        GameScreen screen = screenHandle.getScreen();
        try {
            notifyScreenChanged(screen);
            if (screen instanceof Stage) {
                notifyStageChanged((Stage) screen);
            }
        } finally {
            if (previous != null) {
                previous.destroy();
            }
        }
    }

    @Override
    public void onScreenCreationFailed(Stage.Id id, Throwable cause) {
        String message = cause == null ? "unknown error" : cause.getMessage();
        hu.norbisquest.nagbase.game.App.error(
                "Failed to create screen " + id + ": " + message);
    }
}
