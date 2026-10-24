package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
public interface ScreenHost {
    GameScreen getScreen();

    /** Stops updates and input on the outgoing screen until replacement. */
    void pauseScreen();

    /** Takes ownership of {@code screen} when invoked. */
    void replaceScreen(StagedScreen screen);
}
