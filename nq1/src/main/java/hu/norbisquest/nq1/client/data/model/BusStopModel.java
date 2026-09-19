package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.ItemFrames;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.BusStop;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class BusStopModel extends StageModel {
	private static final String BUS_DOOR_LAYER = "bus_door";
	private static final int BUS_FRONT_X = 417;
	private static final int BUS_FRONT_Y = 81;

	private static final int BUS_BACK_X = 88;
	private static final int BUS_BACK_Y = 83;

	private static final int BUS_ZINDEX = Layer.Z_BACKGROUND2;

	public BusStopModel() {
		id = NQ1Ids.BusStop;
		backround = BusStop.INSTANCE.background();
		maskTop = BusStop.INSTANCE.mask_top();
		hotSpotMask = BusStop.INSTANCE.hotspots();

		music = Common.INSTANCE.warren_music();
		ambientNoise = Common.INSTANCE.suburb_noise();

		autoPlayMusic = false;
		heroAudio = BusStop.Data.norbi;
		heroLines = BusStop.INSTANCE.nLines();
		addHotSpot(BusStop.HotSpots.SEAT, NQ1Texts.s06_seat, 320, 300);
		addHotSpot(BusStop.HotSpots.TRASH, NQ1Texts.s06_trash, 570, 240);
		addHotSpot(BusStop.HotSpots.TIMETABLE, NQ1Texts.s06_timetable, 585, 162);
		addHotSpot(BusStop.HotSpots.BALL, NQ1Texts.s06_ball, 345, 95);

		stageItems.add(front_door());
		stageItems.add(back_door());

		setGraph(BusStop.INSTANCE.graph());

	}

	private static StageItem front_door() {
		StageItem item = new StageItem(BusStop.HotSpots.FRONT_DOOR,
				NQ1Texts.s06_front_door, new NAGColor(20, 0, 0));
		item.setLayer(ItemFrames.INSTANCE.bus_front_door(), BUS_DOOR_LAYER,
				BUS_ZINDEX, BUS_FRONT_X, BUS_FRONT_Y);
		// item.setVisible(false);
		return item;
	}

	private static StageItem back_door() {
		StageItem item = new StageItem(BusStop.HotSpots.BACK_DOOR,
				NQ1Texts.s06_back_door, new NAGColor(20, 20, 0));
		item.setLayer(ItemFrames.INSTANCE.bus_back_door(), BUS_DOOR_LAYER + "2",
				BUS_ZINDEX, BUS_BACK_X, BUS_BACK_Y);
		item.setVisible(false);
		return item;
	}

}