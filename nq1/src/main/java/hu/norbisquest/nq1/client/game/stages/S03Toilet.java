package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Toilet;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N03;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.List;

public class S03Toilet extends NQ1Stage {

	private class BrushControl extends NQControl {
		BrushControl() {
			setLabel("BRUSH");
			setLook(Look.RIGHT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return lookAndSay(N03.TOILET_BRUSH);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (NQ1Settings.get().isBrushNeeded()) {
				return Cmd.asList(walkLabel(),
						Norbi.Cmd.turnRight(),
						Norbi.say(N03.MAYBE_USABLE),
						Norbi.Cmd.face(Norbi.GRAB_DOWN),
						Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
						Cmd.pause(Norbi.GRAB_TIMEOUT),
						Norbi.Cmd.take(S03Toilet.this, Toilet.HotSpots.BRUSH, InventoryModel.Ids.TOILET_BRUSH),
						Norbi.Cmd.turnRight());
			}
			return lookAndSay(N03.NOT_CARRYING_THIS);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private class PaperControl extends NQControl {

		PaperControl() {
			setLabel("A");
			setLook(Look.UP);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N03.TIME_TO_REFILL);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.asList(walkLabel(),
					Norbi.Cmd.turnBack(),
					Norbi.say(N03.COME_HANDY),
					Norbi.Cmd.face(Norbi.GRAB_BACK),
					Cmd.pause(Norbi.GRAB_TIMEOUT),
					Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
					Norbi.Cmd.take(S03Toilet.this, Toilet.HotSpots.PAPER,
							InventoryModel.Ids.TOILET_PAPER),
					Norbi.Cmd.turnBack());
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private class PaperHolderControl extends NQControl {

		PaperHolderControl() {
			setLabel("A");
			setLook(Look.UP);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(App.getInventory().has(InventoryModel.Ids.TOILET_PAPER)
					? N03.DONT_FORGET_TO_REPLACE : N03.RUN_OUT_OF_PAPER);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(App.getInventory().has(InventoryModel.Ids.TOILET_PAPER)
					? N03.ALREADY_GOT_IT : N03.NO_REMOVE_IT);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			if (id == InventoryModel.Ids.TOILET_PAPER) {
				return Cmd.asList(walkLabel(),
						Norbi.Cmd.turnBack(),
						Norbi.say(N03.PUT_IT_BACK),
						Norbi.Cmd.face(Norbi.GRAB_BACK),
						Cmd.pause(Norbi.GRAB_TIMEOUT),
						Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
						Norbi.Cmd.loose(InventoryModel.Ids.TOILET_PAPER),
						Cmd.setItemVisible(S03Toilet.this, Toilet.HotSpots.PAPER, true),
						Norbi.Cmd.turnBack());
			}
			return null;
		}

	}

	private class ChainControl extends NQControl {
		private static final int CUT_DURATION = 100;

		ChainControl() {
			setLabel("C");
			setLook(Look.RIGHT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(NQ1Settings.get().isChainNeeded()
					? N03.MAYBE_GOOD_FOR_SZURDI : N03.TOILET_CHAIN);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(NQ1Settings.get().isChainNeeded()
					? N03.NOT_BY_HAND : N03.WASTE_OF_WATER);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			if (id != InventoryModel.Ids.SNIPS) {
				return null;
			}

			return Cmd.asList(Norbi.walk(getGraph().getVertex("CUT")),
					Norbi.Cmd.turnRight(),
					Norbi.say(N03.WONDER_IF_WORKS),
					enableMaskCmd(true),
					Cmd.playSfx(Toilet.INSTANCE.cutting_chain(), false),
					Norbi.Cmd.face(Norbi.CUT_CHAIN),
					Norbi.Cmd.start(),
					Cmd.pause(CUT_DURATION),
					Norbi.Cmd.stop(),
					Norbi.Cmd.take(S03Toilet.this, Toilet.HotSpots.CHAIN, InventoryModel.Ids.TOILET_CHAIN),
					Norbi.say(N03.SUCCEEDED),
					enableMaskCmd(true),
					Cmd.resetMode());
		}

	}

	public S03Toilet(StageFactory factory) {
		super(factory);
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		Norbi.get().putTo("S");
		walkOnFloor();
		addCommand(Norbi.Cmd.turnRight());
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((Toilet.HotSpots) hotSpotId) {
		case BRUSH:
			return new BrushControl();
		case CHAIN:
			return new ChainControl();
		case DOOR:
			return createDoorControl("B", Look.LEFT, NQ1Ids.Corridor)
					.addExamine(N03.THE_WAY_OUT);
		case LAMP:
			return createLookControl(Look.LEFT)
					.addExamine(N03.LAMP)
					.addUse(N03.WORKING_PROPERLY);
		case PAPER:
			return new PaperControl();
		case PAPER_HOLDER:
			return new PaperHolderControl();
		case SWITCH:
			return createLookControl(Look.UP).addExamine(N03.SWITCH)
					.addUse(N03.DONT_LIKE_DARK);
		case TANK:
			return createWalkControl("S", Look.RIGHT).addExamine(N03.TANK)
					.addUse(N03.HONESTLY_WHATS_FOR);
		case TOILET:
			return createWalkControl("S", Look.RIGHT)
					.addExamine(N03.TOILET)
					.addUse(N03.NO_NEED_TO_PISS)
					.addInventory(InventoryModel.Ids.TOILET_BRUSH,
							this::noCleaning);

		default:
			break;

		}
		return null;
	}

	@Override
	protected void destroyStage() {
		// not used.
	}

	private List<Command> noCleaning() {
		return Cmd.asList(Norbi.Cmd.turnRight(),
				Norbi.say(N03.NO_CLEANING));
	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("S - ROOM")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.Room,
					getListener(), false));
		}
	}
}