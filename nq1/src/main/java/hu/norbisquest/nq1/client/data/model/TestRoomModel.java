package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Room;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
public class TestRoomModel extends StageModel {

	private static final String walk = "scale 1.1 1.1;" +
			"v 140 355 S;" +
			"v 600 355 A;" +
			"cAll S A;"
			;
	public TestRoomModel() {
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

	}

}