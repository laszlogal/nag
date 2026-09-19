package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.core.MultiAnimation;

public class AnimateCommand extends TimeoutCommand {
	private MultiAnimation anim;
	private int phase;

	private AnimateCommand(MultiAnimation anim, int phase, int duration) {
		this.anim = anim;
		this.phase = phase;
		setTimeout(duration);
	}

	public AnimateCommand(MultiAnimation anim, int phase) {
		this(anim, phase, -1);
	}

	@Override
	public void start() {
		anim.changeAnimation(phase);
		anim.start();
		super.start();
	}

	@Override
	public boolean isBlocker() {
		return false;
	}

	@Override
	public void setCondition(boolean condition) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getCondition() {
		return true;
	}

}
