package hu.norbisquest.nagbase.game.conversation;


import hu.norbisquest.nagbase.common.game.HasConversation;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.OneShotCommand;

public class ConversationOpen extends OneShotCommand {

	private final DialogManager dialogManager;
	private HasConversation conversation;

	public ConversationOpen(HasConversation conversation) {
		this.conversation = conversation;
		this.dialogManager = App.getDialogManager();
		
	}
	
	@Override
	public void start() {
		App.block();
		dialogManager.showConversation(conversation);

	}
}
