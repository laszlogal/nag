package hu.norbisquest.nqcommon.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.TransitionEffect;
import hu.norbisquest.nagbase.core.layer.EventLayerListener;
import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Screen;

public class Splash extends Screen implements EventLayerListener {
	private int timer;
	private final int hold;
	private final int fadeOut;
	private final ScreenId next;

	protected Splash(FlowPanel parent, ImageResource res, int hold, int fadeOut,
                     ScreenId next) {
		super(App.LEFT, App.TOP, App.WIDTH, App.HEIGHT, parent);
		if (res != null) {
			setBackground(new ImageLayer(res, Layer.Z_BACKGROUND));
		}
		setEventListener(this);
		this.hold = hold;
		this.fadeOut = fadeOut;
		this.next = next;
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		super.onAvailable(isFirst);
		timer = 0;
	}

	@Override
	public boolean isReady() {
		return super.isValid()
				&& getBackground() != null
				&& getBackground().isLoaded();
	}

	@Override
	public void onClick(int x, int y) {
	}



	@Override
	public void update(double timestamp) {
		timer++;
		if (fadeOut < 0 && timer == hold) {
			onFadeOut();
		} else if (fadeOut >= 0 && timer == hold) {
			App.setRootStyle("fade-out-slow");
			if (fadeOut == 0) {
				onFadeOut();
			}
		} else if (fadeOut >= 0 && timer == hold + fadeOut) {
			onFadeOut();
		}
	}

	protected void onFadeOut() {
		getListener().navigateTo(next, getTransitionEffect());

	}

	protected TransitionEffect getTransitionEffect() {
		return TransitionEffect.FADE_IN;
	}

	@Override
	public void onMouseMove(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDestroy() {
	}

	@Override
	public void onMouseOut(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongPress(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrop(int x, int y) {
		// TODO Auto-generated method stub

	}

}
