package hu.norbisquest.nqcommon.client.dialog;

import hu.norbisquest.nagbase.common.game.HasConversation;
import hu.norbisquest.nagbase.game.AbstractDialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nagbase.game.conversation.ConversationView;

import java.util.ArrayList;
import java.util.List;

public abstract class NQDialogManager extends AbstractDialogManager {
	private SettingsChanged listener;
	private NQInventoryDialog inventory = null;

	private List<NAGPopupPanel> popups = new ArrayList<>();

	protected NQDialogManager(SettingsChanged listener) {
		this.setListener(listener);
		inventory = createInventoryDialog();
	}

	@Override
	public void showLoadDialog() {
		NQLoadDialog dlg = createLoadDialog();
		dlg.center();
		add(dlg );
	}

	@Override
	public void showConversation(HasConversation conversation) {
		ConversationView cp = ConversationView.get(getScaler());
		cp.open(conversation);
		add(cp);
	}

	@Override
	public void showSaveDialog() {
		NQSaveDialog dlg = createSaveDialog();
		dlg.center();
		add(dlg);
	}

	protected abstract NQInventoryDialog createInventoryDialog();

	protected abstract NQLoadDialog createLoadDialog();

	protected abstract NQSaveDialog createSaveDialog();

	protected abstract NQExitDialog createExitDialog();

	@Override
	public void showExitDialog() {
		NQExitDialog dlg = createExitDialog();
		dlg.center();
		add(dlg);
	}

	@Override
	public void showInventoryDialog() {
		if (inventory.isShowing()) {
			inventory.hide();
			popups.remove(inventory);
			App.setToolbarEnabled(true);
		} else {
			inventory.refresh();
			App.setToolbarEnabled(false);
			inventory.center();
			inventory.show();
			add(inventory);
		}

	}

	protected SettingsChanged getListener() {
		return listener;
	}

	private void setListener(SettingsChanged listener) {
		this.listener = listener;
	}

	public NQInventoryDialog getInventoryDialog() {
		return inventory;
	}

	public boolean isInventoryShowing() {
		return inventory != null && inventory.isShowing();
	}

	@Override
	public void closesAll() {
		for (NAGPopupPanel p: popups) {
			p.hide();
		}
		popups.clear();
	}

	private void add(NAGPopupPanel p) {
		if (!popups.contains(p)) {
			popups.add(p);
		}
	}

	@Override
	public void resize() {
		for (NAGPopupPanel p : popups) {
			if (p.isShowing()) {
				p.center();
			}
		}
	}
}
