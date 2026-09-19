package hu.norbisquest.nagbase.core;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.CanvasElement;
import hu.norbisquest.nagbase.game.App;

/**
 * Class to simplify canvas usage: Resize, clear, scale and alpha support
 * Another NAGCanvas elements can be drawn into it.
 * 
 * @author Laszlo Gal
 *
 */
public class NAGCanvas extends NAGObject {
	private static final float NO_ALPHA = -1;
	private Canvas canvas;
	private double width;
	private double height;
	private double alpha;
	private double scale;
	private boolean scalingEnabled = true;
	public int idx;
	public NAGCanvas() {
		super();
		canvas = Canvas.createIfSupported();
		alpha = NO_ALPHA;
		scale = 1.0f;

		App.debug("[CANVAS] CREATING ");
	}

	private int toInt(double n) {
		return (int) n;
	}

	/**
	 * Sets the size of the canvas.
	 * 
	 * @param width to set.
	 * @param height to set.
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		canvas.setSize(width + "px", height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
	}

	@Override
	public boolean isValid() {
		boolean valid = (canvas != null);
		if (!valid) {
//			App.printStacktrace("Canvas is not valid!");
			App.debug("Canvas is not valid!");
		}
		return super.isValid() && valid;
	}

	/**
	 * 
	 * @return If drawing methods use opacity or not.
	 */
    protected boolean isOpaque() {
		return alpha != NO_ALPHA;
	}

	/**
	 * 
	 * @return The GWT canvas object.
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * 
	 * @return The 2d context of this canvas.
	 */
	public Context2d getContext2d() {
		if (!isValid()) {
			return null;
		}

		return canvas.getContext2d();
	}

	/**
	 * Clears the whole canvas.
	 */
	public void clear() {
		if (!isValid()) {
			return;
		}

		getContext2d().clearRect(0, 0, toInt(getWidth()), toInt(getHeight()));
	}

	/**
	 * Draws another NAGCanvas element to this NAGCanvas
	 * 
	 * @param elem
	 *            The NAGCanvas element to draw.
	 * @param x
	 *            position x.
	 * @param y
	 *            position y.
	 */
	public void draw(NAGCanvas elem, double x, double y) {
		draw(elem.getCanvas().getCanvasElement(), x, y);

	}

	public void draw(NAGCanvas elem, int x, int y, double width,
			double height) {
		draw(elem.getCanvas().getCanvasElement(), x, y, width, height);

	}

	/**
	 * Draws a CanvasElement element to this NAGCanvas
	 * 
	 * @param elem
	 *            The CanvasElement element to draw.
	 * @param x
	 *            position x.
	 * @param y
	 *            position y.
	 */
	public void draw(CanvasElement elem, double x, double y) {
		Context2d ctx = getContext2d();
		ctx.save();
		double d = scalingEnabled ? scale : 1.0;
		ctx.scale(d, d);
		ctx.drawImage(elem, toInt(x), toInt(y));
		ctx.restore();
	}

	/**
	 * Draws a CanvasElement element to this NAGCanvas stretched.
	 * 
	 * @param elem
	 *            The CanvasElement element to draw.
	 * @param x
	 *            position x.
	 * @param y
	 *            position y.
	 * @param width
	 *            Width of the stretched rectangle.
	 * @param height
	 *            Height of the stretched rectangle.
	 */
    private void draw(CanvasElement elem, double x, double y, double width,
                      double height) {
		save();
		getContext2d().drawImage(elem, toInt(x), toInt(y), toInt(width), toInt(height));
		restore();
	}

	/**
	 * Saves the context.
	 */
	public void save() {
		getContext2d().save();
	}

	/**
	 * Restores the context.
	 */
	public void restore() {
		getContext2d().restore();
	}

	/**
	 * 
	 * @return The apha
	 */
    protected double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Creates a GWT Canvas object with given size. Use this to create temporary
	 * canvases.
	 * 
	 * @param width of the new canvas.
	 * @param height of the new canvas.
	 * @return The new GWT canvas with given size.
	 */
	static public Canvas createCanvas(int width, int height) {
		Canvas c = Canvas.createIfSupported();
		c.setSize(width + "px", height + "px");
		c.getElement().setClassName("temp");
	    c.setCoordinateSpaceWidth(width);
		c.setCoordinateSpaceHeight(height);
		return c;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	void setScale(double value) {
		scale = value;
	}

	double getScale() {
		return scale;
	}

	@Override
	public String toString() {
        return "(" + width + ", " + height + ") scale: " + scale
                + " alfa: " + alpha;

	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return "NAGCanvas";
	}

	ImageData getImageData() {
		return getContext2d().getImageData(0, 0, toInt(getWidth()),
				toInt(getHeight()));
	}

	public boolean isScalingEnabled() {
		return scalingEnabled;
	}

	public void setScalingEnabled(boolean scalingEnabled) {
		this.scalingEnabled = scalingEnabled;

	}

	void deleteCanvas() {
		canvas = null;
	}

	@Override
	final public void destroyCore() {
		if (canvas == null) {
			return;
		}
		setSize(0,0);
		canvas.removeFromParent();
		canvas = null;
		App.debug("[CANVAS] DESTROYING ");
	}

}
