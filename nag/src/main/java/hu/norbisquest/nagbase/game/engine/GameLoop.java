package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.animation.client.AnimationScheduler;
import hu.norbisquest.nagbase.game.App;

/**
 * Main animation cycle of the game.
 */
public class GameLoop implements AnimationScheduler.AnimationCallback {
    public static final int MIN_FRAME_PER_SECOND = 20;
    public static final int MAX_FRAME_PER_SECOND = 60;
    private final int fps;
    private final GameDisplay display;
    private double fpsInterval;
    private double timeThen;
    private LoopState state = LoopState.STOPPED;
    private enum LoopState {STOPPED, RUNNING}
    public GameLoop(GameDisplay display, int fps) {
        this.display = display;
        this.fps = validateFPS(fps);
    }

    private int validateFPS(int fps) {
        if (fps < MIN_FRAME_PER_SECOND || fps > MAX_FRAME_PER_SECOND) {
            throw new IllegalArgumentException("fps must be between " + MIN_FRAME_PER_SECOND
            + " and  " + MAX_FRAME_PER_SECOND);
        }

        return fps;
    }

    public void start() {
        if (state == LoopState.RUNNING) {
            return;
        }
        fpsInterval = 1000.0 / fps;
        timeThen = App.now();
        AnimationScheduler.get().requestAnimationFrame(this);
        state = LoopState.RUNNING;
    }

    public void stop() {
        state = LoopState.STOPPED;
    }

    @Override
    public void execute(double now) {
        if (state != LoopState.RUNNING) {
            return;
        }
        double elapsed = now - timeThen;

        if (fpsInterval < elapsed) {
            timeThen = now - (elapsed % fpsInterval);
            display.process(now);
        }
        AnimationScheduler.get().requestAnimationFrame(this);
    }
}
