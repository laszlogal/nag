package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;

public class Toolbar extends FlowPanel implements ZoomHandler {
	public interface ToolbarListener {
		void save();
	}

	public class ToolbarButton extends PushButton {
		public ToolbarButton(Image img) {
			super(img);
			setUp();
		}

		protected void setUp() {
			// sinkEvents(//Event.MOUSEEVENTS | Event.ONCLICK |
			// Event.ONCONTEXTMENU);// | Event.TOUCHEVENTS);
		}
	}

	public enum ToolbarPosition {
		TOP, LEFT, RIGHT, BOTTOM
	}

	private static final int DEFAULT_BUTTON_HEIGHT = 32;
	private static final int MARGIN_RATIO = 10;
	private static final int MARGIN_LEFT = 0;
	private ToolbarPosition position;
	private int buttonHeight;
	private int margin;
	private boolean show;
	private ToolbarListener listener = null;
	private boolean enabled;

	protected Toolbar() {
		// Toolbar is alwas the top layer
		getElement().getStyle().setZIndex(Integer.MAX_VALUE);
		setButtonHeight(DEFAULT_BUTTON_HEIGHT);
		setPosition(ToolbarPosition.TOP);

		addDomHandler(event -> {
            if (show) {
                return;
            }
            show = true;

            if (!Browser.isAndroid()) {
                App.suspend();
            }

        }, MouseOverEvent.getType());

		addDomHandler(event -> {
            if (!Browser.isAndroid()) {
                App.resume();
            }
            show = false;
        }, MouseOutEvent.getType());
	}

	protected void addButton(ToolbarButton button) {
		button.getElement().getStyle().setTop(margin, Unit.PX);
		add(button);
	}

	public ToolbarButton addButton(ImageResource res, final String style) {
		ToolbarButton button = new ToolbarButton(new Image(res));
		button.addStyleName(style);
		addButton(button);
		return button;
	}

	public ToolbarPosition getPosition() {
		return position;
	}

	private void setPosition(ToolbarPosition position) {
		this.position = position;
		int top = 0;
		int left = 0;
		switch (position) {
		case BOTTOM:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case TOP:
			top = margin;
			left = MARGIN_LEFT + margin;
			setWidth(App.getSettings().getScreenWidth() - left + "px");
			setHeight(getButtonHeight() + margin + "px");
			break;
		default:
			break;

		}
		getElement().getStyle().setTop(top, Unit.PX);
		getElement().getStyle().setLeft(left, Unit.PX);
	}

	private int getButtonHeight() {
		return buttonHeight;
	}

	private void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
		margin = buttonHeight / MARGIN_RATIO;
	}

	@Override
	public void setVisible(boolean value) {
		for (Widget w : getChildren()) {
			w.setVisible(value);
		}
	}

	protected ToolbarListener getListener() {
		return listener;
	}

	public void setListener(ToolbarListener listener) {
		this.listener = listener;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean value) {
		enabled = value;
		update();
	}

	public void update() {}
	public void reset() {}


	@Override
	public void onZoomIn() {
		if (Browser.isAndroid()) {
			setVisible(false);
		}
	}

	@Override
	public void onZoomOut() {
		if (Browser.isAndroid()) {
			setVisible(true);
		}
	}
}
