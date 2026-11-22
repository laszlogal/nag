package hu.norbisquest.nagbase.core;

import hu.norbisquest.nagbase.core.howler.Howl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HowlerAudioChannelTest {
    private final List<FakeHowl> sounds = new ArrayList<>();
    private final HowlerAudioChannel channel = new HowlerAudioChannel(options -> {
        FakeHowl sound = new FakeHowl(options);
        sounds.add(sound);
        return sound;
    });
    private final Events events = new Events();

    private FakeHowl load() {
        channel.setChannelListener(events);
        channel.load("sound.mp3");
        return sounds.get(sounds.size() - 1);
    }

    @Test
    public void autoplayWaitsForLoadAndUsesConfiguredLoopAndVolume() {
        channel.setAutoPlay(true);
        channel.setLoop(true);
        channel.setVolume(0.4);
        FakeHowl sound = load();
        assertFalse(sound.options.preload);
        assertTrue(sound.options.loop);
        assertEquals(0.4, sound.options.volume, 0);
        assertEquals(0, sound.playCount);
        assertFalse(channel.isLoaded());
        sound.options.onload.call();
        assertTrue(channel.isLoaded());
        assertEquals(1, sound.playCount);
        assertEquals(1, events.loads);
    }

    @Test
    public void listenerStartingPlaybackDoesNotCauseDuplicateAutoplay() {
        channel.setAutoPlay(true);
        channel.setChannelListener(new Events() {
            @Override public void onLoad(IAudioChannel audio) { audio.play(false); }
        });
        channel.load("sound.mp3");
        sounds.get(0).options.onload.call();
        assertEquals(1, sounds.get(0).playCount);
    }

    @Test
    public void pauseResumesSamePlaybackAndStopStartsNewPlayback() {
        FakeHowl sound = load();
        channel.play(false);
        assertTrue(channel.isPlaying());
        channel.play(false);
        assertEquals(1, sound.playCount);
        channel.pause();
        assertFalse(channel.isPlaying());
        sound.options.onpause.call(1);
        assertEquals(1, events.pauses);
        channel.play(false);
        assertEquals(1, sound.resumedId, 0);
        channel.stop();
        assertFalse(channel.isPlaying());
        sound.options.onend.call(1);
        assertEquals(0, events.ends);
        channel.play(false);
        assertEquals(2, sound.nextId, 0);
    }

    @Test
    public void stoppingDuringLoadCancelsAutoplay() {
        channel.setAutoPlay(true);
        FakeHowl sound = load();
        channel.stop();
        sound.options.onload.call();
        assertEquals(0, sound.playCount);
    }

    @Test
    public void disablingAutoplayDuringLoadCancelsIt() {
        channel.setAutoPlay(true);
        FakeHowl sound = load();
        channel.setAutoPlay(false);
        sound.options.onload.call();
        assertEquals(0, sound.playCount);
    }

    @Test
    public void latePlaybackErrorDoesNotAffectNewPlayback() {
        FakeHowl sound = load();
        channel.play(false);
        channel.stop();
        channel.play(false);
        sound.options.onplayerror.call(1, "old playback failed");
        assertNull(sound.unlock);
        assertEquals(0, events.errors);
        assertTrue(channel.isPlaying());
    }

    @Test
    public void loopBoundariesAreNotCompletionButOneShotEndIs() {
        FakeHowl sound = load();
        channel.play(true);
        sound.options.onend.call(1);
        assertEquals(0, events.ends);
        channel.stop();
        channel.play(false);
        sound.options.onend.call(2);
        sound.options.onend.call(2);
        assertEquals(1, events.ends);
        assertFalse(channel.isPlaying());
    }

    @Test
    public void reloadAndDestroyUnloadAndIgnoreLateCallbacks() {
        channel.setAutoPlay(true);
        FakeHowl first = load();
        FakeHowl second = load();
        assertEquals(1, first.unloads);
        first.options.onload.call();
        first.options.onloaderror.call(null, "late error");
        assertEquals(0, events.loads);
        assertEquals(0, events.errors);
        channel.destroy();
        channel.destroy();
        assertEquals(1, second.unloads);
        second.options.onload.call();
        assertFalse(channel.isLoaded());
        assertEquals(0, second.playCount);
    }

    @Test
    public void loadFailureReleasesResourcesAndReportsFailure() {
        FakeHowl sound = load();
        sound.options.onloaderror.call(null, "404");
        assertEquals(1, events.errors);
        assertEquals(1, sound.unloads);
        assertFalse(channel.isLoaded());
        assertFalse(channel.isPlaying());
    }

    @Test
    public void blockedPlaybackRetriesOnUnlockUnlessStopped() {
        FakeHowl sound = load();
        channel.play(false);
        sound.options.onplayerror.call(1, "locked");
        assertNotNull(sound.unlock);
        sound.unlock.call(0);
        assertEquals(1, sound.resumedId, 0);
        assertEquals(0, events.errors);
        channel.stop();
        sound.unlock.call(0);
        assertEquals(2, sound.playCount);
    }

    @Test
    public void failedUnlockRetryReportsFailureInsteadOfRetryingForever() {
        FakeHowl sound = load();
        channel.play(false);
        sound.options.onplayerror.call(1, "locked");
        sound.unlock.call(0);
        sound.options.onplayerror.call(1, "still failed");
        assertEquals(1, events.errors);
        assertEquals(1, sound.unloads);
    }

    private static class Events implements IAudioChannel.IAudioChannelListener {
        int loads, pauses, ends, errors;
        @Override public void onLoad(IAudioChannel channel) { loads++; }
        @Override public void onPlay(IAudioChannel channel) {}
        @Override public void onPause(IAudioChannel channel) { pauses++; }
        @Override public void onEnded(IAudioChannel channel) { ends++; }
        @Override public void onError(IAudioChannel channel, String message) { errors++; }
    }

    private static class FakeHowl extends Howl {
        final Options options;
        int playCount, unloads;
        double nextId, resumedId;
        boolean playing;
        SoundCallback unlock;
        FakeHowl(Options options) { super(options); this.options = options; }
        @Override public Howl load() { return this; }
        @Override public double play() { playCount++; playing = true; return ++nextId; }
        @Override public double play(double id) { playCount++; resumedId = id; playing = true; return id; }
        @Override public Howl pause(double id) { playing = false; return this; }
        @Override public Howl stop() { playing = false; return this; }
        @Override public boolean playing(double id) { return playing; }
        @Override public Howl loop(boolean loop) { return this; }
        @Override public Howl volume(double volume) { return this; }
        @Override public Howl unload() { unloads++; return this; }
        @Override public Howl once(String event, SoundCallback callback) { unlock = callback; return this; }
    }
}
