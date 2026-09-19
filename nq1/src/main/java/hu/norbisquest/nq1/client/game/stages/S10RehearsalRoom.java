package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Random;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.core.layer.MaskedLayer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nagbase.game.walk.WalkGraph;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.RehearsalRoom;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N10;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nqcommon.client.NQStage;

import java.util.List;

public class S10RehearsalRoom extends NQ1Stage {
	private StageItem glass;

	private static class Cmd extends hu.norbisquest.nagbase.game.command.Cmd {
		private static Command setMask(final ImageResource res, final boolean top) {
			return new OneShotCommand() {

				@Override
				public void start() {
					((S10RehearsalRoom) NQStage.current()).setMask(res, top);
				}

				@Override
				public boolean isBlocker() {
					return true;
				}
			};
		}
	}

	private class KnifeControl extends NQControl {
		static final int PULL_DURATION = 100;

		KnifeControl() {
			setLabel("Knife");
			setLook(Look.LEFT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N10.KINIFE);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(),
					Norbi.Cmd.turnLeft(),
					Norbi.say(N10.TRYING),
					Norbi.Cmd.face(Norbi.PULL_KNIFE),
					Norbi.Cmd.start(),
					Cmd.pause(PULL_DURATION),
					Norbi.Cmd.stop(),
					Norbi.say(N10.NO_WAY_TO_PULL),
					new OneShotCommand() {

						@Override
						public void start() {
							if (!NQ1Settings.get().isKnifePulled()) {
								NQ1Settings.get().setAskAboutKnife(true);
								NQ1Settings.get().setKnifePulled(false);
							}
						}
					});
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			if (id == InventoryModel.Ids.ALCOHOL) {
				return walkAndSay(N10.BEER_NOT_OIL);
			} else if (id == InventoryModel.Ids.SNIPS) {
				return walkAndSay(N10.WILL_BE_BROKEN);
			}
			return null;
		}

	}

	private class CoatControl extends NQControl {
		private static final int SEARCH_DURATION = 100;

		CoatControl() {
			setLabel("Coat");
			setLook(Look.RIGHT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N10.SOMEBODYS_COAT);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (NQ1Settings.get().isCoatSearched()) {
				return walkAndSay(N10.ALREADY_SEARCHED);
			}

			NQ1Settings.get().setCoatSearched(true);

			return Cmd.asList(walkLabel(),
					Norbi.say(N10.LETS_LOOK_POCKETS),
					Norbi.Cmd.setRole(Norbi.Role.SEARCH_COAT),
					Norbi.Cmd.start(),
					Cmd.pause(SEARCH_DURATION),
					Norbi.Cmd.stop(),
					Norbi.Cmd.restoreRole(),
					Norbi.say(N10.BOX_OF_CIGAR),
					Norbi.Cmd.take(null, null, InventoryModel.Ids.CIGARETTE));
		}

		@Override
		public List<Command> onInventory(HotSpot.Id ids) {
			return null;
		}
	}

	private class RubbishControl extends NQControl {
		static final int SEARCH_DURATION = 40;

		RubbishControl() {
			setLabel("Rubbish");
			setLook(Look.UP);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N10.DISGUSTING);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(N10.NO_WAY_MAN);
		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			if (id != InventoryModel.Ids.TOILET_BRUSH) {
				return null;
			}

			if (NQ1Settings.get().isRubbishSearched()) {
				return walkAndSay(N10.NO_OTHER_USABLE_IN_IT);

			}

			NQ1Settings.get().setRubbishSearched(true);
			return Cmd.asList(
					walkLabel(),
					Norbi.Cmd.turnBack(),
					Norbi.say(N10.SEARCHING),
					Norbi.Cmd.setRole(Norbi.Role.SEARCH_BRUSH),
					Norbi.Cmd.start(),
					Cmd.pause(SEARCH_DURATION),
					Norbi.Cmd.stop(),
					Norbi.Cmd.restoreRole(),
					Norbi.say(N10.SANDWITCH),
					Norbi.say(N10.DUST_A_BIT),
					Norbi.Cmd.take(null, null, InventoryModel.Ids.SANDWITCH));
		}

	}

	private class TrashControl extends NQControl {
		static final int SEARCH_DURATION = 80;

		TrashControl() {
			setLabel("Trash");
			setLook(Look.UP);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			if (NQ1Settings.get().isTrashSearched()) {
				return walkAndSay(N10.NOTHING_MORE);
			}

			return Cmd.asList(walkLabel(),
					Norbi.Cmd.turnBack(),
					Norbi.say(N10.FULL),
					Norbi.say(N10.SOMETHING_IN_THERE),
					new OneShotCommand() {

						@Override
						public void start() {
							NQ1Settings.get().setBrushNeeded(true);
						}
					});
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(N10.NOT_PUTTING_MY_HAND);
		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			if (id != InventoryModel.Ids.TOILET_BRUSH) {
				return walkAndSay(N10.NOT_TRASH_IT);
			}

			if (NQ1Settings.get().isTrashSearched()) {
				return walkAndSay(N10.TRASH_LOOKED);

			}

			NQ1Settings.get().setTrashSearched(true);
			return Cmd.asList(walkLabel(),
					Norbi.Cmd.turnBack(),
					Norbi.say(N10.CAN_BE_USED),
					Norbi.Cmd.setRole(Norbi.Role.SEARCH_BRUSH),
					Norbi.Cmd.start(),
					Cmd.pause(SEARCH_DURATION),
					Norbi.Cmd.stop(),
					Norbi.Cmd.restoreRole(),
					Norbi.say(N10.HALF_BEER),
					Norbi.Cmd.take(null, null, InventoryModel.Ids.ALCOHOL));
		}

	}

	class MarshallControl extends NQControl {

		MarshallControl() {
			setLabel("Marshall");
			setLook(Look.UP);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N10.MY_DEAR_AMPLIFIER);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(Random.nextBoolean() ? N10.OUT_OF_ORDER
					: N10.HI_MARSHALL);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			switch ((InventoryModel.Ids) id) {
			case ALCOHOL:
				return walkAndSay(N10.NO_POURING);
			case FUSE:
				return replaceFuse();
			case MONEY:
				return walkAndSay(N10.NO_AUTOMAT_TO_FEED);
			case SNIPS:
				return walkAndSay(N10.NO_DESTROYING);
			case TOILET_BRUSH:
				return walkAndSay(N10.NO_DUST_BUT_WC_BRUSH);
			case TICKET:
			case NEWSPAPER:
			case SANDWITCH:
			case SLEEVE:
			case SLEEVE_KEY:
			case CIGARETTE:
			case TOILET_CHAIN:
			case TOILET_PAPER:
			case USED_TICKET:
			default:
			}

			return null;
		}

		private List<Command> replaceFuse() {
			createPathToMarshall();
			return Cmd.list(
					walkTo("Fuse1"),
					Cmd.setItemZIndex(S10RehearsalRoom.this, RehearsalRoom.HotSpots.MARSAL, Layer.Z_FOREGROUND2),
					walkTo("Fuse2"),
					Norbi.Cmd.turnFront(),
					Norbi.say(N10.REPLACE_AT_LAST),
					Norbi.Cmd.setRole(Norbi.Role.SQUAT_FRONT),
					Norbi.say(N10.WHERE_TO_PLUG),
					Norbi.say(N10.ALLRIGHT),
					Norbi.say(N10.BRING_MY_GUITAR),
					Norbi.Cmd.setRole(Norbi.Role.WALK),
					walkTo("Fuse1"),
					Cmd.setMask(RehearsalRoom.INSTANCE.mask_top(), false),
					walkTo("D"),
					Cmd.fadeOutToScreen(NQ1Ids.PlayBass, getListener(),
							true));
		}

	}

	public S10RehearsalRoom (StageFactory factory) {
		super(factory);
	}
	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();
		setMaskEnabled(false);
		Norbi.get().putTo("S");
		glass = getStageItem(RehearsalRoom.HotSpots.GLASS);
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((RehearsalRoom.HotSpots) hotSpotId) {
		case COAT:
			return new CoatControl();
		case KNIFE:
			return new KnifeControl();
		case MARSAL:
			return new MarshallControl();
		case RUBBISH:
			return new RubbishControl();
		case SOCKET:
			return createWalkControl("S", Look.UP)
					.addExamine(N10.SOCKET)
					.addUse(N10.DONT_WANNA_SHOCK);
		case SWITCH:
			return createWalkControl("S", Look.UP)
					.addExamine(N10.LIGHT_SWITCH)
					.addUse(N10.NO_SWITCHING);
		case TRASH:
			return new TrashControl();
		case GLASS:
		default:
			break;
		}
		return null;
	}

	@Override
	protected void destroyStage() {
		// not used
	}

	private void createPathToMarshall() {
		WalkGraph g = getGraph();
		g.connect("Fuse1", "B");
		g.connect("Fuse1", "C");

	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("S - RockKlub")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.RockKlub,
					getListener(), false));
		}

		if (region.contains("D")) {
			App.debug("Glass: FOREGROUND");
			glass.setZIndex(Layer.Z_FOREGROUND1);
			setMaskEnabled(true);
		} else { // if (region.contains("C")) {
			App.debug("Glass: BACKGROUND");
			glass.setZIndex(Layer.Z_BACKGROUND);
			setMaskEnabled(false);
		}

	}

	private void setMaskEnabled(boolean value) {

		getForeground().setMaskEnable(value);
	}

	private void setMask(ImageResource res, boolean top) {
		App.debug("setMask " + (top ? "top" : "bottom") + ": " + res);
		MaskedLayer fg = getForeground();
		if (top) {
			fg.setTopMask(res);
		} else {
			fg.setBottomMask(res);

		}
		fg.setMaskEnable(res != null);
		fg.refresh();
	}
}