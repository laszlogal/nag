package hu.norbisquest.nagbase.core;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.dom.client.EndedEvent;
import com.google.gwt.event.dom.client.EndedHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.RootPanel;

public class AudioChannel extends NAGObject implements EndedHandler, IAudioChannel {
	/**
	 * Interface to give feedback to the listener object from the status of
	 * audio playback.
	 * 
	 * @author Lászlo Gál
	 *
	 */
	@SuppressWarnings("EmptyMethod")
    public interface IAudioChannelListener {
		/** Called when audio file is loaded */
		void onLoad(IAudioChannel channel);

		/** Called when audio starts to play */
		void onPlay(IAudioChannel channel);

		/** Called when audio is paused */
		void onPause(IAudioChannel channel);

		/** Called when audio play stops at the end */
		void onEnded(IAudioChannel channel);

	}

	private Audio audio;
	private IAudioChannelListener listener;
	private static String MIME_TYPE = canPlayMp3() ? AudioElement.TYPE_MP3 : AudioElement.TYPE_OGG;
	private boolean loaded;
	private boolean autoPlay;
	private static int count = 0;

	private AudioChannel(boolean autoPlay) {
		audio = Audio.createIfSupported();
		audio.getAudioElement().setId("audio" + count);
		count++;
		RootPanel.get().add(audio);
		audio.addEndedHandler(this);
		// addNativeEndedListener(audio.getAudioElement());
		audio.setVolume(1);
		loaded = false;
		this.setAutoPlay(autoPlay);
	}

	AudioChannel() {
		this(false);
	}

	private native void addNativeEndedListener(JavaScriptObject obj) /*-{
		obj.addEventListener('ended', function() {
		});
	}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#setChannelListener(nag.core.AudioChannel.
	 * IAudioChannelListener)
	 */
	@Override
	public void setChannelListener(IAudioChannelListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nag.core.IAudioChannel#load(com.google.gwt.resources.client.DataResource)
	 */
	@Override
	public void load(DataResource res) {
		loaded = false;
		// ugly hack
		String url = res.getSafeUri().asString();
		load(url.replace("content/unknown", MIME_TYPE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#load(java.lang.String)
	 */
	@Override
	public void load(String url) {
		audio.setSrc(url);
		audio.addLoadedMetadataHandler(event -> {
            loaded = true;
            if (isAutoPlay()) {
                play(isLoop());
            }

            if (listener != null) {

                listener.onLoad(AudioChannel.this);

            }

        });

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#play(boolean)
	 */
	@Override
	public void play(boolean loop) {
		audio.setLoop(loop);
		audio.play();
		if (listener != null) {
			listener.onPlay(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#isLoop()
	 */
	@Override
	public boolean isLoop() {
		return audio.isLoop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		return !audio.isPaused();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#pause()
	 */
	@Override
	public void pause() {
		audio.pause();
		if (listener != null) {
			listener.onPause(this);
		}
	}

	@Override
	public void onEnded(EndedEvent event) {
		// App.debug("Playback ended.");
		if (listener != null) {
			listener.onEnded(this);
		}
	}

	private static boolean canPlayMp3() {
		Audio a = Audio.createIfSupported();
        // App.debug("Can play mp3: " + result);
		return MediaElement.CAN_PLAY_PROBABLY.equals(a.canPlayType(AudioElement.TYPE_MP3))
				|| MediaElement.CAN_PLAY_MAYBE.equals(a.canPlayType(AudioElement.TYPE_MP3));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return loaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#setVolume(double)
	 */
	@Override
	public void setVolume(double value) {
		audio.setVolume(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#isAutoPlay()
	 */
	@Override
	public boolean isAutoPlay() {
		return autoPlay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#setAutoPlay(boolean)
	 */
	@Override
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay = autoPlay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#getVolume()
	 */
	@Override
	public double getVolume() {
		return audio.getVolume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nag.core.IAudioChannel#setLoop(boolean)
	 */
	@Override
	public void setLoop(boolean loop) {
		audio.setLoop(loop);
	}

	@Override
	public void doDestroy() {
		// not used
	}

	@Override
	public void stop() {

	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub

	}

}
