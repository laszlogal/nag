package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.TransitionEffect;

public interface ScreenHost {
    GameScreen getScreen();

    /** Stops updates and input on the outgoing screen until replacement. */
    void pauseScreen();

    /**
     * Takes ownership after this method returns successfully and presents
     * {@code screen} after it becomes ready.
     */
    void presentWhenReady(StagedScreen screen, TransitionEffect effect,
                          ScreenPresentationCallback callback);

    /** Cancels and destroys any presentation that has not completed entry. */
    void cancelPresentation();
}
