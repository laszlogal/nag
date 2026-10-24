package hu.norbisquest.nagbase.game.engine;

public interface ScreenCreationCallback {
    void onSuccess(StagedScreen screen);

    void onFailure(Throwable cause);
}
