package hu.norbisquest.nagbase.game;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.common.gui.ScreenDimension;
import hu.norbisquest.nagbase.core.layer.*;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.engine.ScreenParent;

public abstract class Screen extends MultiLayer implements GameScreen {

	/**
	 * Default Layer Names Layers that usually needed in a game.
	 */
	private static final String BACKGROUND_LAYER_KEY = "nag_background";
	private static final String EVENT_LAYER_KEY = "nag_event";
	private static final String DEBUG_LAYER_KEY = "nag_debug";

	private EventLayer eventLayer;

	private static Layer debugLayer;

	private ScreenContext context;
	private Id id;
	private int zBase;

	Screen(){}

	public Screen(int left, int top, int width, int height, FlowPanel parent) {
		super(left, top, width, height, parent);
	}

	@Override
	public final void attach(ScreenContext context) {
		if (context == null) {
			throw new IllegalArgumentException("context must not be null");
		}
		if (this.context != null) {
			throw new IllegalStateException("Screen context is already attached");
		}
		this.context = context;
	}


	@Override
	public void setInputEnabled(boolean enabled) {
		if (eventLayer != null) {
			eventLayer.setEnabled(enabled);
		}
	}

	@Override
	public void enter() {
		onAvailable(App.getSettings().isScreenVisited());
	}

	/**
	 * Defines a game screen on the Web Page.
	 * 
	 * @param dimension of the screen.
	 *
	 * @param parent of the screen.
	 */
    protected Screen(ScreenDimension dimension, ScreenParent parent) {
		super(dimension.getLeft(), dimension.getTop(), dimension.getWidth(), dimension.getHeight(),
				parent.getPanel());
		eventLayer = null;
		zBase = 0;
	}

	/**
	 * Sets the image as background.
	 * 
	 * @param res
	 *            The resource of the image.
	 */
    protected void setBackground(ImageResource res) {
		setBackground(new ImageLayer(res, zBase + Layer.Z_BACKGROUND));
	}

	/**
	 * Use an ImageLayer as background layer.
	 * 
	 * @param layer
	 *            The layer containing the image.
	 */
    protected void setBackground(ImageLayer layer) {
		addImageLayer(BACKGROUND_LAYER_KEY + zBase, layer);
	}

	/**
	 * Gets the layer to draw the background that rarely changes.
	 */
    protected ImageLayer getBackground() {
		return getImageLayer(BACKGROUND_LAYER_KEY + zBase);
	}

	/**
	 * Gets the layer for debug info. If additional info needed to be drawn on
	 * canvas in debug mode, use this layer. Call enableDebug() before using it.
	 * It is disabled by default.
	 */
    protected Layer getDebugLayer() {

		return debugLayer;
	}

	/**
	 * Gets the event layer. This layer is responsible for catching all the
	 * events: mouse, tap, gesture, keyboard etc. Event layer is the topmost
	 * layer of the game.
	 */
    protected EventLayer getEventLayer() {
		if (eventLayer == null) {
			eventLayer = new EventLayer(zBase + Layer.Z_EVENT);
			setLayer(EVENT_LAYER_KEY + zBase, eventLayer);
		}

		return eventLayer;
	}

	protected void setEventListener(EventLayerListener listener) {
		getEventLayer().setListener(listener);
	}

	/**
	 * Adds a new layer with a given image.
	 * 
	 * @param name
	 *            The layer name as key. It must be unique. See Default Layer
	 *            Names to avoid conflict.
	 * @param res The resource of the image.
	 * @param zIndex The z-index of the layers.
	 */
	public void addImageLayer(String name, ImageResource res, int zIndex) {
		ImageLayer layer = new ImageLayer(res, zBase + zIndex);
		layer.setZIndex(zBase + zIndex);
		addImageLayer(name, layer);
	}

	/**
	 * Adds a specific ImageLayer to the screen.
	 * 
	 * @param name
	 *            The layer name as key. It must be unique. See Default Layer
	 *            Names to avoid conflict.
	 * @param layer
	 *            The ImageLayer to add.
	 */
    private void addImageLayer(String name, ImageLayer layer) {
		setLayer(name, layer);
		layer.load();
	}

	/**
	 * Gets a layer as ImageLayer.
	 * 
	 * @param name
	 *            The layer name.
	 * @return The ImageLayer if exits at the given name, null otherwise.
	 */
	private ImageLayer  getImageLayer(String name) {
		Layer layer = getLayer(name);
		if (layer instanceof ImageLayer) {
			return (ImageLayer) layer;
		}
		return null;
	}

	/**
	 * Determines if debug layer is enabled to use.
	 * 
	 * @return If debug layer is enabled or not.
	 */
    protected boolean isDebugEnabled() {
		return debugLayer != null;
	}

	/**
	 * Enables usage of debug layer.
	 * 
	 */

	protected synchronized void enableDebug() {
		if (debugLayer == null) {
			debugLayer = new Layer();
			debugLayer.setLeft(getLeft(), Unit.PX);
			debugLayer.setZIndex(zBase + Layer.Z_DEBUG);
			setLayer(DEBUG_LAYER_KEY, debugLayer);
		}
	}

	/**
	 * Disables usage of debug layer.
	 * 
	 */
    protected void disableDebug() {
		removeLayerById(DEBUG_LAYER_KEY);
		Screen.removeDebug();
	}

	private static void removeDebug() {
		debugLayer = null;

	}

	/**
	 * Gets the unique id of the screen. Use this id to change screen or refer
	 * to.
	 * 
	 * @return The unique screen identifier.
	 */
	@Override
	public Id getId() {
		return id;
	}

	/**
	 * Sets the unique id of the screen. Use this id to change screen or refer
	 * to.
	 * 
	 * @param id
	 *            The unique screen identifier.
	 */
    protected void setId(Id id) {
		this.id = id;
	}

	public ScreenNavigation getListener() {
		return requireContext().navigation();
	}

	protected final GameGUI getGui() {
		return requireContext().gui();
	}

	private ScreenContext requireContext() {
		if (context == null) {
			throw new IllegalStateException("Screen context is not attached");
		}
		return context;
	}

	/**
	 * This method is called when the screen became valid (For ex. background is
	 * loaded, anims are ready, music has loaded etc). Override it to setup the
	 * already loaded components.
	 * 
	 * @param isFirst
	 *            decides if the screen become available for the first time.
	 */
    protected void onAvailable(boolean isFirst) {
		eventLayer.setZoomer(getGui().getZoomer());
    	App.debug("Screen " + id + " is available");
	}

	public int getZBase() {
		return zBase;
	}

	/**
	 * Sets the minimal Z-Index that screen can use. Every new layer must have
	 * equal or bigger Z-Index Useful for pop-up dialogs.
	 * 
	 * @param zBase
	 *            The base Z-Index.
	 */
	public void setZBase(int zBase) {
		this.zBase = zBase;
	}

	public void reload() {
		onReload();
		getListener().navigateTo(id);
	}

	private void onReload() {
//		App.getAudioManager().stopMusic();
//		App.getAudioManager().stopAmbientNoise();
	}
}
