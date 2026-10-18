package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.game.Stage;

public interface ScreenHost {
    GameScreen getScreen();

    /** Takes ownership of {@code handle} when invoked. */
    void replaceScreen(ScreenHandle handle);

    void onScreenCreationFailed(Stage.Id id, Throwable cause);
}
