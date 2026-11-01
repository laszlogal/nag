package hu.norbisquest.nagbase.game.engine;

/** Receives the terminal result of one staged-screen presentation. */
public interface ScreenPresentationCallback {
    void onPresented();

    void onFailure(Throwable cause);
}
