package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.Walker;
import hu.norbisquest.nagbase.game.target.ExitTarget;

public class ExitCommand extends WalkCommand {
	private ScreenListener listener;
	private Id exitId;

	public ExitCommand(Walker walker, ExitTarget exit, ScreenListener listener) {
		super(walker, exit.getLeft(), exit.getTop(), true);
		this.listener = listener;
		exitId = exit.getScreenId();
	}

	@Override
	public void onArrive() {
		super.onArrive();
		listener.changeScreen(exitId);
	}
}
