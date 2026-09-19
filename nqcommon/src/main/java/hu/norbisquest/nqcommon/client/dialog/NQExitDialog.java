package hu.norbisquest.nqcommon.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class NQExitDialog extends NAGPopupPanel
		implements
			ClickHandler,
			ContextMenuHandler {
	private FlowPanel main;
	private PushButton btnExit;
	private SettingsChanged listener;
	protected NQExitDialog(Scalable scaler, SettingsChanged listener) {
		super(scaler);
		addStyleName("exitDialog");
		this.setListener(listener);
		main = new FlowPanel();
		createTitle();
		createButtons();
		setWidget(main);
		setGlassEnabled(true);
		setModal(true);

	}

	private void createTitle() {
		Label title = new Label(getTitle());
		title.addStyleName("title");
		main.add(title);
	}

	protected abstract ImageResource getBackgroundResource();
	protected abstract ImageResource getExitResource();
	protected abstract ImageResource getCancelResource();

	private void createButtons() {
		FlowPanel buttons = new FlowPanel();
		buttons.addStyleName("buttons");

		btnExit = new PushButton(new Image(getExitResource()));
		btnExit.addStyleName("ok");

		PushButton btnCancel = new PushButton(new Image(getCancelResource()));

		buttons.add(btnExit);
		buttons.add(btnCancel);

		btnExit.addClickHandler(this);
		btnExit.addStyleName("ok");

		btnCancel.addClickHandler(this);

		main.add(buttons);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		if (source == btnExit) {
			onExit();
		}
		hide(true);
	}

	protected abstract void onExit();

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();
	}

	protected SettingsChanged getListener() {
		return listener;
	}

	private void setListener(SettingsChanged listener) {
		this.listener = listener;
	}
}
