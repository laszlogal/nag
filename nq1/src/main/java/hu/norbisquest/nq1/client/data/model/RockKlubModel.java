package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.ItemFrames;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.RockKlub;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class RockKlubModel extends StageModel {

	private static final String SNIPS_LAYER = "snips";
	private static final int SNIPS_X = 363;
	private static final int SNIPS_Y = 324;
	private static final int SNIPS_ZINDEX = Layer.Z_BACKGROUND;

	public RockKlubModel() {
		id = NQ1Ids.RockKlub;
		backround = RockKlub.INSTANCE.background();
		maskTop = RockKlub.INSTANCE.mask_top();
		hotSpotMask = RockKlub.INSTANCE.hotspots();

		music = Common.INSTANCE.rockklub_music();
		ambientNoise = Common.INSTANCE.rockklub_noise();

		autoPlayMusic = false;
		heroAudio = RockKlub.Data.norbi;
		heroLines = RockKlub.INSTANCE.nLines();

		addHotSpot(RockKlub.HotSpots.CLUBCHAIR, NQ1Texts.s09_clubchair, 340, 250);
		addHotSpot(RockKlub.HotSpots.PIPE, NQ1Texts.s09_pipe, 85, 110);
		stageItems.add(snips());

		setGraph(RockKlub.INSTANCE.graph());
	}

	private static StageItem snips() {
		StageItem item = new StageItem(RockKlub.HotSpots.SNIPS, NQ1Texts.s09_snips,
				new NAGColor(60, 0, 0));
		item.setLayer(ItemFrames.INSTANCE.snips(), SNIPS_LAYER, SNIPS_ZINDEX,
				SNIPS_X, SNIPS_Y);
		item.setVisible(false);
		return item;
	}
}