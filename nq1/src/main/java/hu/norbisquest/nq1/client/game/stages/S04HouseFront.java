package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.data.bundle.HouseFront;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N04;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.List;

public class S04HouseFront extends NQ1Stage {

	public S04HouseFront(StageFactory factory) {
		super(factory);
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();

		if (App.getSettings().getReferer() == NQ1Ids.Corridor) {
			Norbi.get().putTo("S");
			addCommand(Norbi.Cmd.turnRight());
		} else {
			Norbi.get().putTo("B");
			addCommand(Norbi.Cmd.turnFront());

		}
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((HouseFront.HotSpots) hotSpotId) {
		case DOOR_PHONE:
			return createWalkControl("S", Look.RIGHT)
					.addExamine(N04.DORBELLS)
					.addUse(N04.I_HAVE_KEY);
		case HOUSE_DOOR:
			return createDoorControl("S", Look.LEFT, NQ1Ids.Corridor)
					.addExamine(N04.HOUSE_ENTRY)
					.addInventory(InventoryModel.Ids.SLEEVE_KEY,
							this::notThisKey);
		case NONSTOP:
			return createWalkControl("C", Look.LEFT)
					.addExamine(N04.CLOSED_AGAIN)
					.addUse(N04.GOING_IS_A_ROBBERY);

		case POSTER:
			return createWalkControl("D", Look.UP)
					.addExamine(N04.NEW_KALYBER, N04.ILL_BY_FROM_ROLI)
					.addUse(N04.IF_I_NEED_ASK_FOR);
		default:
			break;
		}
		return null;
	}

	private List<Command> notThisKey() {
		return Cmd.list(Norbi.Cmd.turnLeft(), Norbi.say(N04.NOT_THIS_KEY));
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
	protected void destroyStage() {
		// not used.
	}
}