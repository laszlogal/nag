package hu.norbisquest.nagbase.game.conversation;


import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.OneShotCommand;

public class ConversationClose extends OneShotCommand {
	
	@Override
	public void start() {
		App.unblock();
		ConversationView.INSTANCE.hide();
	}
}
