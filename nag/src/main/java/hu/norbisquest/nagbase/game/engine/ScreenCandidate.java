package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;

/** A screen created outside the active UI and ready to be committed or discarded. */
public interface ScreenCandidate {
    GameScreen getScreen();

    void activate(boolean clearCurrentContent);

    void discard();
}
