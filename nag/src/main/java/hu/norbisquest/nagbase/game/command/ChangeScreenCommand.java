package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;

public class ChangeScreenCommand extends OneShotCommand {

	private ScreenId screenId;
	private ScreenNavigation listener;
    
	ChangeScreenCommand(ScreenId screenId, ScreenNavigation listener) {
		this.screenId = screenId;
		this.listener = listener;
	}

	@Override
	public void start() {
		listener.navigateTo(screenId);
	}


}
