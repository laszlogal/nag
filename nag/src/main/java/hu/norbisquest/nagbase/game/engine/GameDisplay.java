package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameDisplay extends NAGObject implements ScreenHost, LoopTarget {

    private GameScreen screen;
    private boolean changed;
    private List<ChangeScreen> changeHandlers = new ArrayList<>();

    public GameDisplay() {
        changed = false;
    }

    void addChangeHandler(ChangeScreen handler) {
        if (!changeHandlers.contains(handler)) {
            changeHandlers.add(handler);
        }
    }


    private void setScreen(GameScreen screen) {
        this.screen = screen;
        notifyScreenChanged(screen);
        if (screen instanceof Stage) {
            notifyStageChanged((Stage) screen);
        }
    }

    @Override
    public void update(double timestamp) {
        if (isScreenChanged()) {
            screen.show();
            changed = false;
        } else {
            executeScreen(timestamp);
        }
    }

   private void executeScreen(double timestamp) {
        if (isScreenValid()) {
            screen.execute(timestamp);
        }
    }

    @Override
    protected void doDestroy() {
        if (screen != null) {
            screen.destroy();
        }
        screen = null;
    }

    private void destroyCurrentScreen() {
        if (screen == null) {
            return;
        }
        screen.destroy();
        screen = null;
    }

    @Override
    public GameScreen getScreen() {
        return screen;
    }

    public Stage getStage() {
        if (screen instanceof Stage) {
            return (Stage) screen;

        }
        return null;
    }

    private boolean isScreenValid() {
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
    public void replaceScreen(GameScreen screen) {
        destroyCurrentScreen();
        setScreen(screen);
    }

    @Override
    public void onScreenCreationFailed(Stage.Id id, Throwable cause) {
        String message = cause == null ? "unknown error" : cause.getMessage();
        hu.norbisquest.nagbase.game.App.error(
                "Failed to create screen " + id + ": " + message);
    }
}
