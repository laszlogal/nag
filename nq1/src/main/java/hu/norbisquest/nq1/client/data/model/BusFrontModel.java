package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nq1.client.data.bundle.BusFront;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class BusFrontModel extends StageModel {
	public BusFrontModel() {
		id = NQ1Ids.BusFront;
		backround = BusFront.INSTANCE.background();
		maskTop = BusFront.INSTANCE.mask_top();
		ambientNoise = Common.INSTANCE.suburb_noise();

		autoPlayMusic = false;
		heroAudio = BusFront.Data.norbi;
		heroLines = BusFront.INSTANCE.nLines();
		setGraph(BusFront.INSTANCE.graph());

	}

}