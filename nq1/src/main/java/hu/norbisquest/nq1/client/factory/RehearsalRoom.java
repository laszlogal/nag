package hu.norbisquest.nq1.client.factory;

import com.google.gwt.core.client.GWT;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.model.RehearsalRoomModel;
import hu.norbisquest.nq1.client.game.stages.S10RehearsalRoom;

class RehearsalRoom {
	public static void create(final ScreenParent parent) {
		GWT.runAsync(new StageAsyncCreator() {
			@Override
			public StageFactory createFactory() {
				return StageFactory1.create(parent, new RehearsalRoomModel());
			}

			@Override
			public void createStage(StageFactory factory) {
				parent.onScreenCreated(new S10RehearsalRoom(factory));
			}
		});
	}
}
