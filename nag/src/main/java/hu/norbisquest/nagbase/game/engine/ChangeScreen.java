package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.game.Stage;

public interface ChangeScreen {
    void onScreenChanged(GameScreen screen);
    void onStageChanged(Stage stage);
}
