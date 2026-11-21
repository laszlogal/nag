package hu.norbisquest.nagbase.game.conversation;


import hu.norbisquest.nagbase.common.game.HasConversation;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.OneShotCommand;

public class ConversationOpen extends OneShotCommand {

	private final DialogManager dialogManager;
	private final HasConversation conversation;

	public ConversationOpen(HasConversation conversation, DialogManager dialogManager) {
		if (conversation == null || dialogManager == null) {
			throw new IllegalArgumentException("Conversation and dialog manager must not be null");
		}
		this.conversation = conversation;
		this.dialogManager = dialogManager;
		
	}
	
	@Override
	public void start() {
		App.block();
		dialogManager.showConversation(conversation);

	}
}
