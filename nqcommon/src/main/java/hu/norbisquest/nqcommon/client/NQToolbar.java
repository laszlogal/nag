package hu.norbisquest.nqcommon.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.App.ModeListener;
import hu.norbisquest.nagbase.game.gui.Toolbar;

import java.util.ArrayList;
import java.util.List;

public abstract class NQToolbar extends Toolbar
		implements
		ClickHandler,
		ModeListener,
		ContextMenuHandler {

	private final DialogManager dialogManager;

	private class NQToolbarButton extends Toolbar.ToolbarButton {

		NQToolbarButton(Image img) {
			super(img);
		}

		@Override
		protected void setUp() {
			super.setUp();
			addClickHandler(NQToolbar.this);
			addHandler(NQToolbar.this, ContextMenuEvent.getType());

		}

	}

	private class ModeButton extends NQToolbarButton {

		private GameModes mode;

		ModeButton(Image img, GameModes mode) {
			super(img);
			this.mode = mode;
		}

		void select() {
			addStyleName("selected");
		}

		void unselect() {
			removeStyleName("selected");

		}

	}

	private class ItemButton extends ModeButton {

		ItemButton(Image img) {
			super(img, GameModes.INVENTORY);
			addStyleName("itemButton");
			addStyleName("modeButton");
		}

		@Override
		public void select() {
			super.select();
			show();
			getUpFace().setImage(App.getCursor().getImage());
		}

		@Override
		public void unselect() {
			super.unselect();
			if (App.getInventory().getSelectedItem() == null) {
				hide();
			}
		}

		void show() {
			removeStyleName("hidden");
		}

		void hide() {
			addStyleName("hidden");
		}
	}

	private ToolbarButton btnChooser;
	private ToolbarButton btnInventory;
	private ToolbarButton btnMusic;
	private ToolbarButton btnFullscreen;
	private ToolbarButton btnExit;
	private ToolbarButton btnLoad;
	private ToolbarButton btnSave;
	private Image imgMusicOn;
	private Image imgMusicOff;
	private Image imgFullscreen;
	private Image imgFullscreenExit;
	private boolean fullChooser = false;
	private ModeButton lastModeBtn = null;
	private ItemButton itemButton = null;
	private List<ModeButton> modeButtons = null;

	protected NQToolbar(DialogManager dialogManager) {
		this.dialogManager = dialogManager;
		fullChooser = true;
        modeButtons = new ArrayList<>();

        createButtons();
		setStyleName(Browser.isAndroid() ? "toolbar-android" : "toolbar");
		sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK | Event.ONCONTEXTMENU
				| Event.TOUCHEVENTS);

		addHandler(this, ContextMenuEvent.getType());
		if (fullChooser) {
			selectModeButton(App.getMode());
		}

	}

	protected abstract void createFullChooser();

	protected abstract ToolbarButton createChooser();

	protected abstract ToolbarButton createInventory();

	protected abstract ToolbarButton createMusic();

	protected abstract ToolbarButton createFullscreen();

	protected abstract ToolbarButton createExit();

	protected abstract ToolbarButton createLoad();

	protected abstract ToolbarButton createSave();

	protected abstract void createMenu();

	@Override
	public ToolbarButton addButton(ImageResource res, final String style) {
		NQToolbarButton button = new NQToolbarButton(new Image(res));
		button.addStyleName(style);
		button.getElement().setId("id_" + style);
		addButton(button);

		return button;
	}

	protected ModeButton addModeButton(ImageResource res, GameModes mode) {
		ModeButton button = new ModeButton(new Image(res), mode);
		button.addStyleName("modeButton");
		// button.getElement().setId("id_" + style);
		addButton(button);
		modeButtons.add(button);
		return button;
	}

	private void createButtons() {
		if (hasFullChooser()) {
			createFullChooser();
		} else {
			btnChooser = createChooser();
		}
		btnInventory = createInventory();
		btnExit = createExit();
		btnLoad = createLoad();
		btnSave = createSave();
		btnMusic = createMusic();
		btnFullscreen = createFullscreen();
		createMenu();
	}

	protected ToolbarButton createMusic(ImageResource on, ImageResource off,
			String style) {
		imgMusicOn = new Image(on);
		imgMusicOff = new Image(off);
		return addButton(App.getSettings().isMusicOn() ? on : off, style);

	}

	protected ToolbarButton createFullscreen(ImageResource on, ImageResource off,
			String style) {
		imgFullscreen = new Image(on);
		imgFullscreenExit = new Image(off);
		return addButton(Browser.isFullscreen() ? off : on, style);

	}

	@Override
	public void onClick(ClickEvent event) {
		if (App.isBlocked()) {
			return;
		}
		Object source = event.getSource();
		if (source == btnChooser) {
			App.nextMode();
			App.debug("Chooser");
			modeChange(null);
		} else if (source == btnInventory) {
			showInventory();
		} else if (source == btnExit) {
			onExit();
		} else if (source == btnLoad) {
			dialogManager.showLoadDialog();
		} else if (source == btnSave) {
			getListener().save();
			dialogManager.showSaveDialog();
		} else if (source == btnMusic) {
			toggleMusic();
		} else if (source == btnFullscreen) {
			toggleFullscreen();
		} else if (source instanceof ModeButton) {
			if (lastModeBtn != null) {
				lastModeBtn.unselect();
			}
			lastModeBtn = (ModeButton) source;
			lastModeBtn.select();
			App.setMode(lastModeBtn.mode);
		}
	}

	private void onExit() {
		// dialogManager.showExitDialog();
		// App.getGame().changeScreen(App.getStage().getId());

	}

	protected void showInventory() {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				dialogManager.showInventoryDialog();

			}

			@Override
			public void onFailure(Throwable reason) {

			}
		});
	}

	private void toggleMusic() {
		boolean musicOn = App.getAudioManager().toggleMusic();
		btnMusic.getUpFace().setImage(musicOn ? imgMusicOn : imgMusicOff);
		App.getSettings().setMusicOn(musicOn);
	}

	private void toggleFullscreen() {
		Browser.toggleFullscreen();
		btnFullscreen.getUpFace().setImage(Browser.isFullscreen() ? imgFullscreenExit : imgFullscreen);
	}

	@Override
	public void modeChange(GameModes mode) {
		if (fullChooser) {
			selectModeButton(mode);
			return;
		}

		if (btnChooser == null) {
			return;
		}
		btnChooser.getUpFace().setImage(App.getCursor().getImage());

	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();

	}

	private boolean hasFullChooser() {
		return fullChooser;
	}

	public void setFullChooser(boolean fullChooser) {
		this.fullChooser = fullChooser;
	}

	private void selectModeButton(GameModes mode) {
		for (ModeButton btn : modeButtons) {
			if (btn.mode == mode) {
				btn.select();
			} else {
				btn.unselect();
			}
		}
	}

	public ModeButton getItemButton() {
		return itemButton;
	}

	protected void addItemButton(ImageResource res) {
		this.itemButton = new ItemButton(new Image());
		itemButton.hide();
		modeButtons.add(itemButton);
		addButton(itemButton);
	}
}
