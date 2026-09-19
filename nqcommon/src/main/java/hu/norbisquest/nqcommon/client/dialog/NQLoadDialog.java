package hu.norbisquest.nqcommon.client.dialog;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.gui.NAGSettingsListBox;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class NQLoadDialog extends NAGPopupPanel
		implements
		ClickHandler,
		ContextMenuHandler, ChangeHandler {
	private FlowPanel main;
	private NAGSettingsListBox lbKeys;
	private PushButton btnLoad;

	protected NQLoadDialog(Scalable scaler) {
		super(scaler);
		addStyleName("loadDialog");
		setBackground(getBackgroundResource());
		main = new FlowPanel();
		createTitle();
		createItems();
		createButtons();
		setWidget(main);
		setGlassEnabled(true);
		setModal(true);
		lbKeys.addChangeHandler(this);

	}

	protected abstract void createTitle();

	protected abstract ImageResource getBackgroundResource();

	protected abstract ImageResource getLoadResource();

	protected abstract ImageResource getCancelResource();

	private void createItems() {
		lbKeys = new NAGSettingsListBox();
		lbKeys.update();
		main.add(lbKeys);
	}

	protected void createButtons() {
		FlowPanel buttons = new FlowPanel();
		buttons.addStyleName("buttons");

		btnLoad = new PushButton(new Image(getLoadResource()));
		btnLoad.addStyleName("ok");

		PushButton btnCancel = new PushButton(new Image(getCancelResource()));

		buttons.add(btnLoad);
		buttons.add(btnCancel);

		btnLoad.addClickHandler(this);
		btnLoad.addStyleName("ok");

		btnCancel.addClickHandler(this);
		main.add(buttons);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		if (source == btnLoad) {
			load();

		}
		hide(true);
	}

	private void load() {
		Stage stage = App.getCurrentStage();
		if (stage != null) {
			stage.clean();
		}
		App.getSettings().load(lbKeys.getSelectedItemText());
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();
	}

	protected void addWidget(Widget w) {
		main.add(w);
	}

	@Override
	public void onChange(ChangeEvent event) {
		load();
		hide(true);
	}
}
