package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;

public class AudioUtils {
	private static int channelCounter = 0;

	static IAudioChannel createAudioChannel() {
		return WebAudioChannel.isSupported()
				? new WebAudioChannel(channelCounter++)
				: new AudioChannel();
	}

	static IAudioChannel createAudioChannel(DataResource ds, int channelID, boolean autoPlay) {
		IAudioChannel ch = createAudioChannel();
		ch.setId(channelID);
		ch.setLoop(true);
		ch.setAutoPlay(autoPlay);
		ch.load(ds);
		return ch;
	}

	static void destroyAudioChannel(IAudioChannel channel) {
		if ( !(channel == null || channel.isDestroyed())) {
			channel.stop();
			channel.destroy();
		}
		channel = null;
	}

	static void play(DataResource ds, IAudioChannel ch, int channelId) {
		if (ds == null) {
			return;
		}

		if (ch == null) {
			ch = createAudioChannel(ds, channelId, true);
		} else if (!ch.isPlaying()) {
			ch.play(true);
		}
	}

	static void stop(IAudioChannel ch) {
		destroyAudioChannel(ch);
    }

	public static void pause(IAudioChannel ch) {
		if (ch == null) {
			return;
		}
		ch.pause();
	}

	static void setOn(DataResource ds, IAudioChannel ch, int channelId, boolean on) {
		if (on) {
			play(ds, ch, channelId);
		} else {
			stop(ch);
		}
	}

	static boolean toggle(DataResource ds, IAudioChannel ch) {
		if (ds != null && ch != null) {
			boolean playing = ch.isPlaying();
			setOn(ds, ch, ch.getId(), !playing);
			return !playing;
		}
        return false;
	}

	static void setVolume(DataResource ds, IAudioChannel ch, double value) {
		if (ch == null) {
			return;
		}
		ch.setVolume(value);
	}

	static boolean isPlaying(IAudioChannel ch) {
		return ch != null && ch.isPlaying();
	}

	static boolean isSame(DataResource ds1, DataResource ds2) {
		return ds1 != null && ds1.getName().equals(ds2.getName());
	}

	public static native boolean canPlayMp3() /*-{
		var a = document.createElement('audio');
		return (a.canPlayType && a.canPlayType('audio/mpeg;').replace(/no/, ''));
	}-*/;
}
