package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.game.Stage;

public interface ScreenListener {
    /**
     * Loads a new screen with the given id. Implement this interface
     * everywhere you want to change screen: menu screens, stages, gui game
     * loop, etc.
     *
     * @param id
     *            The next screen id.
     */
    void changeScreen(Stage.Id id);

    void changeScreen(Stage.Id id, boolean clear);
}
