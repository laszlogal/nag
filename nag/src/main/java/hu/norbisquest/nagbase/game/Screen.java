package hu.norbisquest.nagbase.game;

import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.common.gui.ScreenDimension;
import hu.norbisquest.nagbase.core.layer.*;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.engine.ScreenParent;

public abstract class Screen extends MultiLayer implements AnimationCallback, GameScreen {

	/**
	 * Default Layer Names Layers that usually needed in a game.
	 */
	private static final String BACKGROUND_LAYER_KEY = "nag_background";
	private static final String EVENT_LAYER_KEY = "nag_event";
	private static final String DEBUG_LAYER_KEY = "nag_debug";

	private EventLayer eventLayer;

	private static Layer debugLayer;

	private ScreenListener listener;
	private Id id;
	private int zBase;

	Screen(){}

	protected GameGUI gui;

	public 	Screen(int left, int top, int width, int height, FlowPanel parent) {
		super(left, top, width, height, parent);
	}

	@Override
	public void setGui(GameGUI gui) {
		this.gui = gui;
	}


	@Override
	public void show() {
		onAvailable(App.getSettings().isScreenVisited());
		appear();
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
		listener = null;
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

	@Override
	public ScreenListener getListener() {
		return listener;
	}

	/**
	 * Sets the screen listener responsible for handling events
	 * 
	 * @param listener
	 *            The object to handle events;
	 */
	@Override
	public void setListener(ScreenListener listener) {
		this.listener = listener;
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
		eventLayer.setZoomer(gui.getZoomer());
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

	@Override
	public void appear() {

	}

	public void reload() {
		onReload();
		listener.changeScreen(id);
	}

	private void onReload() {
//		App.getAudioManager().stopMusic();
//		App.getAudioManager().stopAmbientNoise();
	}
}
