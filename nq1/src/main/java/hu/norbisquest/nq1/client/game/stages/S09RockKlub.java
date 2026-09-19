package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.Random;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.command.Pause;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.TalkControl;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.RockKlub;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N09;
import hu.norbisquest.nq1.client.data.speech.SZ;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nq1.client.game.actors.Szurdi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class S09RockKlub extends NQ1Stage {
	private class SzurdiControl extends NQControl implements TalkControl {

		SzurdiControl() {
			setLabel("Talk");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N09.GOD_OF_ROCKKLUB);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (Random.nextBoolean()) {
				return walkAndDo(Szurdi.get().talkTo());
			}
			return walkAndSay(N09.NOT_GOOD_IDEA_BOTHER_HIM);

		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			switch (InventoryModel.Ids.as(id)) {
			case CIGARETTE:
				return walkAndDo(Szurdi.get().giveCigarette());

			case FUSE:
				return walkAndDo(Szurdi.get().giveFuse());

			case MONEY:
				if (NQ1Settings.get().getMoney() >= NQ1Settings.FEE_PRICE) {
					return NQ1Settings.get().isFeePayed() ? Norbi.sayAll(N09.NO_MORE_FOR_HIM)
							: walkAndDo(Szurdi.get().giveFee());
				} else {
					return Norbi.sayAll(N09.NOT_ENOUGH_FOR_FEE);
				}

			case NEWSPAPER:
				return walkAndDo(Szurdi.get().giveNewspaper());

			case SANDWITCH:
				return walkAndDo(Szurdi.get().giveSandwitch());

			case SNIPS:
				return walkAndSay(N09.NOT_SHOW_IT);

			case TOILET_BRUSH:
				return walkAndDo(Szurdi.get().giveBrush());

			case TOILET_CHAIN:
				return walkAndDo(Szurdi.get().giveToiletChain());

			case ALCOHOL:
				return walkAndSay(N09.MAYBE_NEEDED);
			default:

			}
			return null;
		}

		@Override
		public List<Command> onTalk(int x, int y) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private class SnipsControl extends NQControl {

		SnipsControl() {
			setLabel("Talk");
		}

		private static final int GRAB_PAUSE = 10;

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N09.SNIPS);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.Cmd.face(Norbi.SQUAT_GRAB_RIGHT),
					Norbi.Cmd.take(S09RockKlub.this, RockKlub.HotSpots.SNIPS, InventoryModel.Ids.SNIPS),
					Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
					new Pause(GRAB_PAUSE),
					Norbi.Cmd.turnRight());
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public S09RockKlub(StageFactory factory) {
		super(factory);
	}

	protected List<ActorTarget> getActors() {
		Szurdi.get().setControl(new SzurdiControl());
		return Collections.singletonList(Szurdi.get());
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();
		if (NQ1Settings.get().getReferer() == NQ1Ids.VRKFront) {
			addCommandList(
					Cmd.list(Norbi.Cmd.putTo("Front"),
							Norbi.walk(getGraph().getVertex("A")),
							Norbi.Cmd.turnRight()));
		} else {
			addCommandList(
					Cmd.list(Norbi.Cmd.putTo("RehearsalRoom"),
							Norbi.walk(getGraph().getVertex("B")),
							Norbi.Cmd.turnLeft()));
		}
		if (isFirst && !App.isDebug()) {
			greets();
		}
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((RockKlub.HotSpots) hotSpotId) {
		case CLUBCHAIR:
			break;
		case PIPE:
			break;
		case SNIPS:
			return new SnipsControl();
		default:
			break;

		}
		return null;
	}

	private void greets() {
		Szurdi sz = Szurdi.get();
		addCommandList(Arrays.asList(
				Norbi.say(N09.HI_ZSOLTI),
				sz.say(SZ.HO),
				Norbi.say(N09.WHATS_UP),
				sz.say(SZ.DONT_ASK),
				sz.say(SZ.WC_WENT_WRONG),
				Norbi.say(N09.IS_IT_OK),
				sz.say(SZ.NOT_AT_ALL),
				Norbi.say(N09.NO_TOILET_THEN),
				sz.say(SZ.RIGHT),
				Norbi.say(N09.AMP_WRONG),
				sz.say(SZ.WHATS_WRONG),
				Norbi.say(N09.NEED_FUSE),
				sz.say(SZ.FEE),
				Norbi.say(N09.SUPPOSED_TO),
				sz.say(SZ.OF_COURSE),
				Norbi.say(N09.GIVE_IT_TODAY),
				sz.say(SZ.SHOULD),
				Norbi.say(N09.HAVE_FUSE),
				sz.say(SZ.LATER),
				Norbi.say(N09.OK1),
				new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get().setChainNeeded(true);

					}
				}));
	}

	@Override
	public void onRegionEnter(String region) {
		if (region.equals("A - Front")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.VRKFront,
					getListener(), false));
		} else if (region.contains("- RehearsalRoom")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.RehearsalRoom,
					getListener(), false));

		}
	}

	@Override
	public void destroyStage() {
		Szurdi.destroyInstance();
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		switch (event.getNativeKeyCode()) {
			case KeyCodes.KEY_S:
				addCommand(Cmd.setItemVisible(NQ1Stage.current(), RockKlub.HotSpots.SNIPS, true));
				break;
			case KeyCodes.KEY_A:
				addCommand(Cmd.setItemVisible(NQ1Stage.current(), RockKlub.HotSpots.SNIPS, false));
				break;

		}
		super.onKeyDown(event);
	}
}