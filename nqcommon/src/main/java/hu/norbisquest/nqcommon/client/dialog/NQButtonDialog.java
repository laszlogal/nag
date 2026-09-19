package hu.norbisquest.nqcommon.client.dialog;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class NQButtonDialog extends NAGPopupPanel
		implements
			ClickHandler,
			ContextMenuHandler {
	private FlowPanel main;

	NQButtonDialog(Scalable scaler) {
		super(scaler);
		main = new FlowPanel();
		setWidget(main);
		setGlassEnabled(true);

		sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK | Event.ONCONTEXTMENU
				| Event.TOUCHEVENTS);

		addHandler(this, ContextMenuEvent.getType());
		setModal(true);

	}

	protected abstract void createButtons();

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();
	}

	FlowPanel getMain() {
		return main;
	}
	public void setMain(FlowPanel main) {
		this.main = main;
	}
}
