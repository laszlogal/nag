package hu.norbisquest.nagbase.game.engine;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class GameLoopTest {

    private static final int FPS = 60;

    @Test
    public void rejectsFrameRateBelowMinimum() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameLoop(
                        new RecordingTarget(),
                        new ManualFrameScheduler(),
                        GameLoop.MIN_FRAMES_PER_SECOND - 1));
    }

    @Test
    public void rejectsFrameRateAboveMaximum() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameLoop(
                        new RecordingTarget(),
                        new ManualFrameScheduler(),
                        GameLoop.MAX_FRAMES_PER_SECOND + 1));
    }

    @Test
    public void acceptsBoundaryFrameRates() {
        new GameLoop(
                new RecordingTarget(),
                new ManualFrameScheduler(),
                GameLoop.MIN_FRAMES_PER_SECOND);
        new GameLoop(
                new RecordingTarget(),
                new ManualFrameScheduler(),
                GameLoop.MAX_FRAMES_PER_SECOND);
    }

    @Test
    public void startRequestsOneFrameAndIsIdempotent() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        GameLoop loop = new GameLoop(new RecordingTarget(), scheduler, FPS);

        loop.start();
        loop.start();

        assertEquals(1, scheduler.getRequestCount());
        assertEquals(1, scheduler.getPendingFrameCount());
    }

    @Test
    public void firstFrameEstablishesTimeWithoutUpdatingTarget() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        RecordingTarget target = new RecordingTarget();
        GameLoop loop = new GameLoop(target, scheduler, FPS);

        loop.start();
        scheduler.fireNextFrame(100.0);

        assertEquals(0, target.getUpdateCount());
        assertEquals(1, scheduler.getPendingFrameCount());
    }

    @Test
    public void updatesOnlyAfterFrameIntervalAndForwardsTimestamp() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        RecordingTarget target = new RecordingTarget();
        GameLoop loop = new GameLoop(target, scheduler, FPS);

        loop.start();
        scheduler.fireNextFrame(100.0);
        scheduler.fireNextFrame(110.0);
        scheduler.fireNextFrame(117.0);

        assertEquals(1, target.getUpdateCount());
        assertEquals(117.0, target.getTimestamp(0), 0.0);
        assertEquals(1, scheduler.getPendingFrameCount());
    }

    @Test
    public void stopPreventsPendingFrameFromUpdatingOrRescheduling() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        RecordingTarget target = new RecordingTarget();
        GameLoop loop = new GameLoop(target, scheduler, FPS);

        loop.start();
        loop.stop();
        scheduler.fireNextFrame(100.0);

        assertEquals(0, target.getUpdateCount());
        assertEquals(0, scheduler.getPendingFrameCount());
        assertEquals(1, scheduler.getRequestCount());
    }

    @Test
    public void restartReusesAlreadyPendingFrameInsteadOfCreatingSecondLoop() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        RecordingTarget target = new RecordingTarget();
        GameLoop loop = new GameLoop(target, scheduler, FPS);

        loop.start();
        loop.stop();
        loop.start();

        assertEquals(1, scheduler.getRequestCount());
        assertEquals(1, scheduler.getPendingFrameCount());

        scheduler.fireNextFrame(100.0);

        assertEquals(0, target.getUpdateCount());
        assertEquals(2, scheduler.getRequestCount());
        assertEquals(1, scheduler.getPendingFrameCount());
    }

    @Test
    public void restartAfterStoppedFrameWasConsumedRequestsNewBaselineFrame() {
        ManualFrameScheduler scheduler = new ManualFrameScheduler();
        RecordingTarget target = new RecordingTarget();
        GameLoop loop = new GameLoop(target, scheduler, FPS);

        loop.start();
        loop.stop();
        scheduler.fireNextFrame(100.0);

        loop.start();
        scheduler.fireNextFrame(500.0);

        assertEquals(0, target.getUpdateCount());
        assertEquals(3, scheduler.getRequestCount());
        assertEquals(1, scheduler.getPendingFrameCount());
    }

    private static final class RecordingTarget implements LoopTarget {
        private final List<Double> timestamps = new ArrayList<>();

        @Override
        public void update(double timestamp) {
            timestamps.add(timestamp);
        }

        int getUpdateCount() {
            return timestamps.size();
        }

        double getTimestamp(int index) {
            return timestamps.get(index);
        }
    }

    private static final class ManualFrameScheduler implements FrameScheduler {
        private final Deque<FrameCallback> pendingFrames = new ArrayDeque<>();
        private int requestCount;

        @Override
        public void requestFrame(FrameCallback callback) {
            requestCount++;
            pendingFrames.addLast(callback);
        }

        void fireNextFrame(double timestamp) {
            if (pendingFrames.isEmpty()) {
                throw new AssertionError("No frame is pending");
            }
            pendingFrames.removeFirst().onFrame(timestamp);
        }

        int getRequestCount() {
            return requestCount;
        }

        int getPendingFrameCount() {
            return pendingFrames.size();
        }
    }
}
