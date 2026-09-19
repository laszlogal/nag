package hu.norbisquest.nagbase.game.target;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.target.ItemResource.PositionInStage;

public class InventoryItem extends HotSpot implements Loadable {
	private NAGImage image;
	private int cusorHotSpotX;
	private int cusorHotSpotY;

	private InventoryItem(Id id, int titleId, NAGColor color,
                          ImageResource res) {
		super(id, titleId, color);
		image = new NAGImage(res);
		PositionInStage posInStage = null;
		load();
	}

	public InventoryItem(Id id, int titleId, ImageResource res) {
		this(id, titleId, null, res);

	}

	public InventoryItem(ItemResource res) {
		this(res.id, res.titleId, null, res.inventoryImage);
		cusorHotSpotX = res.cursorHotSpotX;
		cusorHotSpotY = res.cursorHotSpotY;
	}

	public NAGImage getImage() {
		return image;
	}

	public void setImage(NAGImage image) {
		this.image = image;
	}

	@Override
	public boolean load() {
		return image.load();
	}

	@Override
	public boolean unload() { return image.unload();
	}

	public int getCusorHotSpotX() {
		return cusorHotSpotX;
	}

	public void setCusorHotSpotX(int cusorHotSpotX) {
		this.cusorHotSpotX = cusorHotSpotX;
	}

	public int getCusorHotSpotY() {
		return cusorHotSpotY;
	}

	public void setCusorHotSpotY(int cusorHotSpotY) {
		this.cusorHotSpotY = cusorHotSpotY;
	}

	public boolean isImageHit(int x, int y) {
		Image img = getImage().getGWTImage();
		int left = img.getAbsoluteLeft();
		int top = img.getAbsoluteTop();
		int right = left + img.getOffsetWidth();
		int bottom = top + img.getOffsetHeight();
		App.debug("item " + getId() + "(" + left + ", " + top + ", " + right + " ," + bottom + ") - (" + x + ", " + y
				+ ")");
		return (x > left && x < right
				&& y > top && y < bottom);
	}
}
