package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.AudioManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CmdAudioTest {
    @Test
    public void pausesOnlyTheInjectedManagerWhenCommandStarts() {
        RecordingAudioManager first = new RecordingAudioManager();
        RecordingAudioManager second = new RecordingAudioManager();

        Command firstPause = Cmd.pauseAmbient(first);
        Command secondPause = Cmd.pauseAmbient(second);
        assertEquals(0, first.pauseCount);
        assertEquals(0, second.pauseCount);

        firstPause.start();
        assertEquals(1, first.pauseCount);
        assertEquals(0, second.pauseCount);

        secondPause.start();
        assertEquals(1, first.pauseCount);
        assertEquals(1, second.pauseCount);
    }

    private static class RecordingAudioManager extends AudioManager {
        private int pauseCount;

        @Override
        public void pauseAmbientNoise() {
            pauseCount++;
        }
    }
}
