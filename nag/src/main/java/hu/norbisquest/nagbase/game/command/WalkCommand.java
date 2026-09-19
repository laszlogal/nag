package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.game.Walker;
import hu.norbisquest.nagbase.game.Walker.WalkerListener;

public class WalkCommand extends ConditionCommand implements WalkerListener {
	private Walker walker;
	private boolean finished;
	private int x;
	private int y;

	public WalkCommand(Walker walker, int x, int y) {
		this(walker, x, y, false);
	}

	public WalkCommand(Walker walker, int x, int y, boolean look) {
		this.walker = walker;
		finished = false;
		this.x = x;
		this.y = y;
	}

	@Override
	public void start() {
		walker.setListener(this);
		walker.walkTo(x, y);
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	@Override
	public void onArrive() {
		finished = true;
	}

	@Override
	public boolean isBlocker() {
		return false;
	}

	@Override
	public String toString() {
		return "[COMMAND] walk to (" + x + ", " + y + ")";
	}

	@Override
	public void process() {
		walker.process();
	}

	@Override
	public void setCondition(boolean condition) {

	}

}
