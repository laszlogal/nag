package hu.norbisquest.nagbase.game;

import com.google.gwt.animation.client.AnimationScheduler;

/**
 * Main animation cycle of the game.
 */
public class GameEngine implements AnimationScheduler.AnimationCallback {
    private final int fps;
    private final EngineListener listener;
    private double fpsInterval;
    private double timeThen;
    public interface EngineListener {
        void nextFrame();
    }

    GameEngine(int fps, EngineListener listener) {
        this.fps = fps;
        this.listener = listener;
    }

    public void start() {
        fpsInterval = 1000.0 / fps;
        timeThen = App.now();
        AnimationScheduler.get().requestAnimationFrame(this);
    }

    @Override
    public void execute(double timestamp) {
        double now = App.now();
        double elapsed = now - timeThen;

        if (fpsInterval < elapsed) {
            timeThen = now - (elapsed % fpsInterval);
            listener.nextFrame();
        }
        AnimationScheduler.get().requestAnimationFrame(this);
    }
}
