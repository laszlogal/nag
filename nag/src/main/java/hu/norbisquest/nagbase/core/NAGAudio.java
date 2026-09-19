package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;


    public class NAGAudio extends NAGObject {
    private DataResource resource;
    private IAudioChannel channel;
    private int id;
    private boolean autoPlay;
    private boolean loop = false;

    NAGAudio() {
        resource = null;
        channel = null;
    }

    public void load(DataResource res, int id, boolean autoPlay, boolean loop) {
        if (sameResource(res)) {
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
        channel = AudioUtils.createAudioChannel(resource, id, autoPlay);
        channel.setLoop(loop);
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
        if (resource != null && isChannelValid()) {
            setOn(!isPlaying());
            return !isPlaying();
        }
        return false;
    }

    void setVolume(double volume) {
        if (isChannelValid()) {
            channel.setVolume(volume);
        }
    }

    double getVolume() {
        return isChannelValid() ? channel.getVolume(): -1;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && isChannelValid() && resource != null;
    }
}
