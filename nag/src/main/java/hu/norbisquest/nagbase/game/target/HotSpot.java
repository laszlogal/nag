package hu.norbisquest.nagbase.game.target;

import com.google.gwt.canvas.dom.client.ImageData;
import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.game.App;

public class HotSpot extends AbstractTarget {

    public interface Id {
		int ordinal();
	}

    private ImageData data;
	private NAGColor color;
	private int titleId;
	private Id id;
	private boolean enabled;
	private int lookX;
	private int lookY;
	private boolean boxedMask = false;

	public HotSpot(Id id, int titleId, NAGColor color) {
		this(id, titleId, color, -1, -1);
	}

	public HotSpot(Id id, int titleId, NAGColor color, int lookX, int lookY) {
		this.data = null;
		this.id = id;
		this.titleId = titleId;
		this.setColor(color);
		enabled = true;
		this.lookX = lookX;
		this.lookY = lookY;

	}

	@Override
	public boolean isHit(int x, int y) {
		if (!enabled || getColor() == null) {
			return false;
		}

		if (data == null) {
			App.warn("HotSpotMap is not set!");
			return false;
		}

		NAGColor pixelColor = new NAGColor(data.getRedAt(x, y), data.getGreenAt(x, y), data.getBlueAt(x, y));
		return getColor().equals(pixelColor);
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitle(int titleId) {
		this.titleId = titleId;
	}

	public ImageData getData() {
		return data;
	}

	public void setData(ImageData data) {
		this.data = data;
		if (color == null) {
			color = new NAGColor(data.getRedAt(lookX, lookY), data.getGreenAt(lookX, lookY),
					data.getBlueAt(lookX, lookY));
		}
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return id + " " + titleId + " " + (enabled ? "enabled" : "disabled");
	}

	NAGColor getColor() {
		return color;
	}

	private void setColor(NAGColor color) {
		this.color = color;
	}

	public void setColor(int red, int green, int blue) {
		setColor(new NAGColor(red, green, blue));
	}

	@Override
	public TargetControl getControl() {
		if (enabled) {
			return super.getControl();
		}
		return null;
	}

	public int getLookX() {
		return lookX;
	}

	void setLookX(int lookX) {
		this.lookX = lookX;
	}

	public int getLookY() {
		return lookY;
	}

	void setLookY(int lookY) {
		this.lookY = lookY;
	}

	boolean hasBoxedMask() {
		return boxedMask;
	}

	public void setBoxedMask(boolean boxedMask) {
		this.boxedMask = boxedMask;
	}

	public void doDestroy() {
		data = null;
		color = null;
	}

}
