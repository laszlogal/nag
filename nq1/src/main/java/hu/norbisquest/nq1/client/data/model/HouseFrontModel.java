package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.HouseFront;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class HouseFrontModel extends StageModel {
	public HouseFrontModel() {
		id = NQ1Ids.HouseFront;
		backround = HouseFront.INSTANCE.background();
		maskTop = HouseFront.INSTANCE.mask_top();
		hotSpotMask = HouseFront.INSTANCE.hotspots();

		music = null;
		ambientNoise = Common.INSTANCE.suburb_noise();

		autoPlayMusic = false;
		heroAudio = HouseFront.Data.norbi;
		heroLines = HouseFront.INSTANCE.nLines();
		addHotSpot(HouseFront.HotSpots.HOUSE_DOOR, NQ1Texts.s04_house_door,
				60, 180);
		addHotSpot(HouseFront.HotSpots.DOOR_PHONE, NQ1Texts.s04_door_phone,
				150, 175);
		addHotSpot(HouseFront.HotSpots.NONSTOP, NQ1Texts.s04_nonstop, 360, 180);
		addHotSpot(HouseFront.HotSpots.POSTER, NQ1Texts.s04_poster, 530, 90);
		setGraph(HouseFront.INSTANCE.graph());

	}

}