package hu.norbisquest.nagbase.game.engine;

public interface ScreenCreationCallback {
    void onSuccess(ScreenCandidate candidate);

    void onFailure(Throwable cause);
}
