package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.game.Stage.Id;

public class ChangeScreenCommand extends OneShotCommand {

	private Id screenId;
	private ScreenListener listener;
    
	ChangeScreenCommand(Id screenId, ScreenListener listener) {
		this.screenId = screenId;
		this.listener = listener;
	}

	@Override
	public void start() {
		listener.changeScreen(screenId);
	}


}
