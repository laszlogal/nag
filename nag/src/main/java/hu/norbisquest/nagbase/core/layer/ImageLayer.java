package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.core.NAGImage;

public class ImageLayer extends Layer implements Loadable {
	private double posX;
	private double posY;
	private NAGImage image;

	private ImageLayer(final String url, int zIndex) {
		setPosX(0);
		setPosY(0);
		setZIndex(zIndex);
		setImage(null);
		image = new NAGImage(url);
	}

	public ImageLayer(ImageResource resource, int zIndex) {
		this(resource.getSafeUri().asString(), zIndex);
	}

	@Override
	public boolean load() {
		if (image == null) {
			return false;
		}

		if (image.isLoaded()) {
			return true;
		}

		image.addLoadHandler(event -> update());
		image.load();
		return true;
	}

	@Override
	public boolean unload() {
		if (!image.isLoaded()) {
			return false;
		}


		image.unload();
		return true;

	}

	public void setImagePosition(double posX, double posY) {
		this.setPosX(posX);
		this.setPosY(posY);
	}

	public void update() {
		if (!getImage().isLoaded()) {
			return;
		}

		Context2d ctx = getContext2d();
		ctx.save();
		if (isOpaque()) {
			ctx.setGlobalAlpha(getAlpha());
		}
		ctx.drawImage(getImage().asCanvasElement(), (int) getPosX(), (int) getPosY());
		ctx.restore();
	}

	public NAGImage getImage() {
		return image;
	}

	private void setImage(NAGImage image) {
		this.image = image;
	}

	public boolean isLoaded() {
		return image != null && image.isLoaded();
	}

	public double getPosX() {
		return posX;
	}

	private void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	private void setPosY(double posY) {
		this.posY = posY;
	}


	@Override
	public void doDestroy() {
		image.destroy();
		image = null;
	}
}
