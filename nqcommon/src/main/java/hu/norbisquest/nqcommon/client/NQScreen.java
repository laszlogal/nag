package hu.norbisquest.nqcommon.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.gui.ScreenDimension;
import hu.norbisquest.nagbase.core.layer.EventLayerListener;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.engine.ScreenParent;

public abstract class NQScreen extends Screen implements EventLayerListener {
	private int fadeTimer = 50;
	private Id fadeTo = null;

	protected NQScreen(int left, int top, int width, int height,
					   ScreenParent parent) {
		super(new ScreenDimension(left, top, width, height), parent);	}

	public NQScreen(int left, int top, int width, int height,
					   FlowPanel parent) {
		super(left, top, width, height, parent);	}

	@Override
	public boolean isValid() {
		return getBackground().isLoaded();
	}

	@Override
	public void execute(double timestamp) {
		if (fadeTo == null) {
			return;
		}

		fadeTimer--;

		if (fadeTimer < 0) {
			onFadeOut();
		}
	}

	protected void onFadeOut() {
		getListener().changeScreen(fadeTo);
	}

	@Override
	public void onMouseMove(int x, int y) {
	}

	@Override
	public boolean unload() {
		App.setRootStyle("fade-out");
		return super.unload();
	}

	@Override
	public boolean load() {
		App.setRootStyle("fade-in");
		return super.load();
	}

	@Override
	public void onMouseOut(int x, int y) {
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
	}

	public int getFadeTimer() {
		return fadeTimer;
	}

	private void setFadeTimer(int fadeTimer) {
		this.fadeTimer = fadeTimer;
	}

	public Id getFadeTo() {
		return fadeTo;
	}

	protected void setFadeTo(Id fadeToId) {
		this.fadeTo = fadeToId;
		App.setRootStyle("fade-out-slow");
	}

	public void setFadeTo(Id fadeToId, int timer) {
		setFadeTimer(timer);
		setFadeTo(fadeToId);
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {

	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		JsArray<Touch> touches = event.getTargetTouches().length() == 0
				? event.getChangedTouches() : event.getTargetTouches();

		Touch t = touches.get(0);

		CanvasElement eventCanvas = getEventLayer().getCanvas().getCanvasElement();
		event.stopPropagation();
	}
}
