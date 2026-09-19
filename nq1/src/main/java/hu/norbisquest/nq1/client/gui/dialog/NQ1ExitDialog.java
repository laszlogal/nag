package hu.norbisquest.nq1.client.gui.dialog;

import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nagbase.game.gui.Scalable;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.gui.Icons;
import hu.norbisquest.nqcommon.client.dialog.NQExitDialog;

class NQ1ExitDialog extends NQExitDialog {

	private static final String EXIT_TITLE = "Biztos kilépsz?";

	NQ1ExitDialog(Scalable scaler, SettingsChanged listener) {
		super(scaler, listener);
	}

	@Override
	protected ImageResource getBackgroundResource() {
		return null;
	}

	@Override
	protected ImageResource getExitResource() {
		return Icons.get().dlg_button_ok();
	}

	@Override
	protected ImageResource getCancelResource() {
		return Icons.get().dlg_button_cancel();
	}

	@Override
	protected void onExit() {
		App.debug("exiting to gui menu");
		App.removeToolbar();
		App.getSettings().setScreenId(NQ1Ids.MainMenu);
		getListener().onSettingsChanged();

	}

	@Override
	public int getOffsetWidth() {
		return 300;
	}

	@Override
	public int getOffsetHeight() {
		return 160;
	}

	@Override
	public String getTitle() {
		return EXIT_TITLE;
	}
}
