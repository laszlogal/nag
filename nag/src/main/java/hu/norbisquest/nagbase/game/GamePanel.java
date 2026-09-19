package hu.norbisquest.nagbase.game;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.core.Scaler;

public class GamePanel extends FlowPanel {

	private FlowPanel content;

	GamePanel() {
		content = new FlowPanel();
		content.getElement().setId("zoomable");
		content.addStyleName("zoom");
		add(content);
		addStyleName("nagGame");
	}

	public void reset() {
		content.removeFromParent();
		add(content);
	}

	public void clearContent() {
		content.clear();
	}

	void addContent(Widget widget) {
		content.add(widget);
	}

	FlowPanel getContent() {
		return content;
	}

	void scale(int ox, int oy, double ratio) {
		Scaler.scale(getElement(), ox, oy, ratio);
	}

	public void setPosition(int top, int left) {
		Style style = getElement().getStyle();
		style.setTop(top, Style.Unit.PX);
		style.setLeft(left, Style.Unit.PX);
	}
}
