package hu.norbisquest.nagbase.core;

import com.google.gwt.resources.client.DataResource;

public class AudioManager {

	private static final int MUSIC_CHANNEL_ID = 0;
	private static final int AMBIENT_CHANNEL_ID = 1;
	private static final double MUSIC_HIGH = 1.0;
	private static AudioManager INSTANCE = null;
	private NAGAudio music = null;
	private NAGAudio ambientNoise = null;

	private AudioManager() {
		music = new NAGAudio();
		ambientNoise = new NAGAudio();
	}

	public static AudioManager get() {
		if (INSTANCE == null) {
			INSTANCE = new AudioManager();
		}
		return INSTANCE;
	}

	public static IAudioChannel createAudioChannel() {
		return AudioUtils.createAudioChannel();
	}


	public void loadMusicNoLoop(DataResource ds, boolean autoPlay) {
		music.load(ds, MUSIC_CHANNEL_ID, autoPlay, false);
	}
	public void loadMusic(DataResource ds, boolean autoPlay) {
		music.load(ds, MUSIC_CHANNEL_ID, autoPlay, true);
	}

	private void playMusic() {
		music.play();
	}

	public void stopMusic() {
		music.stop();
    }

	public void setMusicOn(boolean on) {
		music.setOn(on);
	}

	public boolean toggleMusic() {
		return music.toggle();
	}

	private void setMusicVolume(double volume) {
		music.setVolume(volume);
	}

	public void loadAmbientNoise(DataResource ds) {
		loadAmbientNoise(ds, true);
	}

	private void loadAmbientNoise(DataResource ds, boolean autoPlay) {
		ambientNoise.load(ds, AMBIENT_CHANNEL_ID, autoPlay, true);
	}

	public void playAmbientNoise() {
		ambientNoise.play();
	}

	public void pauseAmbientNoise() {
        ambientNoise.pause();
	}

	public void stopAmbientNoise() {
        ambientNoise.stop();
	}

	public void setAmbientNoiseVolume(double value) {
		ambientNoise.setVolume(value);
	}

	public void restoreMusicVolume() {
		if (music.getVolume() != MUSIC_HIGH) {
			setMusicVolume(MUSIC_HIGH);
		}
	}

	public boolean isMusicPlaying() {
		return music.isPlaying();
	}

	public static native boolean canPlayMp3() /*-{
		var a = document.createElement('audio');
		return (a.canPlayType && a.canPlayType('audio/mpeg;').replace(/no/, ''));
	}-*/;
}
