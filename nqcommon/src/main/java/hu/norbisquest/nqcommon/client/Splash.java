package hu.norbisquest.nqcommon.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.core.layer.EventLayerListener;
import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage.Id;

public class Splash extends Screen implements EventLayerListener {
	private int timer;
	private int hold;
	private int fadeOut;
	private Id next;
	private boolean clear;
	protected Splash(FlowPanel parent, ImageResource res, int hold, int fadeOut,
                     Id next) {
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
		App.setRootStyle("fade-in");
		timer = 0;
	}

	@Override
	public boolean isValid() {
		return getBackground() != null && getBackground().isLoaded();
	}

	@Override
	public void onClick(int x, int y) {

	}

	@Override
	public void execute(double timestamp) {
		timer++;
		if (timer == hold) {
			if (fadeOut != -1) {
				App.setRootStyle("fade-out-slow");
			}
		} else if (timer == hold + fadeOut) {
			onFadeOut();
		}
	}

	protected void onFadeOut() {
		getListener().changeScreen(next, isClear());

	}

	@Override
	public void onMouseMove(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDestroy() {
		App.setRootStyle("fade-out");
	}

	@Override
	public void onMouseOut(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		// TODO Auto-generated method stub

	}

	private boolean isClear() {
		return clear;
	}

	protected void setClear(boolean clear) {
		this.clear = clear;
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
