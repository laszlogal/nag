package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.Destroyable;

/** A screen built off-screen and ready to be activated or destroyed. */
public interface StagedScreen extends Destroyable {
    GameScreen getScreen();

    void activate(boolean clearCurrentContent);
}
