package hu.norbisquest.nagbase.core;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.AudioChannel.IAudioChannelListener;
import hu.norbisquest.nagbase.game.App;

import java.util.Date;

@SuppressWarnings("UnusedReturnValue")
public class WebAudioChannel extends NAGObject implements IAudioChannel {
	private boolean autoPlay = false;
	private boolean loaded = false;
	private boolean loop = false;
	private boolean playing = false;
	private int id = 0;

	private static JavaScriptObject context = null;

	private JavaScriptObject buffer = null;

    private JavaScriptObject source;
	private JavaScriptObject gainNode = null;
	private IAudioChannelListener listener;
	private static long lastPlayed;
	private double volume = 1.0;


	private int channelNum;
	WebAudioChannel(int num) {
		channelNum = num;
		if (context == null) {
			initialize();
		}
		lastPlayed = new Date().getTime();
		App.debug("[WebAudioChannel] CREATE #" + channelNum);
	}

	private native boolean initialize() /*-{

		var contextClass = ($wnd.AudioContext || $wnd.webkitAudioContext
				|| $wnd.mozAudioContext || $wnd.oAudioContext || $wnd.msAudioContext);
		if (contextClass) {
			@hu.norbisquest.nagbase.core.WebAudioChannel::context = new contextClass();
			console.log("[WEBAUDIOAPI] API is initialized.");
			return true;
		} else {
			console.log("[WEBAUDIOAPI] API is not available.");
		}
		return false;

	}-*/;

	private native boolean closeContext() /*-{
		var context = @hu.norbisquest.nagbase.core.WebAudioChannel::context;
		if (context) {
			context.close();
		}
	}-*/;

	public void load(String url) {
		load(url, !Browser.isSafari());
	}

	private native void load(String url, boolean promise) /*-{
		var request = new XMLHttpRequest();
		request.open('GET', url, true);
		request.responseType = 'arraybuffer';

		// Decode asynchronously
		var that = this;
		var context = @hu.norbisquest.nagbase.core.WebAudioChannel::context;
		if (promise === true) {
			request.onload = function() {
				context.decodeAudioData(request.response).then(function(buffer) {
					that.@hu.norbisquest.nagbase.core.WebAudioChannel::buffer = buffer;
					that.@hu.norbisquest.nagbase.core.WebAudioChannel::onLoad()();

				}, null);
			}
		} else {
			request.onload = function() {
				context.decodeAudioData(request.response, function(buffer) {
					that.@hu.norbisquest.nagbase.core.WebAudioChannel::buffer = buffer;
					that.@hu.norbisquest.nagbase.core.WebAudioChannel::onLoad()();

				}, function(e) {$wnd.log('error decoding audio.');});
			}
		}
		request.send();
	}-*/;

	private void onLoad() {
		loaded = true;
		if (listener != null) {
			listener.onLoad(this);
		}

		if (isAutoPlay()) {
			playSound();
			playing = true;
		}
	}

    private void onPlay() {
		if (listener != null) {
			listener.onPlay(this);
		}
	}

	private void onEnded() {
		if (listener != null) {
			listener.onEnded(this);
		}
		destroy();
	}

	@Override
	public void setChannelListener(IAudioChannelListener listener) {
		this.listener = listener;
	}

	@Override
	public void load(DataResource res) {
		load(res.getSafeUri().asString());

	}

	@Override
	public void play(boolean loop) {
		long now = new Date().getTime();
		if (now - lastPlayed > 30000) {
			App.debug("[WEBAUDIOAPI] reinitializing context.");
			closeContext();
			initialize();
		}

		if (!loaded) {
			App.debug("[WEBAUDIOAPI] not loaded yet");
			return;
		}

		setLoop(loop);
		stopSound();
		playSound();
		onPlay();
		lastPlayed = now;
		playing = true;
	}

	@Override
	public boolean isLoop() {
		return loop;
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public void pause() {
		stopSound();
		playing = false;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public boolean isAutoPlay() {
		return autoPlay;
	}

	@Override
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay = autoPlay;
	}

	@Override
	public void setVolume(double value) {
		volume = value;
		if (gainNode != null) {
			setGainVolume(volume);
		}

	}

	private native void setGainVolume(double value) /*-{
		this.@hu.norbisquest.nagbase.core.WebAudioChannel::gainNode.gain.value = value;
	}-*/;

	@Override
	public double getVolume() {
		return volume;
	}

	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;

	}
	public int getChannelNum() {
		return channelNum;
	}

	public native void playSound() /*-{
		console.log("[WEBAUDIOAPI] playSound");

		var context = @hu.norbisquest.nagbase.core.WebAudioChannel::context;
		var source = context.createBufferSource(); // creates a sound source
		var gainNode = context.createGain();
		gainNode.gain.value = this.@hu.norbisquest.nagbase.core.WebAudioChannel::volume;
		source.buffer = this.@hu.norbisquest.nagbase.core.WebAudioChannel::getBuffer()();
		source.loop = this.@hu.norbisquest.nagbase.core.WebAudioChannel::loop;
		this.@hu.norbisquest.nagbase.core.WebAudioChannel::source = source;
		this.@hu.norbisquest.nagbase.core.WebAudioChannel::gainNode = gainNode;

		var that = this;

		source.onended = function() {
			if (that.@hu.norbisquest.nagbase.core.WebAudioChannel::listener) {
				that.@hu.norbisquest.nagbase.core.WebAudioChannel::onEnded()();
			}

		}

		source.connect(gainNode);
		gainNode.connect(context.destination);
		source.start(0); // play the source now

	}-*/;

	public native void stopSound() /*-{
		var source = this.@hu.norbisquest.nagbase.core.WebAudioChannel::source;
		var gain = this.@hu.norbisquest.nagbase.core.WebAudioChannel::gainNode;
		if (!source) {
			return;
		}
		console.log("[WEBAUDIOAPI] stopSound");
		source.stop();
		source.disconnect();
		gain.disconnect();
		source.onended = null;
	}-*/;

	// public static native boolean isSupported() /*-{
	// var contextClass = ($wnd.AudioContext || $wnd.webkitAudioContext
	// || $wnd.mozAudioContext || $wnd.oAudioContext || $wnd.msAudioContext);
	//
	// if (contextClass) {
	// console.log("[WEBAUDIOAPI] supported");
	// return true;
	// } else {
	// console.log("[WEBAUDIOAPI] NOT supported");
	//
	// }
	// return false;
	// }-*/;

	static boolean isSupported() {
		return true;
	}

	JavaScriptObject getBuffer() {
		return buffer;
	}

	private void setBuffer(JavaScriptObject buffer) {
		this.buffer = buffer;
	}

    private JavaScriptObject getSource() {
        return source;
    }

    private void setSource(JavaScriptObject source) {
        this.source = source;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	protected String getPrefix() {
		return null;
	}

	@Override
	public void doDestroy() {
		listener = null;
		gainNode = null;
		destroySource();
		buffer = null;
		source = null;
		App.debug("[WebAudioChannel] DESTROY #" + channelNum);
	}

	private native void destroySource() /*-{
		var source = this.@hu.norbisquest.nagbase.core.WebAudioChannel::source;
		if (source) {
			source.buffer = null
		}
	}-*/;

	@Override
	public void stop() {
		stopSound();
	}
}
