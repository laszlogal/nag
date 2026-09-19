package hu.norbisquest.nagbase.game.command;


public class Pause extends TimeoutCommand {
	public Pause(int duration) {
		setTimeout(duration);
	}
	
 	@Override
	public boolean isBlocker() {
		return true;
	}

	@Override
	public void setCondition(boolean condition) {
	}

	@Override
	public boolean getCondition() {
		return true;
	}

}
