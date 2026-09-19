package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.game.NAGGame;
import hu.norbisquest.nagbase.game.Settings.SettingsChanged;
import hu.norbisquest.nagbase.game.gui.GameBuilder;
import hu.norbisquest.nq1.client.factory.InventoryFactory;
import hu.norbisquest.nq1.client.gui.ButtonPanel;

public class NQ1Game extends NAGGame implements SettingsChanged {

	private final ButtonPanel buttonPanel = new ButtonPanel();

	@Override
	protected void onLaunch() {
		addStyleName("gui");
		if (NQ1Settings.get().isInventoryFill()) {
			InventoryFactory.get().fill();
		}

		add(buttonPanel);
	}

	@Override
	public void setUserZoomEnabled(boolean b) {
		//hey?!!
	}

	@Override
	protected GameBuilder getBuilder() {
		return new NQ1Builder(this);
	}

	@Override
	public void onSettingsChanged() {
		// remove me from here
	}
}
