package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.walk.WalkPoint;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.RockKlub;
import hu.norbisquest.nq1.client.data.model.ActorModel;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N09;
import hu.norbisquest.nq1.client.data.speech.SZ;
import hu.norbisquest.nq1.client.game.stages.NQ1Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Szurdi extends ActorTarget {
	private static final int GRAB_TIME = 20;

	static class Cmd extends hu.norbisquest.nagbase.game.command.Cmd {

		static Command face(final int animId) {
			return new OneShotCommand() {

				@Override
				public void start() {
					get().changeAnimation(animId);
				}
			};
		}

	}

	private static final int GRAB = 2;
	private static final int NEWSPAPER_TALK = 3;
	private static final int NEWSPAPER_GRAB = 4;
	private static final int READ = 5;

	private static Szurdi INSTANCE = null;

	public interface SzurdiListener {
		WalkPoint getTalkPoint();

		void setSnipsVisible(boolean value);
	}

	private Szurdi() {
		super(ActorModel.getSzurdi());
	}

	@Override
	protected void prepareForStage() {
		if (NQ1Settings.get().isSzurdiReading()) {
			changeAnimation(READ);
			getActor().setTalkAnimation(READ, NEWSPAPER_TALK);
		}
	}

	@Override
	protected void addAnimations() {
		setTalkAnimation(RockKlub.INSTANCE.szurdi_sit(), RockKlub.Data.szurdi_talk);
		addAnimation(GRAB, RockKlub.INSTANCE.szurdi_grab());
		addAnimation(READ, RockKlub.INSTANCE.szurdi_read());
		addAnimation(NEWSPAPER_GRAB, RockKlub.INSTANCE.szurdi_newspaper_grab());
		addAnimation(NEWSPAPER_TALK, RockKlub.Data.szurdi_newspaper_talk, getTalkSpeed());
		changeAnimation(NORMAL);
	}

    @Override
    protected Conversation createConversation() {
        return null;
    }

    public static Szurdi get() {
		if (INSTANCE == null) {
			INSTANCE = new Szurdi();
		}
		return INSTANCE;
	}

	public List<Command> giveCigarette() {
		List<Command> list = new ArrayList<>();
		list.add(Norbi.Cmd.turnRight());
		if (NQ1Settings.get().isSzurdiHasChain()) {
			list.addAll(Arrays.asList(
					Norbi.say(N09.CIGARETTE),
					say(SZ.SURE_NORBI),
					Norbi.say(N09.GIVE_IF_LOOK_AMP),
					say(SZ.AH_THE_AMP),
					say(SZ.MUST_BE_FUSE),
					Norbi.say(N09.GIVE_NEW_ONE),
					Szurdi.Cmd.face(GRAB),
					Cmd.pause(GRAB_TIME),
					Norbi.Cmd.face(Norbi.GRAB_RIGHT),
					Cmd.pause(GRAB_TIME),
					Szurdi.Cmd.face(NORMAL),
					Norbi.Cmd.take(null, null, InventoryModel.Ids.FUSE),
					Norbi.say(N09.THANKS_MAN),
					say(SZ.CIGARETTE),
					Norbi.say(N09.GIVE_YOU_ALL),
					Norbi.Cmd.face(Norbi.GIVE_CIGARETTE),
					Cmd.pause(GRAB_TIME),
					Szurdi.Cmd.face(GRAB),
					Cmd.pause(GRAB_TIME),
					Szurdi.Cmd.face(NORMAL),
					Norbi.Cmd.face(Norbi.WALK_RIGHT),
					Norbi.Cmd.loose(InventoryModel.Ids.CIGARETTE)));
		} else {
			list.addAll(Arrays.asList(
					Norbi.say(N09.CIGARETTE_ALT),
					say(SZ.NOT_NOW),
					Norbi.say(N09.OK_BUT_OUT)));
		}
		return list;
	}

	public List<Command> giveBrush() {
		if (NQ1Settings.get().isSzurdiHasChain()) {
			if (NQ1Settings.get().isBrushToSzurdi()) {
				return Norbi.sayAll(N09.BETTER_NOT_DISTURB);
			} else {
				NQ1Settings.get().setBrushToSzurdi(true);
				return Cmd.asList(
						Norbi.Cmd.turnRight(),
						Norbi.say(N09.BRUSH),
						say(SZ.CHAIN_NEEDED_NOT_BRUSH),
						Norbi.say(N09.SORRY));
			}
		}

		return Cmd.asList(Norbi.Cmd.turnRight(),
				say(SZ.LEAVE_ME_ALONE));
	}

	public List<Command> giveFuse() {
		return Arrays.asList(
				Norbi.Cmd.turnRight(),
				Norbi.say(N09.HELP_REPLACING),
				Szurdi.get().say(SZ.DONT_BE_LAME),
				Szurdi.get().say(SZ.ITS_EASY),
				Norbi.say(N09.RIGHT));

	}

	public List<Command> giveNewspaper() {
		List<Command> list = new ArrayList<>();
		list.add(Norbi.Cmd.turnRight());
		list.add(Norbi.say(N09.SOMETHING_TO_READ));
		if (NQ1Settings.get().isArticleTold()) {
			list.addAll(Arrays.asList(
					Norbi.say(N09.TWO_PAGES_FOR_ROCKKLUB),
					say(SZ.LETS_SEE),
					Norbi.Cmd.face(Norbi.GIVE_NEWSPAPER),
					Cmd.pause(GRAB_TIME),
					Szurdi.Cmd.face(GRAB),
					Cmd.pause(GRAB_TIME),
					Norbi.Cmd.face(Norbi.WALK_RIGHT),
					Szurdi.Cmd.face(READ),
					Norbi.Cmd.loose(InventoryModel.Ids.NEWSPAPER),
					Cmd.setItemVisible(NQ1Stage.current(), RockKlub.HotSpots.SNIPS, true),
					new OneShotCommand() {

						@Override
						public void start() {
							NQ1Settings.get().setSzurdiReading(true);
							getActor().setTalkAnimId(NEWSPAPER_TALK);
							getActor().setTalkBase(READ);

						}
					}));
		} else {
			list.addAll(Arrays.asList(
					say(SZ.IM_BUSY_SEE),
					Norbi.say(N09.NOT_REALLY),
					say(SZ.LEAVE_ME_ALONE),
					Norbi.say(N09.OKOK)));
		}
		return list;
	}

	public List<Command> giveFee() {
		NQ1Settings.get().setFeePayed(true);
		NQ1Settings.get().setChainNeeded(true);
		return Arrays.asList(
				Norbi.Cmd.turnRight(),
				Norbi.say(N09.HERE_IS_THE_FEE),
				say(SZ.SUPERB),
				say(SZ.WRITE_IF_NOT_FORGOT),
				Norbi.say(N09.SO_LOOK_MY_AMP),
				say(SZ.IM_BUSY_REPAIRING_TOILET),
				say(SZ.AFTER_THAT),
				Norbi.say(N09.YOU_NOT_WORKING),
				say(SZ.NO_CHAIN),
				Norbi.say(N09.WHY_NOT_SEARCH_IT),
				say(SZ.MUST_BE_A_PUNK),
				Norbi.say(N09.ONE_STANDING_OUTSIDE),
				say(SZ.WOULD_YOU_ASK_HIM),
				Norbi.say(N09.RIGHT_BUT_REPAIR_MY_AMP_THEN),
				say(SZ.SURE),
				Cmd.resetMode()
		);

	}

	public List<Command> giveToiletChain() {
		NQ1Settings.get().setSzurdiHasChain(true);
		return Arrays.asList(
				Norbi.Cmd.turnRight(),
				Norbi.say(N09.LOOK_WHAT_I_GOT),
				say(SZ.FOUND_THE_CHAIN),
				Norbi.say(N09.SOMETHING_LIKE_THAT),
				say(SZ.PUNKS_HAVE_IS_IT),
				Norbi.say(N09.HERE_IT_IS),
				Norbi.Cmd.face(Norbi.GRAB_RIGHT),
				Cmd.pause(GRAB_TIME),
				Norbi.Cmd.loose(InventoryModel.Ids.TOILET_CHAIN),
				Szurdi.Cmd.face(Szurdi.GRAB),
				Cmd.pause(GRAB_TIME),
				Norbi.Cmd.face(Norbi.WALK_RIGHT),
				Szurdi.Cmd.face(READ),
				Norbi.say(N09.DO_LOOK_MY_AMP_NOW),
				say(SZ.PUT_CHAIN_FIRST),
				say(SZ.WAIT_IN_FITTING_ROOM),
				Norbi.say(N09.YEP));
	}

	public List<Command> giveSandwitch() {
		return Arrays.asList(
				Norbi.Cmd.turnRight(),
				Norbi.say(N09.SANDWITCH),
				say(SZ.NOT_HUNGRY));
	}

	public List<Command> talkTo() {
		if (App.getInventory().has(InventoryModel.Ids.FUSE)) {
			if (NQ1Settings.get().isSzurdiFedUp()) {
				return Norbi.sayAll(N09.NOT_DISTURB);
			} else {
				NQ1Settings.get().setSzurdiFedUp(true);
				return Cmd.asList(
						Norbi.Cmd.turnRight(),
						say(SZ.YOU_HAVE_YOU_WANT),
						Norbi.say(N09.BUT1),
						say(SZ.LEAVE_ME_NORBIKAM),
						Norbi.say(N09.OK_SORRY));
			}
		} else {
			return Cmd.asList(
					Norbi.Cmd.turnRight(),
					say(SZ.DONT_SAY_A_WORD),
					Norbi.say(N09.BUT2),
					say(SZ.NOT_INTERESTED),
					say(SZ.IF_FEE_WE_TALK));

		}
	}

	public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}