package hu.norbisquest.nagbase.game.engine;

public interface FrameScheduler {
    void requestFrame(FrameCallback callback);
    interface FrameCallback {
        void onFrame(double timestamp);
    }
}
