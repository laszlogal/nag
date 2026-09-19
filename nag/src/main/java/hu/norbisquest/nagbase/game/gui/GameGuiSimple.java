package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.common.gui.HasZoom;
import hu.norbisquest.nagbase.game.AbstractDialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Stage;

public abstract class GameGuiSimple extends FlowPanel implements GameGUI {

	private final DialogManager dialogManager;
	private FlowPanel content;
	private Toolbar toolbar;

	private MenuPanel menu;
	private Scalable scaler;
	private Zoomer zoomer;

	public GameGuiSimple(DialogManager dialogManager) {
		this.dialogManager = dialogManager;
		menu = createMenu();
		toolbar = createToolBar();

		content = new FlowPanel();
		scaler = new Scaler(getElement());
		zoomer = new Zoomer(scaler);
		zoomer.addZoomHandler(toolbar);
		add(content);
		add(menu);

		content.getElement().setId("zoomable");
		content.addStyleName("zoom");
		addStyleName("nagGame");
		RootPanel.get().add(this);
	    createImageCache();
		Window.addResizeHandler(event -> resize());
        App.scrollTouchScreen();

    }

	@Override
	public void resize() {
		fitToWindow();
	}

	private void fitToWindow() {
		scaler.fitToWindow();
		setPosition(scaler.getTop(),scaler.getLeft());
		getDialogManager().resize();
	}


	protected abstract MenuPanel createMenu();
	protected abstract Toolbar createToolBar();

	@Override
	public void reset() {
		content.removeFromParent();
		add(content);
	}

	@Override
	public void scale(int ox, int oy, double ratio) {

	}

	public void clearContent() {
		content.clear();
	}

	public void addContent(Widget widget) {
		content.add(widget);
	}

	public FlowPanel getContent() {
		return content;
	}

	@Override
	public void setPosition(int top, int left) {
		Style style = getElement().getStyle();
		style.setTop(top, Style.Unit.PX);
		style.setLeft(left, Style.Unit.PX);
	}

	@Override
	public Toolbar getToolbar() {
		return toolbar;
	}

	@Override
	public void showMenu() {
		menu.show();
	}

	@Override
	public void toggleMenu() {
		menu.toggle();
	}

	@Override
	public boolean hideMenu() {
		if (menu == null || !menu.isShowing()) {
			return false;
		}
		menu.hide();
		return true;
	}


	private void attachToolbar(Stage stage) {
		if (toolbar == null) {
			return;
		}
		toolbar.setListener(stage);
		add(toolbar);
		App.notifyModeChange();
	}

	@Override
	public void onScreenChanged(GameScreen screen) {
		screen.setGui(this);
	}

	@Override
	public void onStageChanged(Stage stage) {
		App.setFeeder(stage);
		((AbstractDialogManager)getDialogManager()).setScaler(scaler);
		setWidth(App.WIDTH + "px");
		attachToolbar(stage);
	}
    private void createImageCache() {
        FlowPanel cache = new FlowPanel();
        cache.getElement().setId("image-cache");
        RootPanel.get().add(cache);
    }

	@Override
	public void zoom(int x, int y) {
		zoomer.zoom(x, y);
	}

	@Override
	public void zoomOut() {
		zoomer.zoomOut();
	}

	@Override
	public int toScaledX(int x) {
		return zoomer.toScaledX(x);
	}

	@Override
	public int toScaledY(int y) {
		return zoomer.toScaledY(y);
	}

	@Override
	public boolean justZoomed() {
		return zoomer.justZoomed();
	}

	@Override
	public void setJustZoomed(boolean value) {
		zoomer.setJustZoomed(value);
	}

	public void setUserZoomEnabled(boolean b) {
		zoomer.setEnabled(b);
	}

	@Override
	public HasZoom getZoomer() {
		return zoomer;
	}

    public DialogManager getDialogManager() {
		return dialogManager;
	}

	@Override
	public void closesAllDialogs() {
		dialogManager.closesAll();
	}
}
