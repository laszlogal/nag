package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.ItemFrames;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.NewsStand;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class NewsStandModel extends StageModel {
	private static final String HOBO_HAT_LAYER = "HOBO_HAT";
	private static final int HOBO_HAT_X = 175;
	private static final int HOBO_HAT_Y = 282;
	private static final int HOBO_HAT_ZINDEX = Layer.Z_BACKGROUND;

	public NewsStandModel() {
		id = NQ1Ids.NewsStand;
		backround = NewsStand.INSTANCE.background();
		hotSpotMask = NewsStand.INSTANCE.hotspots();

		ambientNoise = Common.INSTANCE.suburb_noise();

		autoPlayMusic = false;
		heroAudio = NewsStand.Data.norbi;
		heroLines = NewsStand.INSTANCE.nLines();
		addHotSpot(NewsStand.HotSpots.TRASH, NQ1Texts.s05_trash, 170, 215);
		addHotSpot(NewsStand.HotSpots.NEWSPAPERS1, NQ1Texts.s05_newspaper, 360, 140);
		addHotSpot(NewsStand.HotSpots.NEWSPAPERS2, NQ1Texts.s05_newspaper, 560, 130);
		addHotSpot(NewsStand.HotSpots.DAILIES, NQ1Texts.s05_dailies, 540, 220);
		stageItems.add(hobo_hat());
		setGraph(NewsStand.INSTANCE.graph());
		bgXDiff = backround.getWidth() - App.getWidth();
		App.debug("BACKGROUND DIFFERENCE IS " + bgXDiff);
	}

	private static StageItem hobo_hat() {
		StageItem item = new StageItem(NewsStand.HotSpots.HOBO_HAT,
				NQ1Texts.s05_hobo_hat, new NAGColor(50, 0, 0));
		item.setLayer(ItemFrames.INSTANCE.s05_hobo_hat(), HOBO_HAT_LAYER,
				HOBO_HAT_ZINDEX, HOBO_HAT_X, HOBO_HAT_Y);
		item.setVisible(true);
		return item;
	}

}