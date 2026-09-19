package hu.norbisquest.nagbase.game.gui;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings;

import java.util.List;

public class NAGSettingsListBox extends NAGListBox {

	public void update() {
		clear();
		List<String> keys = App.getSettings().getSavedKeys();
		for (String key: keys) {
			addItem(key.split(Settings.TAG_PREFIX)[1]);
		}
	}
}
