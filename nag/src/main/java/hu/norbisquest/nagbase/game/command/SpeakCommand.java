package hu.norbisquest.nagbase.game.command;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.AudioChannel.IAudioChannelListener;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.IAudioChannel;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Walker;

public class SpeakCommand extends ConditionCommand implements IAudioChannelListener, Destroyable {
	private boolean finished;
	private Actor speaker;
	private String text;
	private int direction;
	private int counter;
	private IAudioChannel voice;
	private boolean playing = false;
	private boolean destroyed = false;

	public SpeakCommand(Actor speaker, String text, DataResource audio, int direction) {
		this.speaker = speaker;
		this.text = text;
		this.direction = direction;

		if (canPlayAudio() && audio != null) {
			voice = AudioManager.createAudioChannel();
			voice.setAutoPlay(false);
			voice.setChannelListener(this);
			voice.setLoop(false);
			voice.load(audio);
		} else {
			voice = null;
		}
	}


	@Override
	public void start() {
		finished = false;
		playing = false;
		if (Browser.isAndroid()) {
			App.setToolbarVisible(false);
		}

		speaker.setTalking(true);
		if (speaker instanceof Walker) {
			direction = ((Walker) speaker).getTalkDirection();
		} else {
			direction = speaker.getTalkAnimId();
		}

		counter = 0;
		if (voice == null) {
			startTalkAnimation();
		}
	}

	private void startTalkAnimation() {
		String subtitle = App.getSettings().hasSpeechText() ? text : null;

		if (direction != Actor.DEFAULT_DIRECTION) {
			speaker.changeAnimation(direction);
		}

		if (subtitle != null) {
			speaker.setSubtitle(subtitle);
		}
		speaker.start();

	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	@Override
	public void onLoad(IAudioChannel channel) {
		// App.debug("[SPEECH] onLoad");
	}

	@Override
	public void onPlay(IAudioChannel channel) {
		// App.debug("[SPEECH] onPlay");

	}

	@Override
	public void onPause(IAudioChannel channel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnded(IAudioChannel channel) {
		App.debug("[SPEECH] onEnd");
		playing = false;
		endSpeak();

	}

	@Override
	public String toString() {
		return text + ", audio: " + (voice != null);
	}

	@Override
	public boolean isBlocker() {
		return true;
	}

	private void endSpeak() {
		finished = true;
		playing = false;
		speaker.stopTalking();
		counter = 0;
	}

	@Override
	public void process() {
		if (voice != null) {
	        if (voice.isDestroyed()) {
	            App.debug("Voice is destroyed - not good...");
	            endSpeak();
	            return;
            }
			if (!playing) {
				playVoice();
			}
			// removing for failsafe :)
 			return;
		}

		counter++;
		if (counter > App.getSettings().getTextSpeed()) {
			endSpeak();
		}
	}

	private boolean canPlayAudio() {
		return App.getSettings().hasSpeechAudio();
	}

	public String getText() {
		return text;
	}

	public boolean hasAudio() {
		return voice != null;
	}

	private void playVoice() {
		if (voice == null || !voice.isLoaded()) {
			return;
		}
		startTalkAnimation();
		voice.setVolume(1);
		voice.play(false);
		playing = true;
	}

	@Override
	public void destroy() {
		if (destroyed) {
			return;
		}
		App.infoDestroy("SpeakCommand " + text);

		if (voice != null) {
			voice.destroy();
		}

		voice = null;
		text = null;
		speaker = null;
		destroyed = true;
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}
}
