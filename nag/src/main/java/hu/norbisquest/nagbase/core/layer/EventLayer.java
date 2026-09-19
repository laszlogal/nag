package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import hu.norbisquest.nagbase.common.gui.HasZoom;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.game.App;

public class EventLayer extends Layer implements Loadable {

	private static final int LONG_PRESS_TIME = 500;
	private int x;
	private int y;
	private EventLayerListener listener;
	private boolean enabled;
	private Timer longPressTimer;
	private int longPressY;
	private int longPressX;

	public void setZoomer(HasZoom zoomer) {
		this.zoomer = zoomer;
	}

	private HasZoom zoomer;
	private HandlerRegistration hMouseMove;

	public EventLayer(int zIndex) {
		final Canvas canvas = getCanvas();
		listener = null;
		enabled = true;
		createLongPressTimer();
		canvas.sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK | Event.ONCONTEXTMENU | Event.TOUCHEVENTS);
		canvas.addMouseMoveHandler(event -> {
			// if (App.isBlocked() || !isEnabled()) {
			// return;
			// }

			x = event.getRelativeX(canvas.getElement());
			y = event.getRelativeY(canvas.getElement());
			if (listener != null) {
				listener.onMouseMove(zoomer.toScaledX(x), zoomer.toScaledY(y));
			}
		});

		canvas.addMouseOutHandler(event -> {
			// if (App.isBlocked() || !isEnabled()) {
			// return;
			// }
			if (listener != null) {
				listener.onMouseOut(zoomer.toScaledX(x), zoomer.toScaledY(y));
			}
		});

		canvas.addClickHandler(event -> {
			if (App.isBlocked() || !isEnabled()) {
				return;
			}

			int x1 = zoomer.toScaledX(x);
			int y1 = zoomer.toScaledY(y);
			log("Click (" + x1 + ", " + y1 + ")");
			if (listener != null) {
				listener.onClick(x1, y1);
			}
		});

		canvas.addMouseDownHandler(event -> {
			longPressX = zoomer.toScaledX(event.getRelativeX(canvas.getElement()));
			longPressY = zoomer.toScaledY(event.getRelativeY(canvas.getElement()));
			longPressTimer.schedule(LONG_PRESS_TIME);
		});

		canvas.addMouseUpHandler(event -> longPressTimer.cancel());
		canvas.addKeyDownHandler(event -> {
			if (App.isBlocked() || !isEnabled()) {
				return;
			}

			listener.onKeyDown(event);

		});

		canvas.addHandler(event -> {
			event.getNativeEvent().stopPropagation();
			event.getNativeEvent().preventDefault();
			listener.onContextMenu(event);
		}, ContextMenuEvent.getType());

		canvas.addTouchStartHandler(event -> {
			event.stopPropagation();
			event.preventDefault();
			Touch t = event.getTargetTouches().get(0);
			longPressX = zoomer.toScaledX(t.getRelativeX(canvas.getElement()));
			longPressY = zoomer.toScaledY(t.getRelativeY(canvas.getElement()));
			longPressTimer.schedule(LONG_PRESS_TIME);

			App.debug("TOUCH START");
			listener.onTouchStart(event);
		});

		canvas.addTouchMoveHandler(event -> {
			event.stopPropagation();
			event.preventDefault();
			App.debug("TOUCH MOVE");

			listener.onTouchMove(event);
		});

		canvas.addTouchEndHandler(event -> {
			event.stopPropagation();
			event.preventDefault();
			App.debug("TOUCH END");
			longPressTimer.cancel();
			listener.onTouchEnd(event);
		});

		canvas.addDragOverHandler(DomEvent::preventDefault);

		canvas.addDropHandler(event -> {
			int x1 = zoomer.toScaledX(getRelativeX(event.getNativeEvent()));
			int y1 = zoomer.toScaledY(getRelativeY(event.getNativeEvent()));
			log("Drop (" + x1 + ", " + y1 + ")");
			if (listener != null) {
				listener.onDrop(x1, y1);
			}
		});

		setZIndex(zIndex);
	}

	private void createLongPressTimer() {
		longPressTimer = new Timer() {

			@Override
			public void run() {
				listener.onLongPress(longPressX, longPressY);
			}
		};
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setListener(EventLayerListener listener) {
		this.listener = listener;
	}

	private boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getPrefix() {
		return "EVENT";
	}

	private int getRelativeX(NativeEvent e) {
		Element target = getCanvas().getElement();
		return e.getClientX() - target.getAbsoluteLeft() + target.getScrollLeft() +
				target.getOwnerDocument().getScrollLeft();
	}

	private int getRelativeY(NativeEvent e) {
		Element target = getCanvas().getElement();
		return e.getClientY() - target.getAbsoluteTop() + target.getScrollTop() +
				target.getOwnerDocument().getScrollTop();
	}

	@Override
	public boolean load() {
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public void doDestroy() {
		longPressTimer.cancel();
		longPressTimer = null;
		listener = null;
	}
}
