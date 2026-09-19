package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Room;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class RoomModel extends StageModel {
	private static final String DRAWER_LAYER = "drawer";
	private static final int DRAWER_X = 499;
	private static final int DRAWER_Y = 273;
	private static final int DRAWER_ZINDEX = Layer.Z_FOREGROUND2;

	private static final String WARDROBE_DOOR_LAYER = "wd_door";
	private static final int WARDROBE_DOOR_X = 249;
	private static final int WARDROBE_DOOR_Y = 236;
	private static final int WARDROBE_DOOR_ZINDEX = Layer.Z_BACKGROUND;

	public RoomModel() {
		id = NQ1Ids.Room;
		backround = Room.INSTANCE.background();
		maskTop = Room.INSTANCE.mask_top();
		maskBottom = Room.INSTANCE.mask_bottom();
		hotSpotMask = Room.INSTANCE.hotspots();

		music = Common.INSTANCE.warren_music();
		autoPlayMusic = false;
		heroAudio = Room.Data.norbi;
		heroLines = Room.INSTANCE.nLines();

		setGraph(Room.INSTANCE.graph());

		addHotSpot(Room.HotSpots.WINDOW, NQ1Texts.s01_window, 40, 200);
		addHotSpot(Room.HotSpots.POSTER, NQ1Texts.s01_poster, 160, 85);
		addHotSpot(Room.HotSpots.DVD_PLAYER, NQ1Texts.s01_dvd, 160, 150);
		addHotSpot(Room.HotSpots.TV, NQ1Texts.s01_tv, 160, 200);
		addHotSpot(Room.HotSpots.BED, NQ1Texts.s01_bed, 260, 360);
		addHotSpot(Room.HotSpots.POWERSTRIP, NQ1Texts.s01_powerstrip, 230, 310);
		addHotSpot(Room.HotSpots.WARDROBE, NQ1Texts.s01_wardrobe, 320, 100);
		addHotSpot(Room.HotSpots.BOOKS, NQ1Texts.s01_books, 320, 190);
		addHotSpot(Room.HotSpots.BOX, NQ1Texts.s01_box, 300, 220);
		addHotSpot(Room.HotSpots.PICTURE, NQ1Texts.s01_picture, 465, 110);
		addHotSpot(Room.HotSpots.DESK, NQ1Texts.s01_desk, 600, 300);
		addHotSpot(Room.HotSpots.DOOR, NQ1Texts.s01_door, 575, 200);

		stageItems.add(drawer());
		stageItems.add(wardrobe_door());

	}

	private static StageItem drawer() {
		StageItem item = new StageItem(Room.HotSpots.DRAWER, NQ1Texts.s01_drawer,
				new NAGColor(1, 1, 1));
		item.setLayer(Room.INSTANCE.drawer(), DRAWER_LAYER,
				DRAWER_ZINDEX, DRAWER_X, DRAWER_Y);
		return item;
	}

	private static StageItem wardrobe_door() {
		StageItem item = new StageItem(Room.HotSpots.WARDROBE_DOOR,
				NQ1Texts.s01_wardrobe, new NAGColor(111, 111, 111));
		item.setLayer(Room.INSTANCE.wardrobe_door(),
				WARDROBE_DOOR_LAYER, WARDROBE_DOOR_ZINDEX, WARDROBE_DOOR_X,
				WARDROBE_DOOR_Y);
		return item;
	}
}