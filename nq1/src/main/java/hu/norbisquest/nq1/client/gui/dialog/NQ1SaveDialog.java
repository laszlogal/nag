package hu.norbisquest.nq1.client.gui.dialog;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Label;
import hu.norbisquest.nagbase.game.gui.Scalable;
import hu.norbisquest.nq1.client.gui.Icons;
import hu.norbisquest.nqcommon.client.dialog.NQSaveDialog;

public class NQ1SaveDialog extends NQSaveDialog {
	NQ1SaveDialog(Scalable scaler) {
		super(scaler);
	}

	@Override
	protected ImageResource getBackgroundResource() {
		return null;
	}

	@Override
	protected ImageResource getSaveResource() {
		return Icons.get().dlg_button_ok();
	}

	@Override
	protected ImageResource getCancelResource() {
		return Icons.get().dlg_button_cancel();
	}

	@Override
	public int getOffsetWidth() {
		return 200;
	}

	@Override
	public int getOffsetHeight() {
		return 300;
	}

	@Override
	protected void createTitle() {
		Label title = new Label("Mentés");
		title.addStyleName("title");
		addWidget(title);
	}

}
