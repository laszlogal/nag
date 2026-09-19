package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nq1.client.data.speech.B;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.BusDriver;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.Collections;
import java.util.List;

public class S07BusFront extends NQ1Stage {
	private boolean driverCameOut;
	private boolean passAsked;


	public S07BusFront(StageFactory factory) {
		super(factory);
	}


	protected List<ActorTarget> getActors() {
		return Collections.singletonList(BusDriver.get());
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();
		BusDriver.get().reset();
		addCommands(Norbi.Cmd.putTo("S"),
				Norbi.Cmd.turnLeft());
		driverCameOut = false;
		passAsked = false;

	}

	@Override
	public void execute(double timestamp) {
		super.execute(timestamp);
		if (isValid()) {

			if (!driverCameOut) {
				driverCameOut = BusDriver.get().cameOut();
			}

			if (driverCameOut && !passAsked) {
				addCommands(BusDriver.get().say(B.TICKET_OR_PASS),
						BusDriver.get().openConversation());
				passAsked = true;
			}

			if (BusDriver.get().isGoingBack()) {
				BusDriver.get().goBack();
			}
		}
    }

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("B - E")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.NewsStand,
					getListener(), false));
		}
	}

	@Override
	public void destroyStage() {
		BusDriver.destroyInstance();
	}
}