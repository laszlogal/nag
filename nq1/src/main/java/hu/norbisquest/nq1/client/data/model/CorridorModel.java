package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Corridor;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class CorridorModel extends StageModel {
	public CorridorModel() {
		id = NQ1Ids.Corridor;
		backround = Corridor.INSTANCE.background();
		maskTop = Corridor.INSTANCE.mask_top();
		hotSpotMask = Corridor.INSTANCE.hotspots();

		music = Common.INSTANCE.warren_music();
		autoPlayMusic = true;
		heroAudio = Corridor.Data.norbi;
		heroLines = Corridor.INSTANCE.nLines();
		addHotSpot(Corridor.HotSpots.WARDROBE, NQ1Texts.s02_wardrobe, 90, 190);
		addHotSpot(Corridor.HotSpots.SHOES, NQ1Texts.s02_shoes, 135, 295);
		addHotSpot(Corridor.HotSpots.FRONT_DOOR, NQ1Texts.s02_front_door, 200, 190);
		addHotSpot(Corridor.HotSpots.DOOR_PHONE, NQ1Texts.s02_telephone, 285, 165);
		addHotSpot(Corridor.HotSpots.KEYS, NQ1Texts.s02_keys, 320, 160);
		addHotSpot(Corridor.HotSpots.TOILET_DOOR, NQ1Texts.s02_wc_door, 390, 200);
		addHotSpot(Corridor.HotSpots.SWITCH, NQ1Texts.s02_switch, 470, 90);
		addHotSpot(Corridor.HotSpots.BATHROOM_DOOR, NQ1Texts.s02_bathroom_door, 600, 200);
		music = Common.INSTANCE.warren_music();

		setGraph(Corridor.INSTANCE.graph());

	}

}