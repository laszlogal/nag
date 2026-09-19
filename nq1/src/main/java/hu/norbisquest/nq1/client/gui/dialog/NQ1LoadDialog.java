package hu.norbisquest.nq1.client.gui.dialog;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Label;
import hu.norbisquest.nagbase.game.gui.Scalable;
import hu.norbisquest.nqcommon.client.dialog.NQLoadDialog;

public class NQ1LoadDialog extends NQLoadDialog {
	NQ1LoadDialog(Scalable scaler) {
		super(scaler);
	}

	@Override
	protected void createTitle() {
		Label title = new Label("Betöltés");
		title.addStyleName("title");
		addWidget(title);
	}

	@Override
	protected ImageResource getBackgroundResource() {
		return null;
	}
	@Override
	protected ImageResource getLoadResource() {
		return null;
	}
	@Override
	protected ImageResource getCancelResource() {
		return null;
	}

	@Override
	protected void createButtons() {

	}

	@Override
	public int getOffsetWidth() {
		return 200;
	}

	@Override
	public int getOffsetHeight() {
		return 300;
	}


}
