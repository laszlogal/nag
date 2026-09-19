package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.NewsStand;
import hu.norbisquest.nq1.client.data.model.ActorModel;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.CS;
import hu.norbisquest.nq1.client.data.speech.N05;

import java.util.Arrays;
import java.util.List;

public class Hobo extends ActorTarget {
	private static Hobo INSTANCE = null;
	private static final int GRAB = 2;
	private static final int DRINK_BASE = 3;
	private static final int TALK_ALCOHOL = 4;
	private static final int DRINK = 5;
	private static final int READ = 6;
	private static final int TALK_NEWSPAPER = 7;
	private static final int GIVE_NEWSPAPER_BACK = 8;
	private static final int TALK_SPEED = 8;
	private static final int DRINK_SPEED = 8;
	private static final int GRAB_SPEED = 800;
	public static final int GRAB_PAUSE = 20;


	public static class Cmd extends hu.norbisquest.nagbase.game.command.Cmd {
		static Command face(final int id) {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hobo.get().changeAnimation(id);
				}
			};
		}
		public static Command say(final SpeechEnum s) {
			return hoboSay(s);
		}

	}

	private class Drink {
		private static final int WAIT = 100;
		private static final int DRINKING = 100;
		private int wait;
		private int drink;
		private boolean enabled;
		private boolean standby;

		Drink() {
			enabled = false;
			wait = 0;
			drink = 0;
		}

		void process() {
			if (!enabled || getActor().isTalking()) {
				return;
			}

			if (standby) {
				wait++;
				if (wait == WAIT) {
					standby = false;
					drink = 0;
					changeAnimation(DRINK);
					getActor().setLoop(true);
					start();
					App.debug("start drinking");
				}
			} else {
				drink++;
				if (!getActor().isRunning()) {
					start();
				}
				if (drink == DRINKING) {
					standby = true;
					wait = 0;
					changeAnimation(DRINK_BASE);
					stop();
					App.debug("stop drinking");
				}
				getActor().update();
			}

		}

		void setEnabled(boolean enabled) {
			standby = true;
			wait = 0;
			drink = 0;
			this.enabled = enabled;
		}

		boolean isDrinking() {
			return !standby;
		}
	}

	private HotSpot hotSpot;
	private Drink drinkAnim;

	private Hobo() {
		super(ActorModel.getHobo());
	}

	@Override
	protected void prepareForStage() {
		if (hasAlcohol()) {
			getActor().setTalkAnimation(DRINK_BASE, DRINK);
			changeAnimation(DRINK_BASE);
			if (NQ1Settings.get().getStealCount() == 0) {
				startDrinking();
			}
		} else if (hasNewspaper()) {
			getActor().setTalkAnimation(READ, TALK_NEWSPAPER);
			changeAnimation(READ);
		} else {
			getActor().setTalkAnimation(NORMAL, TALK);
			changeAnimation(NORMAL);

		}

	}

	public List<Command> giveSandwich() {
		return Arrays.asList(Norbi.say(N05.WANT_ONE_SANDWICH),
				hoboSay(CS.FOOD_GREAT_GUY),
				Norbi.say(N05.FOR_GIVING_MY_PAPER_BACK),
				Cmd.say(CS.DEAL_MAN),
				Hobo.Cmd.face(GIVE_NEWSPAPER_BACK),
				Cmd.pause(GRAB_PAUSE),
				Hobo.Cmd.face(GRAB),
				Norbi.Cmd.face(Norbi.GIVE_SANDWICH),
				Cmd.pause(GRAB_PAUSE),
				Norbi.Cmd.face(Norbi.WALK_RIGHT),
				Hobo.Cmd.face(NORMAL),
				new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get().setHoboItem(null);
						NQ1Settings.get().setNewspaperBack(true);
						Hobo.get().getActor().setTalkAnimation(NORMAL, TALK);
					}
				},
				Norbi.Cmd.take(null, null, InventoryModel.Ids.NEWSPAPER),
				Norbi.Cmd.loose(InventoryModel.Ids.SANDWITCH)

		);
	}

	public List<Command> giveCigarette() {
		if (hasAlcohol()) {
			return Arrays.asList(
					Norbi.say(N05.WANT_ONE_CIGARETTE1),
					hoboSay(CS.AFTER_BEER),
					Norbi.say(N05.AFTER_BEER_OK));
		}
		if (hasNewspaper()) {
			return Arrays.asList(
					Norbi.say(N05.WANT_ONE_CIGARETTE2),
					hoboSay(CS.READING_MAYBE_LATER),
					Norbi.say(N05.LATER_OK));
		}

		return Arrays.asList(Norbi.say(N05.WANT_ONE_CIGARETTE3),
				Norbi.Cmd.face(Norbi.GIVE_CIGARETTE),
				Cmd.pause(GRAB_PAUSE),
				Hobo.Cmd.face(GRAB),
				Cmd.pause(GRAB_PAUSE),
				Norbi.Cmd.face(Norbi.WALK_RIGHT),
				new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get().setCigaretteToHobo(true);
					}
				},
				Cmd.say(CS.THANKS_U_GOT_FIRE_TOO),
				Norbi.say(N05.NO_FIRE, Norbi.TALK_RIGHT),
				Cmd.say(CS.NP_ASK_SOMEONE)

		);
	}

	public List<Command> giveAlcohol() {

		return Arrays.asList(Norbi.say(N05.WANT_SOME_DRINK),
				Cmd.say(CS.ALWAYS),
				Norbi.Cmd.face(Norbi.GIVE_ALCOHOL),
				Cmd.pause(GRAB_PAUSE), Hobo.Cmd.face(GRAB),
				Cmd.pause(GRAB_PAUSE), new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get()
								.setHoboItem(InventoryModel.Ids.ALCOHOL);
						startDrinking();
					}
				},
				Norbi.Cmd.loose(InventoryModel.Ids.ALCOHOL),
				Norbi.Cmd.face(Norbi.WALK_RIGHT),
				Hobo.Cmd.face(DRINK_BASE),
				Hobo.Cmd.say(CS.THANKS_BUDDY),
				Hobo.Cmd.face(DRINK_BASE),
				Norbi.say(N05.UR_WELCOME));
	}

	public List<Command> giveNewspaper() {

		return Arrays.asList(Norbi.say(N05.WANT_SOMETHING_TO_READ),
				hoboSay(CS.SURE_I_WAS_BORED),
				Norbi.Cmd.face(Norbi.GIVE_NEWSPAPER),
				Cmd.pause(GRAB_PAUSE),
				Norbi.Cmd.face(Norbi.WALK_RIGHT),
				Hobo.Cmd.face(GRAB),
				Hobo.Cmd.face(READ),
				new OneShotCommand() {
					@Override
					public void start() {
						NQ1Settings.get().setStealCount(1);
						NQ1Settings.get()
								.setHoboItem(InventoryModel.Ids.NEWSPAPER);
						Hobo.get().getActor().setTalkAnimation(READ, TALK_NEWSPAPER);
					}
				},
				Norbi.Cmd.loose(InventoryModel.Ids.NEWSPAPER),
				Cmd.pause(GRAB_PAUSE),
				Hobo.Cmd.say(CS.THANKS_DUDE),
				Norbi.say(N05.HAPPY_READING)

		);
	}

	@Override
	public boolean isHit(int x, int y) {
		return hotSpot.isHit(x, y);
	}

	public static Command hoboSay(SpeechEnum s) {
		return get().say(s);
	}


	public void setHotSpot(HotSpot hotSpot) {
		this.hotSpot = hotSpot;
	}

	@Override
	public boolean tick(double timestamp) {
		boolean result = super.tick(timestamp);
		if (drinkAnim != null) {
			drinkAnim.process();
		}
		return result;
	}

	private void startDrinking() {
		getActor().setTalkAnimation(DRINK_BASE, TALK_ALCOHOL);
		drinkAnim = new Drink();
		drinkAnim.setEnabled(true);

	}

	public boolean isDrinking() {
		return drinkAnim != null && drinkAnim.isDrinking();
	}

	public boolean isReading() {
		int id = getActor().getCurrentAnimationId();
		return id == READ;
	}

	private static boolean hasAlcohol() {
        return NQ1Settings.get() != null && NQ1Settings.get().getHoboItem() == InventoryModel.Ids.ALCOHOL;
    }

	private static boolean hasNewspaper() {
        return NQ1Settings.get() != null && NQ1Settings.get().getHoboItem() == InventoryModel.Ids.NEWSPAPER;

    }

	public void stopDrinking() {
		drinkAnim = null;
		face(DRINK_BASE);
	}

	@Override
	protected void addAnimations() {
		setTalkAnimation(NewsStand.INSTANCE.hobo_normal(), NewsStand.Data.hobo_talk);
		addAnimation(GRAB, NewsStand.Data.hobo_grab, GRAB_SPEED);
		addAnimation(DRINK, NewsStand.Data.hobo_drink, DRINK_SPEED);
		addAnimation(DRINK_BASE, NewsStand.INSTANCE.hobo_with_alcohol());
		addAnimation(TALK_ALCOHOL, NewsStand.Data.hobo_talk_alcohol,
				TALK_SPEED);
		addAnimation(READ, NewsStand.INSTANCE.hobo_read_left());
		addAnimation(TALK_NEWSPAPER, NewsStand.Data.hobo_talk_newspaper,
				TALK_SPEED);
		addAnimation(GIVE_NEWSPAPER_BACK,
				NewsStand.INSTANCE.hobo_give_newspaper_back());


		changeAnimation(NORMAL);
		stop();
	}

	public static Hobo get() {
		if (INSTANCE == null) {
			INSTANCE = new Hobo();
		}
		return INSTANCE;
	}

	@Override
	protected Conversation createConversation() {
		return new HoboConversation(Norbi.get(), this);
	}

	public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}
