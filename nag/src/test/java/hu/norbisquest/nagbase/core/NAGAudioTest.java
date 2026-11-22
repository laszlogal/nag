package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NAGAudioTest {
    private final List<Channel> channels = new ArrayList<>();
    private final NAGAudio audio = new NAGAudio(() -> {
        Channel channel = new Channel();
        channels.add(channel);
        return channel;
    });
    private final DataResource resource = (DataResource) Proxy.newProxyInstance(
            DataResource.class.getClassLoader(), new Class<?>[]{DataResource.class},
            (proxy, method, arguments) -> method.getName().equals("getName") ? "music" : null);

    @Test
    public void configuresChannelBeforeLoadingAndKeepsVolumeAcrossReplacement() {
        audio.setVolume(0.3);
        audio.load(resource, 7, true, false);
        Channel first = channels.get(0);
        assertEquals(7, first.id);
        assertTrue(first.autoPlay);
        assertFalse(first.loopAtLoad);
        assertEquals(0.3, first.volumeAtLoad, 0);
        audio.stop();
        audio.play();
        assertTrue(first.destroyed);
        assertEquals(0.3, channels.get(1).volumeAtLoad, 0);
    }

    @Test
    public void togglesOffAndBackOnEvenAfterChannelWasDestroyed() {
        audio.load(resource, 0, true, true);
        assertFalse(audio.toggle());
        assertTrue(channels.get(0).destroyed);
        assertTrue(audio.toggle());
        assertEquals(2, channels.size());
        assertTrue(channels.get(1).playing);
    }

    @Test
    public void sameMusicContinuesButStoppedMusicCanBeLoadedAgain() {
        audio.load(resource, 0, true, true);
        audio.load(resource, 0, true, true);
        assertEquals(1, channels.size());
        audio.stop();
        audio.load(resource, 0, true, true);
        assertEquals(2, channels.size());
    }

    @Test
    public void enablingBeforeAnyResourceIsLoadedDoesNotCreateChannel() {
        audio.setOn(true);
        assertFalse(audio.toggle());
        assertTrue(channels.isEmpty());
    }

    private static class Channel implements IAudioChannel {
        int id;
        boolean autoPlay, loop, loopAtLoad, playing, destroyed;
        double volume, volumeAtLoad;
        @Override public void setChannelListener(IAudioChannelListener listener) {}
        @Override public void load(DataResource resource) { loopAtLoad = loop; volumeAtLoad = volume; playing = autoPlay; }
        @Override public void load(String url) {}
        @Override public void play(boolean loop) { playing = true; }
        @Override public void pause() { playing = false; }
        @Override public void stop() { playing = false; }
        @Override public void destroy() { destroyed = true; }
        @Override public boolean isDestroyed() { return destroyed; }
        @Override public boolean isPlaying() { return playing; }
        @Override public boolean isLoaded() { return true; }
        @Override public boolean isLoop() { return loop; }
        @Override public void setLoop(boolean loop) { this.loop = loop; }
        @Override public void setId(int id) { this.id = id; }
        @Override public int getId() { return id; }
        @Override public void setVolume(double volume) { this.volume = volume; }
        @Override public double getVolume() { return volume; }
        @Override public boolean isAutoPlay() { return autoPlay; }
        @Override public void setAutoPlay(boolean autoPlay) { this.autoPlay = autoPlay; }
    }
}
