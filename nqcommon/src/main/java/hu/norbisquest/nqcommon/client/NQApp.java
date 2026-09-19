package hu.norbisquest.nqcommon.client;

import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.target.InventoryItem;
import hu.norbisquest.nagbase.game.target.ItemResource;
import hu.norbisquest.nqcommon.client.control.ExamineControl;

public class NQApp {
	public static InventoryItem createItemExamineOnly(ItemResource res,
			SpeechEnum... speeches) {
		InventoryItem item = new InventoryItem(res);
		item.setControl(new ExamineControl(speeches));
		return item;
	}
}
