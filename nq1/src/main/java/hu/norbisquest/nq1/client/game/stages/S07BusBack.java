package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nq1.client.game.actors.BusDriver;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.Arrays;
import java.util.List;

public class S07BusBack extends NQ1Stage {
	private static final int WATCH_TIMEOUT = 50;
	private static final int SIT_X = 16;
	private static final int SIT_Y = 204;

	public S07BusBack(StageFactory factory) {
		super(factory);
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		super.onAvailable(isFirst);
		walkOnFloor();
		Norbi.get().putTo("S");
		addCommandList(sitDown());
		addCommandList(BusDriver.busStarts());
	}

	@Override
	protected void destroyStage() {

	}

	private List<Command> sitDown() {
		return Arrays.asList(
				Norbi.Cmd.turnBack(),
				Cmd.pause(WATCH_TIMEOUT),
				Norbi.walk(getGraph().getVertex("A")),
				Cmd.pause(WATCH_TIMEOUT),
				Norbi.Cmd.hide(),
				Norbi.Cmd.face(Norbi.SIT_ON_BUS_BACK),
				Norbi.Cmd.setFreePosition(SIT_X, SIT_Y,1),
				Norbi.Cmd.show()

		);
	}

}