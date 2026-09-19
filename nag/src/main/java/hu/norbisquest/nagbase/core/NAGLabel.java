package hu.norbisquest.nagbase.core;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;

import hu.norbisquest.nagbase.game.App;

public class NAGLabel extends Label {

	private static final int MARGIN_X = 5;

	public void clear() {
		setText("");
	}

	public void putText(String text, int x, int y) {
		setText(text);
		int x0 = x;
		if (x0 < 0) {
			x0 = MARGIN_X;
		}

		int maxX = App.WIDTH - getOffsetWidth();
		if (x0 > maxX) {
			x0 = maxX;
		}

		getElement().getStyle().setLeft(x0, Unit.PX);
		getElement().getStyle().setTop(y, Unit.PX);
	}

	public void setColor(String color) {
		getElement().getStyle().setColor(color);

	}
}
