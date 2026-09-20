package hu.norbisquest.nagbase.game.engine;

/**
 * Main animation cycle of the game.
 */
public final class GameLoop implements FrameScheduler.FrameCallback {
    public static final int MIN_FRAMES_PER_SECOND = 20;
    public static final int MAX_FRAMES_PER_SECOND = 60;

    private final LoopTarget target;
    private final FrameScheduler scheduler;
    private final double frameInterval;

    private boolean running;
    private boolean framePending;
    private double previousTime = Double.NaN;

    public GameLoop(LoopTarget target, FrameScheduler scheduler, int framesPerSecond) {
        validateFramesPerSecond(framesPerSecond);

        this.target = target;
        this.scheduler = scheduler;
        this.frameInterval = 1000.0 / framesPerSecond;
    }

    public void start() {
        if (running) {
            return;
        }

        running = true;
        previousTime = Double.NaN;
        requestNextFrame();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void onFrame(double timestamp) {
        framePending = false;

        if (!running) {
            return;
        }

        if (Double.isNaN(previousTime)) {
            previousTime = timestamp;
        } else {
            double elapsed = timestamp - previousTime;

            if (elapsed >= frameInterval) {
                previousTime =
                        timestamp - elapsed % frameInterval;
                target.update(timestamp);
            }
        }

        requestNextFrame();
    }

    private void requestNextFrame() {
        if (running && !framePending) {
            framePending = true;
            scheduler.requestFrame(this);
        }
    }

    private static void validateFramesPerSecond(int fps) {
        if (fps < MIN_FRAMES_PER_SECOND
                || fps > MAX_FRAMES_PER_SECOND) {
            throw new IllegalArgumentException(
                    "fps must be between "
                            + MIN_FRAMES_PER_SECOND
                            + " and "
                            + MAX_FRAMES_PER_SECOND
            );
        }
    }
}