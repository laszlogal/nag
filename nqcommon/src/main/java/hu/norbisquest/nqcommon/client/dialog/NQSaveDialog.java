package hu.norbisquest.nqcommon.client.dialog;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.*;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.gui.NAGSettingsListBox;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class NQSaveDialog extends NAGPopupPanel
		implements
			ClickHandler,
			ContextMenuHandler {
	private FlowPanel main;
	private TextBox tbSavedKey;
	private NAGSettingsListBox lbKeys;
	private PushButton btnSave;
	protected NQSaveDialog(Scalable scaler) {
		super(scaler);
		addStyleName("saveDialog");
		setBackground(getBackgroundResource());
		main = new FlowPanel();
		createTitle();
		createItems();
		createButtons();
		setWidget(main);
		setGlassEnabled(true);
		setModal(true);

	}

	protected abstract ImageResource getBackgroundResource();
	protected abstract ImageResource getSaveResource();
	protected abstract ImageResource getCancelResource();
	protected abstract void createTitle();

	private void createItems() {
		tbSavedKey = new TextBox();
		lbKeys = new NAGSettingsListBox();
		lbKeys.addChangeHandler(event -> tbSavedKey.setValue(lbKeys.getSelectedItemText()));

		lbKeys.update();
		tbSavedKey.addKeyPressHandler(event -> {
            if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                save();
            }
        });
		main.add(tbSavedKey);
		main.add(lbKeys);
	}
	private void createButtons() {
		FlowPanel buttons = new FlowPanel();
		buttons.addStyleName("buttons");

		btnSave = new PushButton(new Image(getSaveResource()));
		btnSave.addStyleName("ok");

		PushButton btnCancel = new PushButton(new Image(getCancelResource()));

		buttons.add(btnSave);
		buttons.add(btnCancel);

		btnSave.addClickHandler(this);
		btnSave.addStyleName("ok");

		btnCancel.addClickHandler(this);
		main.add(buttons);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		if (source == btnSave) {
			save();
		}
		hide(true);
	}

	private void save() {
		String tag = tbSavedKey.getValue();
		App.debug("SAVING " + tag);
		App.getSettings().save(tag);
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();
	}

	protected void addWidget(Widget w) {
		main.add(w);
	}
}
