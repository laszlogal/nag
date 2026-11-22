package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;

public interface IAudioChannel extends Destroyable {

    interface IAudioChannelListener {
        void onLoad(IAudioChannel channel);
        void onPlay(IAudioChannel channel);
        void onPause(IAudioChannel channel);
        void onEnded(IAudioChannel channel);

        /** A terminal load/play failure; the channel has released its resources. */
        default void onError(IAudioChannel channel, String message) {}
    }

	void setChannelListener(IAudioChannelListener listener);

	void load(DataResource res);

	void load(String url);

	void play(boolean loop);

	boolean isLoop();

	boolean isPlaying();

	void pause();

	void setId(int id);

	boolean isLoaded();

	void setVolume(double value);

	boolean isAutoPlay();

	void setAutoPlay(boolean autoPlay);

	double getVolume();

	void setLoop(boolean loop);

    void stop();

    int getId();
}
