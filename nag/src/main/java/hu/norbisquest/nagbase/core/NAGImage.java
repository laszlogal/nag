package hu.norbisquest.nagbase.core;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import hu.norbisquest.nagbase.game.App;

public class NAGImage extends NAGCanvas implements Loadable, LoadHandler {
	private static final String IMAGE_CACHE = "image-cache";
	private String url;
	private Image image;
	private boolean loaded;
	private HandlerRegistration handle;
	private ImageData data;
	private HandlerRegistration hCustom;
	private LoadHandler customHandler;

	public NAGImage(final String url) {
		this.url = url;
		setImage(null);
		loaded = false;
	}

	public NAGImage(ImageResource resource) {
		this(resource.getSafeUri().asString());
	}

	@Override
	public boolean load() {
		loaded = false;
		Image img = new Image(url);
		img.getElement().setAttribute("crossOrigin", "Anonymous");
		setImage(img);
		getGWTImage().setVisible(false);
		addToElement();
		handle = image.addLoadHandler(this);
		if (customHandler != null) {
			hCustom = image.addLoadHandler(customHandler);
		}
		return true;
	}

	@Override
	public boolean unload() {
		App.debug("[UNLOAD] image: " + url);

		loaded = false;
		if (getGWTImage() == null) {
			return false;
		}
		deleteCanvas();
		handle.removeHandler();
		image = null;
		data = null;
		return true;
	}

	@Override
	public void onLoad(LoadEvent event) {
		loaded = true;
		setSize(getGWTImage().getWidth(), getGWTImage().getHeight());
		update();
	}

	private void update() {
		if (!isLoaded()) {
			return;
		}

		Context2d ctx = getContext2d();
		ctx.save();
		if (isOpaque()) {
			ctx.setGlobalAlpha(getAlpha());
		}
		ctx.drawImage(ImageElement.as(getGWTImage().getElement()), 0, 0);
	}

	public void addLoadHandler(LoadHandler loadHandler) {
		customHandler = loadHandler;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public CanvasElement asCanvasElement() {
		if (!loaded) {
			return null;
		}
		return getCanvas().getCanvasElement();
	}

	public Image getGWTImage() {
		return image;
	}

	private void setImage(Image image) {
		this.image = image;
	}

	private Integer firstPixelInRow(int y) {
		for (int x = 0; x < getWidth(); x++) {
			if (data.getAlphaAt(x, y) != 0) {
				return x;
			}
		}
		return null;
	}

	private void refreshData() {
		update();
		data = getImageData();
	}

	@Override
	public ImageData getImageData() {
		// App.debug("[NAGImage][ImageData] " + url);
		return super.getImageData();
	}

	public int findBottom() {
		refreshData();
		for (int y = (int) getHeight(); y > 0; y--) {
			if (firstPixelInRow(y) != null) {
				return y;
			}
		}
		return -1;
	}

	public int findTop() {
		refreshData();
		data = getContext2d().getImageData(0, 0, (int) getWidth(), (int) getHeight());
		for (int y = 0; y < getHeight(); y++) {
			if (firstPixelInRow(y) != null) {
				return y;
			}
		}
		return -1;
	}

	public String getUrl() {
		return url;
	}

	private void addToElement() {
		RootPanel.get(IMAGE_CACHE).add(getGWTImage());

	}

    public static void clearCache() {
		RootPanel.get(IMAGE_CACHE).clear();
	}

	@Override
	public void doDestroy() {
		RootPanel.get(IMAGE_CACHE).remove(image);
		image.removeFromParent();
		App.debug("[NAGIMAGE] destroy");
		handle.removeHandler();
		if (hCustom != null) {
			hCustom.removeHandler();
		}
		image = null;
	}
}