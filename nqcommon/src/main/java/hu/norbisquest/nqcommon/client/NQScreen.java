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
	private boolean transitionRequested;

	protected NQScreen(int left, int top, int width, int height,
					   ScreenParent parent) {
		super(new ScreenDimension(left, top, width, height), parent);	}

	public NQScreen(int left, int top, int width, int height,
					   FlowPanel parent) {
		super(left, top, width, height, parent);	}

	@Override
	public boolean isReady() {
		return super.isValid()
				&& getBackground() != null
				&& getBackground().isLoaded();
	}

	@Override
	public void update(double timestamp) {
		if (fadeTo == null || transitionRequested) {
			return;
		}

		fadeTimer--;

		if (fadeTimer < 0) {
			transitionRequested = true;
			onFadeOut();
		}
	}

	protected void onFadeOut() {
		getListener().navigateTo(fadeTo);
	}

	@Override
	public void onMouseMove(int x, int y) {
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		super.onAvailable(isFirst);
		App.setRootStyle("fade-in");
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
		if (transitionRequested) {
			return;
		}
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
		if (App.isBlocked()) {
			return;
		}

		JsArray<Touch> touches = event.getChangedTouches();
		if (touches.length() == 0) {
			return;
		}

		Touch t = touches.get(0);
		CanvasElement eventCanvas = getEventLayer().getCanvas().getCanvasElement();
		int x = getGui().toScaledX(t.getRelativeX(eventCanvas));
		int y = getGui().toScaledY(t.getRelativeY(eventCanvas));
		onClick(x, y);
	}
}
