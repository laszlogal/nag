package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.VRKFront;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class VRKFrontModel extends StageModel {
	private static final String wgraph = "scale 1.1 1.15;" +
			"v 15 260 BusStop;" +
			"v 92 287 A;" +
			"v 180 300 ADVERT;" +
			"v 252 315 SIGN;" +
			"v 355 365 S;" +
			"v 423 290 RockKlub;" +
			"v 458 370 Punk;" +
			"cAll BusStop A ADVERT SIGN S;" +
			"c S RockKlub;" +
			"c S Punk;";
	private static final int DOOR_X = 425;
	private static final int DOOR_Y = 111;

	public VRKFrontModel() {
		id = NQ1Ids.VRKFront;
		backround = VRKFront.INSTANCE.background();
		maskBottom = VRKFront.INSTANCE.mask_bottom();
		hotSpotMask = VRKFront.INSTANCE.hotspots();

		music = Common.INSTANCE.rockklub_music();
		ambientNoise = VRKFront.INSTANCE.town_noise();

		addHotSpot(VRKFront.HotSpots.SIGN, NQ1Texts.s08_sign, 245, 90);
		addHotSpot(VRKFront.HotSpots.AVERTISMENTS, NQ1Texts.s08_advertisments,
				215, 170);
		addHotSpot(VRKFront.HotSpots.ELETRIC_BOX, NQ1Texts.s08_electric_box,
				125, 285);
		addHotSpot(VRKFront.HotSpots.CANNAL, NQ1Texts.s08_cannal, 245, 370);
		stageItems.add(door());

		autoPlayMusic = true;
		heroAudio = VRKFront.Data.norbi;
		heroLines = VRKFront.INSTANCE.nLines();

		setGraph(VRKFront.INSTANCE.graph());
		graphData = wgraph;
	}

	private static StageItem door() {
		StageItem item = new StageItem(VRKFront.HotSpots.DOOR, NQ1Texts.items_alcohol,
				new NAGColor(90, 0, 0));
		item.setLayer(VRKFront.INSTANCE.door(), "door", Layer.Z_FOREGROUND1,
				DOOR_X, DOOR_Y);
//		item.setVisible(false);
		return item;
	}

}