package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.data.bundle.Corridor;
import hu.norbisquest.nq1.client.data.speech.N02;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;

public class S02Corridor extends NQ1Stage {

	public S02Corridor(StageFactory factory) {
		super(factory);
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();
		if (App.getSettings().getReferer() == NQ1Ids.Room) {
			Norbi.get().putTo("ROOM");
			addCommand(Norbi.walk(getGraph().getVertex("S")));
			addCommand(Norbi.Cmd.turnLeft());
		} else

		if (App.getSettings().getReferer() == NQ1Ids.HouseFront) {
			Norbi.get().putTo("DOOR");
			addCommand(Norbi.Cmd.turnFront());

		} else {
			Norbi.get().putTo("C");
			addCommand(Norbi.Cmd.turnRight());
		}
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((Corridor.HotSpots) hotSpotId) {
		case BATHROOM_DOOR:
			return createLookControl()
					.addExamine(N02.BATHROOM_DOOR)
					.addUse(N02.NOTHING_TO_DO_THERE);
		case DOOR_PHONE:
			return createLookControl()
					.addExamine(N02.RINGS_IS_SOMEBODY_COMES)
					.addUse(N02.NOT_RINGING_NOW);
		case FRONT_DOOR:
			return createDoorControl("DOOR", Look.UP,
					NQ1Ids.HouseFront)
							.addExamine(N02.FRONT_DOOR);
		case KEYS:
			return createWalkControl("S", Look.UP)
					.addExamine(N02.OUR_KEYS)
					.addUse(N02.NO_KEYS_NOW);
		case SHOES:
			return createLookControl().addExamine(N02.SHOES)
					.addUse(N02.GOOD_WHAT_I_WEAR);
		case SWITCH:
			return createWalkControl("C", Look.RIGHT)
					.addExamine(N02.SWITCH)
					.addUse(N02.NO_REASON_SWITCHING);
		case TOILET_DOOR:
			return createDoorControl("WC", Look.RIGHT, NQ1Ids.TOILET)
					.addExamine(N02.WC, N02.NOBODYS_THERE);
		case WARDROBE:
			return createWalkControl("C", Look.LEFT)
					.addExamine(N02.WARDROBE)
					.addUse(N02.NO_NEED_FROM_THERE);
		default:
			break;
		}
		return null;
	}

	@Override
	protected void destroyStage() {

	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("S - ROOM")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.Room,
					getListener(), false));
		}
	}

	@Override
	public void reload() {
		addCommandList(Cmd.fadeOutToScreen(NQ1Ids.Room,
				getListener(), false));
	}

}