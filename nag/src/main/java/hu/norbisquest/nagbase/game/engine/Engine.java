package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameEngine;
import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage;

public class Engine implements GameEngine {
    private final GameLoop gameLoop;
    private GameDisplay display;

    public Engine(GameLoop gameLoop, GameDisplay display) {
        this.gameLoop = gameLoop;
        this.display = display;
    }

    @Override
    public void addChangeScreenHandler(ChangeScreen handler) {
        display.addChangeHandler(handler);
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
        gameLoop.start();
    }
}
