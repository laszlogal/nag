package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.game.Stage;

public interface ScreenNavigation {
    /**
     * Loads a new screen with the given id. Implement this interface
     * everywhere you want to change screen: menu screens, stages, gui game
     * loop, etc.
     *
     * @param id
     *            The next screen id.
     */
    void navigateTo(Stage.Id id);

    void navigateTo(Stage.Id id, boolean clear);
}
