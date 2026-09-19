package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nq1.client.data.bundle.BusBack;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class BusBackModel extends StageModel {
	public BusBackModel() {
		id = NQ1Ids.BusBack;
		backround = BusBack.INSTANCE.background();
		setGraph(BusBack.INSTANCE.graph());

	}

}