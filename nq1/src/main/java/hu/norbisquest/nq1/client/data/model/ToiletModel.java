package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.ItemFrames;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Toilet;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class ToiletModel extends StageModel {
	private static final String TOILET_PAPER_LAYER = "toilet_paper";
	private static final int TOILET_PAPER_X = 288;
	private static final int TOILET_PAPER_Y = 200;
	private static final int TOILET_PAPER_ZINDEX = Layer.Z_BACKGROUND;

	private static final String TOILET_BRUSH_LAYER = "toilet_brush";
	private static final int TOILET_BRUSH_X = 356;
	private static final int TOILET_BRUSH_Y = 230;
	private static final int TOILET_BRUSH_ZINDEX = Layer.Z_BACKGROUND;

	private static final String TOILET_CHAIN_LAYER = "toilet_chain";
	private static final int TOILET_CHAIN_X = 408;
	private static final int TOILET_CHAIN_Y = 128;
	private static final int TOILET_CHAIN_ZINDEX = Layer.Z_BACKGROUND;

	public ToiletModel() {
		id = NQ1Ids.Corridor;
		backround = Toilet.INSTANCE.background();
		maskTop = Toilet.INSTANCE.mask_top();
		hotSpotMask = Toilet.INSTANCE.hotspots();

		music = Common.INSTANCE.warren_music();
		autoPlayMusic = false;
		heroAudio = Toilet.Data.norbi;
		heroLines = Toilet.INSTANCE.nLines();
		addHotSpot(Toilet.HotSpots.DOOR, NQ1Texts.s03_door, 200, 210);
		addHotSpot(Toilet.HotSpots.LAMP, NQ1Texts.s03_lamp, 210, 45);
		addHotSpot(Toilet.HotSpots.SWITCH, NQ1Texts.s03_switch, 260, 135);
		addHotSpot(Toilet.HotSpots.PAPER_HOLDER, NQ1Texts.s03_paper_holder, 300, 210);
		addHotSpot(Toilet.HotSpots.TOILET, NQ1Texts.s03_toilet, 385, 300);
		addHotSpot(Toilet.HotSpots.TANK, NQ1Texts.s03_tank, 415, 80);

		stageItems.add(toilet_paper());
		stageItems.add(toilet_brush());
		stageItems.add(toilet_chain());

		music = Common.INSTANCE.warren_music();

		setGraph(Toilet.INSTANCE.graph());

	}

	private static StageItem toilet_paper() {
		StageItem item = new StageItem(Toilet.HotSpots.PAPER, NQ1Texts.s03_paper,
				new NAGColor(255, 0, 0));
		item.setLayer(Toilet.INSTANCE.toilet_paper(), TOILET_PAPER_LAYER,
				TOILET_PAPER_ZINDEX, TOILET_PAPER_X, TOILET_PAPER_Y);
		item.setVisible(true);
		return item;
	}

	private static StageItem toilet_brush() {
		StageItem item = new StageItem(Toilet.HotSpots.BRUSH, NQ1Texts.s03_brush,
				new NAGColor(200, 0, 0));
		item.setLayer(Toilet.INSTANCE.toilet_brush(), TOILET_BRUSH_LAYER,
				TOILET_BRUSH_ZINDEX, TOILET_BRUSH_X, TOILET_BRUSH_Y);
		item.setVisible(true);
		return item;
	}

	private static StageItem toilet_chain() {
		StageItem item = new StageItem(Toilet.HotSpots.CHAIN, NQ1Texts.s03_chain,
				new NAGColor(150, 0, 0));
		item.setLayer(ItemFrames.INSTANCE.toilet_chain(), TOILET_CHAIN_LAYER,
				TOILET_CHAIN_ZINDEX, TOILET_CHAIN_X, TOILET_CHAIN_Y);
		item.setVisible(true);
		item.setBoxedMask(true);
		item.setBoxedZoom(3);
		return item;
	}

}