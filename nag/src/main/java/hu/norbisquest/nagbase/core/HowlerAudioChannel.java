package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.howler.Howl;
import hu.norbisquest.nagbase.core.howler.HowlerLibrary;

/** One independently controlled playback, backed by a Howl. */
public final class HowlerAudioChannel extends NAGObject implements IAudioChannel {
    interface Factory {
        Howl create(Howl.Options options);
    }

    private final Factory factory;
    private IAudioChannelListener listener;
    private Howl sound;
    private Double soundId;
    private int generation;
    private int id;
    private boolean loaded;
    private boolean loop;
    private boolean autoPlay;
    private boolean playbackRequested;
    private boolean pendingAutoPlay;
    private boolean waitingForUnlock;
    private double volume = 1;

    public HowlerAudioChannel() {
        this(HowlerLibrary::create);
    }

    HowlerAudioChannel(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void load(DataResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Audio resource must not be null");
        }
        load(resource.getSafeUri().asString());
    }

    @Override
    public void load(String url) {
        if (isDestroyed()) {
            throw new IllegalStateException("Audio channel is destroyed");
        }
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Audio URL must not be empty");
        }
        release();
        int token = generation;
        pendingAutoPlay = autoPlay;
        Howl.Options options = new Howl.Options();
        options.src = new String[]{url};
        // Register handlers and assign sound before starting even a cached load.
        options.preload = false;
        options.loop = loop;
        options.volume = volume;
        options.onload = () -> {
            if (!isCurrent(token)) return;
            loaded = true;
            if (listener != null) listener.onLoad(this);
            if (isCurrent(token) && pendingAutoPlay && !playbackRequested) play(loop);
        };
        options.onplay = playbackId -> {
            if (isCurrentPlayback(token, playbackId) && playbackRequested && listener != null) {
                listener.onPlay(this);
            }
        };
        options.onpause = playbackId -> {
            if (isCurrentPlayback(token, playbackId) && !playbackRequested && listener != null) {
                listener.onPause(this);
            }
        };
        options.onend = playbackId -> {
            // Howler emits end at every loop boundary, too.
            if (!isCurrentPlayback(token, playbackId) || loop || !playbackRequested) return;
            playbackRequested = false;
            soundId = null;
            if (listener != null) listener.onEnded(this);
        };
        options.onloaderror = (playbackId, error) -> fail(token, "Audio load failed: " + error);
        options.onplayerror = (playbackId, error) -> {
            if (!isCurrentPlayback(token, playbackId) || !playbackRequested) return;
            if (waitingForUnlock) {
                fail(token, "Audio playback failed after unlock: " + error);
                return;
            }
            // Browser policy may reject the first play until a user gesture.
            waitingForUnlock = true;
            sound.once("unlock", ignored -> {
                if (!isCurrent(token) || !playbackRequested || soundId == null) return;
                sound.play(soundId);
            });
        };
        sound = factory.create(options);
        sound.load();
    }

    private boolean isCurrent(int token) {
        return !isDestroyed() && token == generation && sound != null;
    }

    private boolean isCurrentPlayback(int token, double playbackId) {
        return isCurrent(token) && soundId != null && soundId == playbackId;
    }

    private void fail(int token, String message) {
        if (!isCurrent(token)) return;
        IAudioChannelListener observer = listener;
        destroy();
        if (observer != null) observer.onError(this, message);
    }

    private void release() {
        generation++;
        loaded = false;
        playbackRequested = false;
        pendingAutoPlay = false;
        waitingForUnlock = false;
        soundId = null;
        Howl previous = sound;
        sound = null;
        if (previous != null) previous.unload();
    }

    @Override
    public void play(boolean loop) {
        setLoop(loop);
        if (isDestroyed() || sound == null || playbackRequested) return;
        pendingAutoPlay = false;
        playbackRequested = true;
        waitingForUnlock = false;
        soundId = soundId == null ? sound.play() : sound.play(soundId);
    }

    @Override
    public void pause() {
        pendingAutoPlay = false;
        playbackRequested = false;
        if (sound != null && soundId != null) sound.pause(soundId);
    }

    @Override
    public void stop() {
        pendingAutoPlay = false;
        playbackRequested = false;
        soundId = null;
        waitingForUnlock = false;
        if (sound != null) sound.stop();
    }

    @Override
    public boolean isPlaying() {
        return !isDestroyed() && playbackRequested && sound != null && soundId != null && sound.playing(soundId);
    }

    @Override
    public void setLoop(boolean loop) {
        this.loop = loop;
        if (sound != null) sound.loop(loop);
    }

    @Override
    public void setVolume(double volume) {
        if (Double.isNaN(volume) || volume < 0 || volume > 1) {
            throw new IllegalArgumentException("Audio volume must be between 0 and 1");
        }
        this.volume = volume;
        if (sound != null) sound.volume(volume);
    }

    @Override
    public void doDestroy() {
        listener = null;
        release();
    }

    @Override
    public void setChannelListener(IAudioChannelListener listener) { this.listener = listener; }
    @Override
    public boolean isLoaded() { return loaded; }
    @Override
    public boolean isLoop() { return loop; }
    @Override
    public double getVolume() { return volume; }
    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        if (!loaded) pendingAutoPlay = autoPlay;
    }
    @Override
    public boolean isAutoPlay() { return autoPlay; }
    @Override
    public void setId(int id) { this.id = id; }
    @Override
    public int getId() { return id; }
}
