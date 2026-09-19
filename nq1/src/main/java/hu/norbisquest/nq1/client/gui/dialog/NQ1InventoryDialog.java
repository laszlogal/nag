package hu.norbisquest.nq1.client.gui.dialog;

import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.gui.Scalable;
import hu.norbisquest.nq1.client.gui.Icons;
import hu.norbisquest.nqcommon.client.dialog.NQInventoryDialog;

public class NQ1InventoryDialog extends NQInventoryDialog {

	NQ1InventoryDialog(Scalable scaler) {
		super(scaler);
	}

	@Override
	protected ImageResource getBackgroundResource() {
		return null;
	}

	@Override
	protected ImageResource getExamineIconResource() {
		return Icons.get().examine_icon();
	}

	@Override
	protected ImageResource getUseIconResource() {
		return Icons.get().use_icon();
	}

	@Override
	protected ImageResource getOkIconResource() {
		return null;
	}

	@Override
	public int getOffsetWidth() {
		return 400;
	}

	@Override
	public int getOffsetHeight() {
		return 300;
	}

	@Override
	public void hide() {
		super.hide();
		App.getGame().getToolbar().reset();
	}
}
