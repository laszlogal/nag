package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;

public class NavigateToScreenCommand extends OneShotCommand {

	private final ScreenId screenId;
	private final ScreenNavigation navigation;

	NavigateToScreenCommand(ScreenId screenId, ScreenNavigation navigation) {
		this.screenId = screenId;
		this.navigation = navigation;
	}

	@Override
	public void start() {
		navigation.navigateTo(screenId);
	}
}
