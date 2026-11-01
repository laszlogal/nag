package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.game.Walker;
import hu.norbisquest.nagbase.game.target.ExitTarget;

public class ExitCommand extends WalkCommand {
	private final ScreenNavigation navigation;
	private final ScreenId exitId;

	public ExitCommand(Walker walker, ExitTarget exit, ScreenNavigation navigation) {
		super(walker, exit.getLeft(), exit.getTop(), true);
		this.navigation = navigation;
		exitId = exit.getScreenId();
	}

	@Override
	public void onArrive() {
		super.onArrive();
		navigation.navigateTo(exitId);
	}
}
