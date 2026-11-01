package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameEngine;
import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage;

public class Engine implements GameEngine {
    private final GameLoop gameLoop;
    private final GameDisplay display;
    private final ScreenCoordinator screenCoordinator;

    public Engine(GameLoop gameLoop, GameDisplay display, ScreenCoordinator screenCoordinator) {
        this.gameLoop = gameLoop;
        this.display = display;
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void addScreenChangeListener(ScreenChangeListener listener) {
        display.addScreenChangeListener(listener);
    }

    @Override
    public Screen getCurrentScreen() {
        return (Screen) display.getScreen();
    }

    @Override
    public Stage getCurrentStage() {
        return display.getStage();
    }

    @Override
    public void run() {
        if (screenCoordinator.getState() != ScreenCoordinator.State.STOPPED) {
            gameLoop.start();
        }
    }

    @Override
    public void stop() {
        gameLoop.stop();
        screenCoordinator.stop();
        display.destroy();
    }
}
