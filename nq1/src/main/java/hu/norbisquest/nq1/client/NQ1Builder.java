package hu.norbisquest.nq1.client;

import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Window;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.common.gui.HasContent;
import hu.norbisquest.nagbase.game.AbstractDialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.Cursor;
import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nagbase.game.engine.ScreenProvider;
import hu.norbisquest.nagbase.game.gui.GameBuilder;
import hu.norbisquest.nagbase.game.target.ItemFactory;
import hu.norbisquest.nq1.client.data.bundle.CSS;
import hu.norbisquest.nq1.client.factory.InventoryFactory;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.gui.dialog.NQ1DialogManager;

import java.util.List;

public class NQ1Builder extends GameBuilder {

	private final SettingsChanged listener;

	NQ1Builder(SettingsChanged listener) {
		this.listener = listener;
	}
	@Override
	protected Settings createSettings() {
		return new NQ1Settings(listener);
	}

	@Override
	protected AbstractDialogManager createDialogManager() {
		return new NQ1DialogManager(listener);
	}

	@Override
	protected Cursor createCursor() {
		return new NQ1Cursor();
	}

	@Override
	protected ItemFactory getItemFactory() {
		return InventoryFactory.get();
	}

	@Override
	protected GameModes getDefaultMode() {
		return GameModes.USE;
	}

	@Override
	protected List<TextResource> getCssResources() {
		return CSS.Data.all;
	}

	@Override
	public ScreenProvider ceateScreenProvider(HasContent parent) {
		return new NQ1ScreenProvider(parent);
	}

	@Override
	public GameGUI createGui(DialogManager dialogManager) {
		return new NQ1GameGui(dialogManager);
	}

	@Override
	protected void parseParameters() {
		String language = Window.Location.getParameter("lang");
		if (language != null) {
			App.getSettings().setLanguage(language);

		}

		String anim = Window.Location.getParameter("a");
		String move = Window.Location.getParameter("m");
		String slope = Window.Location.getParameter("s");
		String autoPilot = Window.Location.getParameter("ap");
		String inventory = Window.Location.getParameter("inv");

		if (anim != null) {
			App.getSettings().setAnimSpeed(Integer.parseInt(anim));
		}
		if (move != null) {
			App.getSettings().setMoveSpeed(Integer.parseInt(move));
		}

		if (slope != null) {
			App.getSettings().setSlopeSpeed(Integer.parseInt(slope));
		}

		String id = Window.Location.getParameter("id");

		if (id != null) {
			NQ1Ids screenId = NQ1Ids.valueOf(id);
			NQ1Settings.get().setStartId(screenId);
		}

		String ref = Window.Location.getParameter("ref");

		if (ref != null) {
			NQ1Ids referer = NQ1Ids.valueOf(ref);
			NQ1Settings.get().setReferer(referer);
		}

		if (autoPilot != null) {
            Settings.AutoPilot ap = Settings.AutoPilot.valueOf(autoPilot);
            App.getSettings().setAutoPilot(ap);
        }


		if (inventory != null) {
			NQ1Settings.get().setInventoryFill(true);
		}
	}
}
