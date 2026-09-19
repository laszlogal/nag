package hu.norbisquest.nagbase.game.command;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.AudioChannel.IAudioChannelListener;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.core.IAudioChannel;

public class SFXCommand extends ConditionCommand
		implements
			IAudioChannelListener {
	private IAudioChannel sfx;
	private DataResource res;
	private boolean finished;
	private boolean wait;
	SFXCommand(DataResource res, boolean wait) {
		finished = false;
		this.wait = wait;
		sfx = AudioManager.createAudioChannel();
		sfx.setLoop(false);
		sfx.setChannelListener(this);

		this.res = res;
	}

	SFXCommand(DataResource res) {
		this(res, true);
	}

	@Override
	public void start() {
		sfx.load(res);
		finished = !wait;
	}

	@Override
	public boolean isBlocker() {
		return true;
	}

	@Override
	public void process() {
		// not used
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	@Override
	public void onLoad(IAudioChannel channel) {
		sfx.play(false);
	}

	@Override
	public void onPlay(IAudioChannel channel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause(IAudioChannel channel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnded(IAudioChannel channel) {
		finished = true;
	}

}
