package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.game.Stage.Id;

public class ChangeScreenCommand extends OneShotCommand {

	private Id screenId;
	private ScreenNavigation listener;
    
	ChangeScreenCommand(Id screenId, ScreenNavigation listener) {
		this.screenId = screenId;
		this.listener = listener;
	}

	@Override
	public void start() {
		listener.navigateTo(screenId);
	}


}
