package hu.norbisquest.nq1.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ToggleButton;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.common.gui.HasMenu;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nqcommon.client.NQToolbar;

public class NQ1Toolbar extends NQToolbar {

	private ToggleButton toggleChooser;
	private ToggleButton toggleInventory;
	private ToggleButton toggleItem;
	private PushButton btnMenu;
    private Image itemImage = null;
	private HasMenu menu;

	public NQ1Toolbar(DialogManager dialogManager, HasMenu menu) {
		super(dialogManager);
		this.menu = menu;
	}

	@Override
	protected ToolbarButton createChooser() {
		return addButton(Common.INSTANCE.cursor_normal(), "chooser");
	}

	@Override
	protected ToolbarButton createInventory() {
		toggleItem = ButtonPanel.createFloatToggleButton(this);
		add(toggleItem);
		toggleItem.addStyleName("hidden");
		toggleInventory = ButtonPanel.createFloatToggleButton(Icons.get().toolbar_inventory(),
				Icons.get().toolbar_inventory(),this);
		toggleInventory.getElement().setAttribute("aria-label", "Open inventory");
		toggleInventory.getElement().setAttribute("title", "Inventory");
		add(toggleInventory);
		return null;
	}

	@Override
	protected ToolbarButton createMusic() {
		return null;
//		return createMusic(Common.INSTANCE.toolbar_music_on(),
//				Common.INSTANCE.toolbar_music_off(), "music");
	}


	@Override
	protected ToolbarButton createFullscreen() {
		return null;
	}

	@Override
	protected ToolbarButton createExit() {
		return null ;
		//addButton(Common.INSTANCE.toolbar_exit(), "quit");
	}

	@Override
	protected ToolbarButton createLoad() {
		return null;
		//addButton(Common.INSTANCE.toolbar_load(), "load");
	}

	@Override
	protected ToolbarButton createSave() {
		return null;
//		addButton(Common.INSTANCE.toolbar_save(), "save");
	}

	@Override
	protected void createMenu() {
		btnMenu = new PushButton(new Image(Icons.get().burger_white()));
		btnMenu.setStyleName("burgerMenuButton");
		btnMenu.getElement().setAttribute("aria-label", "Open game menu");
		btnMenu.getElement().setAttribute("title", "Menu");
		add(btnMenu);
		btnMenu.addClickHandler(this);
	}

	@Override
	protected void createFullChooser() {
		toggleChooser = ButtonPanel.createFloatToggleButton(Icons.get().examine_icon(), Icons.get().use_icon(), this);
		toggleChooser.getElement().setAttribute("aria-label", "Switch interaction mode");
		toggleChooser.getElement().setAttribute("title", "Look / use");
		add(toggleChooser);
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		if (value) {
			removeStyleName("disabled");
		} else {
			addStyleName("disabled");
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (menu.hideMenu()) {
			return;
		}
		Object src = event.getSource();
		if (src == toggleChooser) {
			App.setMode(toggleChooser.isDown() ? GameModes.USE: GameModes.EXAMINE);
		} else if (src == toggleInventory) {
			showInventory();
            boolean toggled = true;
		} if (src == toggleItem) {
			App.resetMode();
		} else if (src == btnMenu) {
			menu.toggleMenu();
		} else {
			super.onClick(event);
		}
	}

	@Override
	public void modeChange(GameModes mode) {
		super.modeChange(mode);
		if (mode == GameModes.INVENTORY) {
			toggleChooser.addStyleName("hidden");
			setItemAsMode();
		} else {
			App.getInventory().unselect();
			toggleItem.addStyleName("hidden");
			toggleChooser.removeStyleName("hidden");
			toggleChooser.setDown(mode == GameModes.USE);
		}
	}

	private void setItemAsMode() {
		toggleItem.removeStyleName("hidden");
		if (itemImage != null) {
			itemImage = null;
		}
		itemImage = new Image(App.getInventory().getSelectedItem().getImage().getGWTImage().getUrl());
		toggleItem.getUpFace().setImage(itemImage);
	}
}
