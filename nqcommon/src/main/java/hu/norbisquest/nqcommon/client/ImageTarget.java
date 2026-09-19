package hu.norbisquest.nqcommon.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import hu.norbisquest.nagbase.game.target.SimpleTarget;

class ImageTarget extends SimpleTarget {
	private Image img;
	private boolean valid;

	public ImageTarget(final int left, final int top, ImageResource res) {
		super("", left, top, 0, 0);
		img = new Image(res);
		img.addLoadHandler(event -> {
            valid = true;
            img.addStyleName("imageTarget");
            setPosition(left, top);
        });

		RootPanel.get("gui").add(img);
	}

	private void setPosition(final int left, final int top) {
		img.getElement().getStyle().setLeft(left, Unit.PX);
		img.getElement().getStyle().setTop(top, Unit.PX);
	}

	public void remove() {
		RootPanel.get("gui").remove(img);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void setOn(boolean value) {
		img.setVisible(value);
	}

}
