package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.target.HotSpot.Id;

public class LooseCommand extends OneShotCommand {

	private Id itemId;

	public LooseCommand(Id itemId) {
		this.itemId = itemId;
	}

	@Override
	public void start() {
		if (App.getInventory().has(itemId)) {
			App.getInventory().remove(itemId);
			App.setMode(GameModes.NORMAL);
		} else {
			App.warn("No such item to loose: " + itemId);
		}
	}
}
