package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;

import java.util.function.Supplier;

public class NAGAudio extends NAGObject {
    private final Supplier<IAudioChannel> channelFactory;
    private double volume = 1;
    private DataResource resource;
    private IAudioChannel channel;
    private int id;
    private boolean autoPlay;
    private boolean loop = false;

    NAGAudio() {
        this(AudioUtils::createAudioChannel);
    }

    NAGAudio(Supplier<IAudioChannel> channelFactory) {
        this.channelFactory = channelFactory;
    }

    public void load(DataResource res, int id, boolean autoPlay, boolean loop) {
        if (sameResource(res) && isChannelValid()) {
            return;
        }

        doDestroy();

        this.resource = res;
        this.autoPlay = autoPlay;
        this.loop = loop;
        this.id = id;
        createChannel();
    }

    private void destroyChannel() {
        if (channel != null) {
            channel.stop();
            channel.destroy();
            channel = null;
        }
    }

    private void createChannel() {
        if (resource == null) return;
        channel = channelFactory.get();
        channel.setId(id);
        channel.setAutoPlay(autoPlay);
        channel.setLoop(loop);
        channel.setVolume(volume);
        channel.load(resource);
    }

    @Override
    public void doDestroy() {
        destroyChannel();
        channel = null;
        resource = null;
    }

    private boolean sameResource(DataResource res) {
        if (resource == null && res == null) {
            return true;
        }

        return res != null  && res.getName().equals(getName());
    }

    public void setResource(DataResource resource) {
        this.resource = resource;
    }

    public String getName() {
        return resource != null ? resource.getName(): "";
    }

    public void stop() {
        if (!isChannelValid()) {
            return;
        }

        if (isPlaying()) {
            channel.stop();
        }
        destroyChannel();
    }

    private boolean isChannelValid() {
        return channel != null && !channel.isDestroyed();
    }

    public void pause() {
        if (!isValid()) {
            return;
        }
        channel.pause();
    }

    public void play() {
        if (resource == null) return;
        if (!isChannelValid()) {
            createChannel();
        }

        channel.play(loop);
    }

    void setOn(boolean on) {
        if (on) {
            play();
        } else {
            stop();
        }
    }

    boolean isPlaying() {
        return isChannelValid() && channel.isPlaying();
    }

    boolean toggle() {
        if (resource == null) return false;
        boolean enabled = !isPlaying();
        setOn(enabled);
        return enabled;
    }

    void setVolume(double volume) {
        if (Double.isNaN(volume) || volume < 0 || volume > 1) {
            throw new IllegalArgumentException("Audio volume must be between 0 and 1");
        }
        this.volume = volume;
        if (isChannelValid()) {
            channel.setVolume(volume);
        }
    }

    double getVolume() {
        return volume;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && isChannelValid() && resource != null;
    }
}
