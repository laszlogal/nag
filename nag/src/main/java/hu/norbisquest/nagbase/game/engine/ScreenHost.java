package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.game.Stage;

public interface ScreenHost {
    GameScreen getScreen();

    void replaceScreen(GameScreen screen);

    void onScreenCreationFailed(Stage.Id id, Throwable cause);
}
