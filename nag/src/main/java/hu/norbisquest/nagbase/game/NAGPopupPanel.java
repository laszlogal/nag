package hu.norbisquest.nagbase.game;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class NAGPopupPanel extends PopupPanel implements CloseHandler<PopupPanel>, Destroyable {
	private ImageResource background=null;
	private boolean destroyed = false;
	private Scalable scaler;
	protected NAGPopupPanel(Scalable scaler) {
		super(false, false);
		this.scaler = scaler;
		setAutoHideEnabled(true);
		addCloseHandler(this);
		setAnimationType(AnimationType.CENTER);
		setAnimationEnabled(true);
	}

	public ImageResource getBackground() {
		return background;
	}

	protected void setBackground(ImageResource background) {
		if (background == null) {
			return;
		}
		this.background = background;
		getElement().getStyle().setProperty("background", "url('" +
				background.getSafeUri().asString() +
				"') no-repeat");
	}

	@Override
	public int getOffsetWidth() {
		if (background == null) {
			return super.getOffsetWidth();
		}
		return background.getWidth();
	}

	@Override
	public int getOffsetHeight() {
		if (background == null) {
			return super.getOffsetHeight();
		}

		return background.getHeight();
	}

	@Override
	public void onClose(CloseEvent event) {
		App.resume();
	}


	public void center() {
		center(1.0);
	}

	private void scaleElement(double ratio) {
		Browser.scale(getElement(), scaler.getRatio() * ratio);
	}

	private void center(double ratio) {
		scaleElement(ratio);
		int h = (int) (getOffsetHeight() * scaler.getRatio() * ratio);
		int w = (int) (getOffsetWidth() * scaler.getRatio() * ratio);

		int top = (Window.getClientHeight() - h) / 2;
		int left = (Window.getClientWidth() - w) / 2;

		setPopupPosition(left, top);
		show();
	}

	@Override
	public void destroy() {
		if (destroyed) {
			return;
		}
		background = null;
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

}
