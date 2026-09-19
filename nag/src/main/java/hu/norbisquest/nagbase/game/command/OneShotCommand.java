package hu.norbisquest.nagbase.game.command;

public abstract class OneShotCommand extends ConditionCommand {

	@Override
	public boolean isBlocker() {
		return true;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
