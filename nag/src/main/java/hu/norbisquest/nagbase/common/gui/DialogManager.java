package hu.norbisquest.nagbase.common.gui;

import hu.norbisquest.nagbase.common.game.HasConversation;

public interface DialogManager {
	void showLoadDialog();

	void showConversation(HasConversation conversation);

	void showSaveDialog();

	void showExitDialog();

	void showInventoryDialog();

	boolean isInventoryShowing();

	void closesAll();

	void resize();

}

