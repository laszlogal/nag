package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.engine.ChangeScreen;

public interface GameEngine {
    void addChangeScreenHandler(ChangeScreen handler);

    Screen getCurrentScreen();

    Stage getCurrentStage();

    void run();
}
