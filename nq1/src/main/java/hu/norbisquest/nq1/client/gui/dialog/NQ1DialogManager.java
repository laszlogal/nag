package hu.norbisquest.nq1.client.gui.dialog;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nq1.client.factory.InventoryFactory;
import hu.norbisquest.nqcommon.client.dialog.*;

public class NQ1DialogManager extends NQDialogManager {

	public NQ1DialogManager(SettingsChanged listener) {
		super(listener);
	}

	@Override
	protected NQSaveDialog createSaveDialog() {
		return new NQ1SaveDialog(getScaler());
	}

	@Override
	protected NQInventoryDialog createInventoryDialog() {
		NQ1InventoryDialog dlg = new NQ1InventoryDialog(getScaler());
		App.getInventory().setView(dlg);
		return dlg;
	}

	@Override
	public void showInventoryDialog() {
		InventoryFactory.setInventorySpeech();
		super.showInventoryDialog();
	}

	@Override
	protected NQLoadDialog createLoadDialog() {
		return new NQ1LoadDialog(getScaler());
	}

	@Override
	protected NQExitDialog createExitDialog() {
		return new NQ1ExitDialog(getScaler(), getListener());
	}

}
