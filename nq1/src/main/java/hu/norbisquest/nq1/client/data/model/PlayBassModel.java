package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.PlayBass;
import hu.norbisquest.nq1.client.data.bundle.RehearsalRoom;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class PlayBassModel extends StageModel {

	private static final String MARSAL_LAYER = "burned_marsal";
	private static final int MARSAL_ZINDEX = Layer.Z_BACKGROUND;

	public PlayBassModel() {
		id = NQ1Ids.PlayBass;
		referer = NQ1Ids.RehearsalRoom;
		backround = RehearsalRoom.INSTANCE.background();
		maskTop = null;// RehearsalRoom.INSTANCE.mask_top();
		maskBottom = RehearsalRoom.INSTANCE.mask_bottom();
		hotSpotMask = RehearsalRoom.INSTANCE.hotspots();
		heroAudio = RehearsalRoom.Data.norbi;
		heroLines = RehearsalRoom.INSTANCE.nLines();

		setGraph(RehearsalRoom.INSTANCE.graph());

		stageItems.add(RehearsalRoomModel.coat());
		stageItems.add(RehearsalRoomModel.knife());
		stageItems.add(RehearsalRoomModel.marsal());
		stageItems.add(marsal_burned());
		stageItems.add(RehearsalRoomModel.glass(Layer.Z_BACKGROUND));

	}

	private static StageItem marsal_burned() {
		StageItem item = new StageItem(RehearsalRoom.HotSpots.MARSAL_BURNED,
				NQ1Texts.s10_marsal, new NAGColor(40, 0, 0));
		item.setLayer(PlayBass.INSTANCE.marsal_burned(), MARSAL_LAYER, MARSAL_ZINDEX,
				RehearsalRoomModel.MARSAL_X, RehearsalRoomModel.MARSAL_Y);
		item.setVisible(false);
		return item;
	}
}