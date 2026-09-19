package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import hu.norbisquest.nagbase.core.NAGCanvas;

public class Layer extends NAGCanvas implements  ILayer {
	public static final int Z_MINIMUM = -1;
	public static final int MAX_LAYERS = 999;
	public static final int Z_EVENT = 200;
	public static final int Z_DEBUG = Z_EVENT - 1;
	public static final int Z_BACKGROUND = -1;
	public static final int Z_FOREGROUND1 = 2;
	public static final int Z_FOREGROUND2 = 3;
	public static final int Z_FOREGROUND3 = 4;
	public static final int Z_DIALOG = Z_EVENT + 1;
	public static final int Z_BACKGROUND2 = 1;
	private int zIndex;
	private int left;
	private int top;
	private String id;

	private boolean permanent=false;

	public Layer() {
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		getCanvas().getElement().getStyle().setPosition(Position.ABSOLUTE);
	}

	public void setZIndex(int value) {
		zIndex = value;
		getCanvas().getElement().getStyle().setZIndex(zIndex);
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int value, Unit unit) {
		left = value;
		getCanvas().getElement().getStyle().setLeft(value, unit);
	}

	public void setTop(int value, Unit unit) {
		top = value;
		getCanvas().getElement().getStyle().setTop(value, unit);
	}

	public int getTop() {
		return top;
	}

	public void setId(String id) {
		getCanvas().getElement().setId(id);
	}

	public void setPositionInPixels(int left, int top) {
		setLeft(left, Unit.PX);
		setTop(top, Unit.PX);
	}

	public String getId() {
		return getCanvas().getElement().getId();

	}

	public void moveLeft(int diff) {
		setLeft(getLeft() + diff, Unit.PX);
	}

	public void moveTop(int diff) {
		setTop(getTop() + diff, Unit.PX);
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += "Id: " + getId() + "top: " + top + " left: " + left + " z-index: " + zIndex;
		return result;
	}

	boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
}
