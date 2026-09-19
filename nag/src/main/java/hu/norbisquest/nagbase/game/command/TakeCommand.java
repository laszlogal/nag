package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.target.InventoryItem;
import hu.norbisquest.nagbase.game.target.StageItem;

public class TakeCommand extends OneShotCommand {

	private InventoryItem item;
	private StageItem stageItem;

	public TakeCommand(InventoryItem item) {
		this.item = item;
		stageItem = null;
	}

	public TakeCommand(StageItem stageItem, InventoryItem item) {
		this.item = item;
		this.stageItem = stageItem;
	}

	@Override
	public void start() {
		if (stageItem != null) {
			stageItem.setVisible(false);
		}
		App.getInventory().put(item);
	}
}
