package hu.norbisquest.nagbase.game.target;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.Composite;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.core.NAGCanvas;
import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Screen;

public class StageItem extends HotSpot {
	private ImageLayer layer;
	private Screen screen;
	private String layerName;
	private boolean visible;
	private double boxedZoom = 1;

	public StageItem(Id id, int titleId, NAGColor color) {
		super(id, titleId, color);
		screen = null;
		visible = false;
	}

	public StageItem(Id id, int titleId, NAGColor color, int lookX, int lookY) {
		super(id, titleId, color, lookX, lookY);
		screen = null;
		visible = false;
	}

	public void setLayer(ImageResource res, String name, int zIndex, int x,
			int y) {
		layer = new ImageLayer(res, zIndex);
		layerName = name;
		layer.setImagePosition(x, y);
		setLookX(x + res.getWidth() / 2);
		setLookY(y + res.getHeight() / 2);

		layer.load();
	}

	public void setPosition(double x, double y) {
		getLayer().setImagePosition(x, y);
	}

	private void show() {
		App.debug("[ITEM] show " + getId());
		if (!screen.hasLayer(layerName)) {
			screen.setLayer(layerName, getLayer());
			getLayer().moveLeft(screen.getLeft());
		}
		getLayer().update();
	}

	private void hide() {
		App.debug("[ITEM] hide " + getId());
		getLayer().clear();
		// getScreen().removeLayerById(layerName);
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;

	}

	public void putMaskTo(Layer l) {

		Context2d ctx = l.getContext2d();
		double x = layer.getPosX();
		double y = layer.getPosY();
		double w = layer.getImage().getWidth();
		double h = layer.getImage().getHeight();

		if (hasBoxedMask()) {
			putBoxedMaskTo(ctx, x, y, w, h);
			return;
		}

		ctx.save();
		ctx.setFillStyle(getColor().toHexString());
		ctx.fillRect((int) x, (int) y, (int) w, (int) h);
		ctx.drawImage(layer.getCanvas().getCanvasElement(), (int) x, (int) y);
		ctx.restore();
	}

	public void putMaskTo(NAGImage img) {
		if (img == null) {
			return;
		}
		double x = layer.getPosX();
		double y = layer.getPosY();
		double w = layer.getImage().getWidth();
		double h = layer.getImage().getHeight();
		Canvas c = NAGCanvas.createCanvas((int) w, (int) h);
		Context2d ctx = c.getContext2d();

		if (hasBoxedMask()) {
			putBoxedMaskTo(img.getContext2d(), x, y, w, h);
			return;
		}

		ctx.save();
		ctx.setFillStyle(getColor().toHexString());
		ctx.fillRect(0, 0, w, h);
		ctx.setGlobalCompositeOperation(Composite.DESTINATION_ATOP);
		ctx.drawImage(layer.getImage().asCanvasElement(), 1, 0);
		img.draw(c.getCanvasElement(), x, y);
		ctx.restore();
		c = null;
	}

	private void putBoxedMaskTo(Context2d ctx, double x0, double y0, double w0, double h0) {

		int w = (int) (boxedZoom * w0);
		int h = (int) (boxedZoom * h0);

		ctx.save();
		ctx.setFillStyle(getColor().toHexString());
		ctx.fillRect((int) x0 - (w / 2.0), (int) y0 - (h / 2.0), w, h);
		ctx.restore();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		setEnabled(visible);
		if (screen != null) {
			update();
		}
	}

	public void update() {
		if (visible) {
			show();
		} else {
			hide();
		}
	}

	public ImageLayer getLayer() {
		return layer;
	}

	public void setLayer(ImageLayer layer) {
		this.layer = layer;
	}

	public double getBoxedZoom() {
		return boxedZoom;
	}

	public void setBoxedZoom(double boxedZoom) {
		this.boxedZoom = boxedZoom;
	}

	@Override
	public void setZIndex(int zIndex) {
		super.setZIndex(zIndex);
		getLayer().setZIndex(zIndex);
	}

}
