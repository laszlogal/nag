package hu.norbisquest.nagbase.game.engine;

/** A screen created outside the active UI and ready to be committed or discarded. */
public interface ScreenCandidate {
    void activate(boolean clearCurrentContent);

    void discard();

    ScreenHandle getHandle();

    ScreenHandle takeHandle();
}
