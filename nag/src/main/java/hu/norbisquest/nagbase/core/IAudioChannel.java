package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.AudioChannel.IAudioChannelListener;

public interface IAudioChannel extends Destroyable {

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