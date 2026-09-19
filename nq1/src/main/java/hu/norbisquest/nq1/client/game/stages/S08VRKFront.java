package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.user.client.Random;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.VRKFront;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N08;
import hu.norbisquest.nq1.client.data.speech.P;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nq1.client.game.actors.Punk;

import java.util.*;

public class S08VRKFront extends NQ1Stage {
	private StageItem door;
	private class CannalControl extends NQControl {

		CannalControl() {
			setLabel("SIGN");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N08.MOVEMENT_DOWN_THERE);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (Random.nextBoolean()) {
				return walkAndSay(N08.CANT_LIFT_IT, N08.NOTHING_TO_DO_THERE);
			}

			if (NQ1Settings.get().isCannalShouted()) {
				return walkAndSay(N08.NOBODY_THERE);
			} else {
				NQ1Settings.get().setCannalShouted(true);
				return Cmd.asList(walkLabel(),
						Norbi.Cmd.turnFront(),
						Norbi.say(N08.ANYBODY_THERE),
						Punk.get().say(P.WHOS_TALKING_TO),
						Norbi.Cmd.turnRight(),
						Norbi.say(N08.SOMEBODY_MIGHT_THERE),
						Punk.get().say(P.CLOWN_EH),
						Norbi.say(N08.NOT_IMPORTANT));
			}
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}

	}

	private class PunkControl extends NQControl {

		PunkControl() {
			setLabel("Punk");
			setLook(Look.RIGHT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Cmd.list(lookRight(),
					Norbi.sayAll(N08.RAW_PUNK, N08.LOOKS_STRONGER));
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(), Norbi.Cmd.turnRight(),
					Punk.get().openConversation());
		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			List<Command> list = new ArrayList<>();
			list.add(walkLabel());
			switch (InventoryModel.Ids.as(id)) {
			case ALCOHOL:
				list.add(Norbi.say(N08.NOT_FOR_ANYBODY2));
				break;
			case CIGARETTE:
				list.addAll(Punk.get().giveCigarette());
				break;
			case MONEY:
				list.add(Norbi.say(N08.NO_CONTRIBUTING));
				break;
			case NEWSPAPER:
				list.addAll(giveNewspaper());
				break;
			case SANDWITCH:
				list.add(Norbi.say(N08.NOT_FOR_ANYBODY1));
				break;
			case USED_TICKET:
				list.addAll(giveUsedTicket());
				break;
			case TOILET_CHAIN:
				list.addAll(giveChain());
				break;
			case FUSE:
			case SLEEVE:
			case SLEEVE_KEY:
			case SNIPS:
			case TICKET:
			case TOILET_BRUSH:
			case TOILET_PAPER:
			default:
				break;

			}
			return list;
		}

		private Collection<Command> giveUsedTicket() {
			return Arrays.asList(
					Norbi.say(N08.WANNA_TICKET),
					say(P.ITS_USED),
					Norbi.say(N08.NOT_NOTICED_SORRY));
		}

		private Collection<Command> giveNewspaper() {
			return Arrays.asList(
					Norbi.say(N08.SOMETHING_TO_READ),
					say(P.NOT_INTERESTED),
					Norbi.say(N08.CANT_READ_EH),
					say(P.OH_FUCK_OFF),
					Norbi.say(N08.ALLRIGHT));
		}

		private Collection<Command> giveChain() {
			return Arrays.asList(
					Norbi.say(N08.I_GOT_CHAIN),
					say(P.FABOULOS));
		}

		private Command say(SpeechEnum s) {
			return Punk.get().say(s);
		}

	}


	public S08VRKFront(StageFactory factory) {
		super(factory);
	}

	protected List<ActorTarget> getActors() {
		Punk.get().setControl(new PunkControl());
		return Collections.singletonList(Punk.get());
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		door = getStageItem(VRKFront.HotSpots.DOOR);
		if (App.getSettings().getReferer() == NQ1Ids.RockKlub) {
			Norbi.get().putTo("S");
			addCommand(Norbi.Cmd.turnFront());
		} else {
			addCommandList(Cmd.list(Norbi.Cmd.turnRight(),
					Norbi.Cmd.putTo("A")));
		}

	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch (((VRKFront.HotSpots) hotSpotId)) {
		case AVERTISMENTS:
			return createWalkControl("ADVERT", Look.UP)
					.addExamine(N08.BROKEN_AND_STOLEN)
					.addUse(N08.NO_WAY_I_GET_HURT);
		case CANNAL:
			return new CannalControl();
		case ELETRIC_BOX:
			return createWalkControl("SIGN", Look.LEFT)
					.addExamine(N08.FULL_OF_POSTERS, N08.TODAYS_CONCERTS)
					.addUse(N08.NO_GRAB_THEM);
		case PUNK:
			break;
		case SIGN:
			return createWalkControl("SIGN", Look.UP)
					.addExamine(N08.CLEAN_YARD_NICE_HOME, N08.OF_COURSE)
					.addUse(N08.WHATS_FOR);
		default:
			break;

		}
		return null;
	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("A - BusStop")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.BusStop,
					getListener(), false));
		} else if (region.contains(" - RockKlub")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.RockKlub, getListener(), false));
		}
		if (region.contains("RockKlub")) {
			setDoorVisible(true);
		} else {
			setDoorVisible(false);

		}
	}

	private void setDoorVisible(boolean b) {
		if (door == null) {
			return;
		}
		door.setVisible(b);
	}

	@Override
	public void destroyStage() {
		Punk.destroyInstance();
	}
}