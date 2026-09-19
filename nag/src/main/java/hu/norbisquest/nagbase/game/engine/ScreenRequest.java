package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.game.Stage;

public interface ScreenRequest {
    void onScreenRequest(Stage.Id id);
    void onScreenCreated(GameScreen screen);

}
