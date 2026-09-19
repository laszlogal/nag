package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.ItemFrames;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.RehearsalRoom;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class RehearsalRoomModel extends StageModel {
	private static final String COAT_LAYER = "coat";
	private static final int COAT_X = 485;
	private static final int COAT_Y = 200;
	private static final int COAT_ZINDEX = Layer.Z_BACKGROUND;
	private static final String KNIFE_LAYER = "knife";
	private static final int KNIFE_X = 172;
	private static final int KNIFE_Y = 220;
	private static final int KNIFE_ZINDEX = Layer.Z_BACKGROUND;
	private static final String MARSAL_LAYER = "marsal";
	static final int MARSAL_X = 350;
	static final int MARSAL_Y = 150;
	private static final int MARSAL_ZINDEX = Layer.Z_BACKGROUND;
	private static final String GLASS_LAYER = "glass";
	private static final int GLASS_X = 310;
	private static final int GLASS_Y = 89;

	public RehearsalRoomModel() {
		id = NQ1Ids.RehearsalRoom;
		referer = NQ1Ids.RockKlub;
		backround = RehearsalRoom.INSTANCE.background();
		maskTop = null;// RehearsalRoom.INSTANCE.mask_top();
		maskBottom = RehearsalRoom.INSTANCE.mask_bottom();
		hotSpotMask = RehearsalRoom.INSTANCE.hotspots();
		heroAudio = RehearsalRoom.Data.norbi;
		heroLines = RehearsalRoom.INSTANCE.nLines();

		setGraph(RehearsalRoom.INSTANCE.graph());

		addHotSpot(RehearsalRoom.HotSpots.SOCKET, NQ1Texts.s10_socket, 65, 120);
		addHotSpot(RehearsalRoom.HotSpots.SWITCH, NQ1Texts.s10_switch, 90, 120);
		addHotSpot(RehearsalRoom.HotSpots.TRASH, NQ1Texts.s10_trash, 220, 280);
		addHotSpot(RehearsalRoom.HotSpots.RUBBISH, NQ1Texts.s10_rubbish, 325, 190);

		stageItems.add(coat());
		stageItems.add(knife());
		stageItems.add(marsal());
		stageItems.add(glass(Layer.Z_FOREGROUND1));
		music = Common.INSTANCE.rockklub_music();
		ambientNoise = Common.INSTANCE.rockklub_noise();

	}

	public static StageItem coat() {
		StageItem item = new StageItem(RehearsalRoom.HotSpots.COAT,
				NQ1Texts.s10_coat, new NAGColor(20, 20, 0));
		item.setLayer(ItemFrames.INSTANCE.coat(), COAT_LAYER, COAT_ZINDEX,
				COAT_X, COAT_Y);
		item.setVisible(true);
		return item;
	}

	public static StageItem knife() {
		StageItem item = new StageItem(RehearsalRoom.HotSpots.KNIFE,
				NQ1Texts.s10_knife, new NAGColor(64, 45, 28));
		item.setLayer(ItemFrames.INSTANCE.knife(), KNIFE_LAYER, KNIFE_ZINDEX,
				KNIFE_X, KNIFE_Y);
		item.setVisible(true);
		return item;
	}

	public static StageItem marsal() {
		StageItem item = new StageItem(RehearsalRoom.HotSpots.MARSAL,
				NQ1Texts.s10_marsal, new NAGColor(40, 0, 0));
		item.setLayer(RehearsalRoom.INSTANCE.marsal(), MARSAL_LAYER, MARSAL_ZINDEX,
				MARSAL_X, MARSAL_Y);
		item.setVisible(true);
		return item;
	}

	public static StageItem glass(int zIndex) {
		StageItem item = new StageItem(RehearsalRoom.HotSpots.GLASS, -1,
				new NAGColor(0, 0, 0));
		item.setLayer(ItemFrames.INSTANCE.glass(), GLASS_LAYER, zIndex, GLASS_X,
				GLASS_Y);
		item.setVisible(true);
		return item;
	}

}