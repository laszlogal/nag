package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public class  GameDisplay extends NAGObject implements ScreenRequest, LoopTarget {

    private GameScreen screen;
    private boolean changed;
    private List<ChangeScreen> changeHandlers = new ArrayList<>();

    enum DefaultID implements Stage.Id {
        DEFAULT_ID;

        @Override
        public Stage.Id toId(String name) {
            return null;
        }

    }
    public GameDisplay() {
        changed = false;
    }

    void addChangeHandler(ChangeScreen handler) {
        if (!changeHandlers.contains(handler)) {
            changeHandlers.add(handler);
        }
    }


    public void setScreen(GameScreen screen) {
        this.screen = screen;
        notifyScreenChanged(screen);
        if (screen instanceof Stage) {
            notifyStageChanged((Stage) screen);
        }
    }

    void process(double timestamp) {
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
        screen.destroy();
        screen = null;
    }

    private Stage.Id getCurrentId() {
        return isScreenValid() ? screen.getId() : DefaultID.DEFAULT_ID;
    }

    private void destroyCurrentScreen() {
        if (screen == null) {
            return;
        }
        screen.destroy();
        screen = null;
    }

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
    public void onScreenRequest(Stage.Id id) {
        Stage.Id referer = null;
        if (isValid()) {
            referer = getCurrentId();
            destroyCurrentScreen();
            NAGImage.clearCache();
        }

        App.getSettings().setReferer(referer);
        App.getSettings().setScreenId(id);
    }

    @Override
    public void onScreenCreated(GameScreen screen) {
        setScreen(screen);
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
}