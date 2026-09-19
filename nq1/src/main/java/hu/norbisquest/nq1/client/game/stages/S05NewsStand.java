package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.NewsStand;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.CS;
import hu.norbisquest.nq1.client.data.speech.N05;
import hu.norbisquest.nq1.client.data.speech.U;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Hobo;
import hu.norbisquest.nq1.client.game.actors.NewsAgent;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class S05NewsStand extends NQ1Stage {

    public static final int BG_DIFF = 141;

    private class NewsAgentControl extends NQControl {

		NewsAgentControl() {
			setLabel("NewsAgent");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Norbi.saySimple(N05.SAME_WOMAN_FOR_YEARS);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(),
					Norbi.say(N05.GREETINGS_NA),
					NewsAgent.get().say(U.GREETINGS),
					NewsAgent.get().openConversation());

		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			if (id == InventoryModel.Ids.CIGARETTE) {
				return Cmd.asList(
						Norbi.say(N05.WANT_SOME_CIGAR),
						NewsAgent.get().say(U.SHAME_ON_YOU),
						Norbi.say(N05.IAM_ADULT),
						NewsAgent.get().say(U.TAKE_AWAY_THEN));
			} else if (id == InventoryModel.Ids.SANDWITCH) {
				return Cmd.asList(
						Norbi.say(N05.HUNGRY_SANDWICH),
						NewsAgent.get().say(U.NO_FOOD_TNX),
						NewsAgent.get().say(U.SMELLS_BAD),
						Norbi.say(N05.NOOOO));

			} else if (id == InventoryModel.Ids.ALCOHOL) {
				return Cmd.asList(
						Norbi.say(N05.RU_THIRSTY),
						NewsAgent.get().say(U.SHAME_TOO_YOUNG),
						Norbi.say(N05.NO_ALCOHOL),
						NewsAgent.get().say(U.RIGHT_THEN));

			}

			return null;
		}
	}

	private class TrashControl extends NQControl {
		TrashControl() {
			setLabel("Trash");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.say(N05.ORDINAL_TRASH_CAN, Norbi.TALK_RIGHT));
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.Cmd.face(Norbi.GRAB_RIGHT),
					Hobo.Cmd.say(CS.GET_OFF_MY_TRASH),
					Norbi.Cmd.turnRight(),
					Norbi.say(N05.NO_NAME),
					Hobo.Cmd.say(CS.NAME_ON_THE_BOTTOM),
					Norbi.say(N05.NOT_INTERESTED),
					Norbi.Cmd.turnFront(),
					Norbi.say(N05.NO_RAG_AND_BONE)

			);

		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			return Arrays.asList(
					walkLabel(),
					Norbi.say(N05.NO_TRASH_IT));
		}

	}

	private class HoboControl extends NQControl {
		HoboControl() {
			setLabel("Hobo");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Norbi.saySimple(N05.HOBO_IN_DA_STREET);
		}

		@Override
		public List<Command> onUse(int x, int y) {

			return Cmd.asList(walkLabel(),
					Norbi.Cmd.turnRight(),
					Hobo.get().openConversation());
		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			if (id == InventoryModel.Ids.ALCOHOL) {
				return Cmd.list(walkLabel(), Norbi.Cmd.turnRight(),
						Hobo.get().giveAlcohol());
			} else if (id == InventoryModel.Ids.NEWSPAPER) {
				if (NQ1Settings.get().isNewspaperBack()) {
					return Norbi.saySimple(N05.JUST_GOT_THAT_BACK);
				}
				return Cmd.list(walkLabel(), Norbi.Cmd.turnRight(),
						Hobo.get().giveNewspaper());
			} else if (id == InventoryModel.Ids.CIGARETTE) {
				if (NQ1Settings.get().isCigaretteToHobo()) {
					return Norbi.saySimple(N05.OUT_OF_CIGARETTE);
				}
				return Cmd.list(walkLabel(), Norbi.Cmd.turnRight(),
						Hobo.get().giveCigarette());
			} else if (id == InventoryModel.Ids.SANDWITCH) {
				if (NQ1Settings.get()
						.getHoboItem() == InventoryModel.Ids.NEWSPAPER
						&& NQ1Settings.get().isArticleTold()) {
					return Cmd.list(walkLabel(), Norbi.Cmd.turnRight(),
							Hobo.get().giveSandwich());
				}
				return Cmd.list(walkLabel(),
						Norbi.sayAll(N05.REFUSE_TO_GIVE,
								N05.MAYBE_USEFUL_LATER));

			} else if (id == InventoryModel.Ids.SLEEVE) {
				if (NQ1Settings.get().isSleeveOpened()) {
					return Cmd.list(walkLabel(),
							Norbi.say(N05.NO_USE_FOR_HIM));
				}
				return Cmd.asList(walkLabel(),
						Norbi.say(N05.NEED_IT_TOO));

			} else if (id == InventoryModel.Ids.TICKET) {
				return Arrays.asList(walkLabel(), Norbi.Cmd.turnRight(),
						Norbi.say(N05.WANT_A_TICKET1),
						Hobo.Cmd.say(CS.TICKET_OF_COURSE),
						Norbi.say(N05.BUY_FOR_YOURSELF),
						Hobo.Cmd.say(CS.FUCK_OFF_YOU_JERK));
			} else if (id == InventoryModel.Ids.USED_TICKET) {
				return Arrays.asList(walkLabel(), Norbi.Cmd.turnRight(),
						Norbi.say(N05.WANT_A_TICKET2),
						Hobo.hoboSay(CS.WANNA_CHARGE_ME),
						Norbi.say(N05.DONT_WANNA_THAT),
						Hobo.Cmd.say(CS.SO_GET_IT_AWAY));
			} else if (id == InventoryModel.Ids.MONEY) {
				return Arrays.asList(walkLabel(),

						Norbi.say(N05.WONT_GIVE_MY_MONEY));
			} else if (id == InventoryModel.Ids.TOILET_BRUSH) {
				return Arrays.asList(walkLabel(), Norbi.Cmd.turnRight(),
						Norbi.say(N05.WANT_A_TEETHBRUSH),
						Hobo.Cmd.say(CS.NOW_GO_AWAY));
			}

			return null;
		}

	}

	public S05NewsStand(StageFactory factory) {
		super(factory);
	}

	protected List<ActorTarget> getActors() {
		NewsAgent.get().setControl(new NewsAgentControl());
		Hobo.get().setControl(new HoboControl());

		return Arrays.asList(NewsAgent.get(), Hobo.get());
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();

		if (NQ1Settings.get().getReferer() == NQ1Ids.HouseFront) {
			Norbi.get().putTo("S");
			addCommand(Norbi.Cmd.turnLeft());
			moveRightSide();
		} else {
			Norbi.get().putTo("BusStop");
			addCommand(Norbi.Cmd.turnRight());

		}
	}

	private class HoboHatControl extends NQControl {
		static final int MONEY_STEALED_DRINKING = 200;
		static final int MONEY_STEALED_READING = 500;

		HoboHatControl() {
			setLabel("HoboHat");
			setLook(Look.RIGHT);
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.say(N05.HOBO_HAT_MONEY_INSIDE)

			);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (Hobo.get().isDrinking()) {
				return stealDrinking(x, y);
			} else if (Hobo.get().isReading()) {
				return stealReading(x, y);
			} else if (NQ1Settings.get().getStealCount() > 2) {
				return noRiskBeating();
			}

            List<Command> list = new ArrayList<>(getOff(x, y));
			int count = NQ1Settings.get().getStealCount();
			if (count == 1) {
				NQ1Settings.get().hoboStolen();
				list.addAll(keepHimBusy(N05.KEEP_HIM_BUSY1));
				list.add(new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get().setCheapestPaperEnabled(true);
					}
				});
			} else if (count == 2) {
				list.addAll(keepHimBusy(N05.KEEP_HIM_BUSY2));
			}
			return list;
		}

		List<Command> getOff(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.Cmd.face(Norbi.SQUAT_GRAB_RIGHT),
					Hobo.Cmd.say(CS.ARM_WILL_BROKEN),
					Norbi.say(N05.WONDER_WHAT_MATERIAL),
					Hobo.Cmd.say(CS.OF_COURSE_GET_OFF));
		}

		List<Command> stealDrinking(int x, int y) {
			return Arrays.asList(
					walkLabel(),
					Norbi.Cmd.face(Norbi.SQUAT_GRAB_RIGHT),
					Cmd.pause(Hobo.GRAB_PAUSE),
					Norbi.say(N05.SUCCESS),
					new OneShotCommand() {

						@Override
						public void start() {
							Hobo.get().stopDrinking();
							NQ1Settings.get().setStealCount(1);
						}
					},
					Norbi.addMoney(MONEY_STEALED_DRINKING),
					Hobo.Cmd.say(CS.WHAT_RU_DID),
					Norbi.say(N05.I_DID_NOTHING),
					Hobo.Cmd.say(CS.STEALING_EH),
					Norbi.say(N05.GOD_NO),
					Hobo.Cmd.say(CS.DONT_WANT_TO_SEE_THAT),
					Hobo.Cmd.say(CS.KEEP_EYE_ON_YOU),
					walk(getGraph().getVertex("Standout")),
					Norbi.Cmd.turnFront(),
					Norbi.say(N05.GET_200FT));

		}

		List<Command> stealReading(int x, int y) {
			int stealCount = NQ1Settings.get().getStealCount();
			if (stealCount == 1) {
				return Arrays.asList(
						walkLabel(),
						Norbi.say(N05.A_QUICK_MOVE),
						Norbi.Cmd.face(Norbi.SQUAT_GRAB_RIGHT),
						Cmd.pause(Hobo.GRAB_PAUSE),
						Norbi.addMoney(MONEY_STEALED_READING),
						Hobo.Cmd.say(CS.WHAT_RU_DOING),
						Norbi.say(N05.LEAVING1),
						walk(getGraph().getVertex("Standout")),
						Norbi.Cmd.turnFront(),
						Norbi.say(N05.GET_500FT),
						new OneShotCommand() {

							@Override
							public void start() {
								NQ1Settings.get().hoboStolen();
							}
						});

			} else if (stealCount == 2) {
				return Arrays.asList(
						walkLabel(),
						Norbi.Cmd.face(Norbi.SQUAT_GRAB_RIGHT),
						Cmd.pause(Hobo.GRAB_PAUSE),
						Norbi.Cmd.face(Norbi.WALK_RIGHT),
						Hobo.Cmd.say(CS.LOOK_DUDE),
						Hobo.Cmd.say(CS.OK_TO_GIVE_THINGS),
						Hobo.Cmd.say(CS.YOU_WILL_BE_BEATEN),
						Norbi.say(N05.UNDERSTOOD),
						new OneShotCommand() {

							@Override
							public void start() {
								NQ1Settings.get().hoboStolen();
							}
						});
			}

			return noRiskBeating();
		}

		private List<Command> keepHimBusy(SpeechEnum s) {
			return Arrays.asList(
					walk(getGraph().getVertex("Standout")),
					Norbi.Cmd.turnFront(),
					Norbi.say(s));

		}

		private List<Command> noRiskBeating() {
			return Arrays.asList(
					walkLabel(),
					Norbi.say(N05.NO_RISK_BEATING, Norbi.TALK_FRONT));

		}

		@Override
		public List<Command> onInventory(HotSpot.Id id) {
			if (id == InventoryModel.Ids.MONEY) {
				return Arrays.asList(
						walkLabel(),
						Norbi.say(N05.LACK_OF_MONEY_TOO, Norbi.TALK_RIGHT));

			} else {
				if (id == InventoryModel.Ids.SANDWITCH) {
					return Arrays.asList(
							walkLabel(),
							Norbi.say(N05.GIVE_IT_TO_HOBO, Norbi.TALK_RIGHT));

				}
				return null;
			}
		}
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((NewsStand.HotSpots) hotSpotId) {
		case DAILIES:
			return createLookControl().addExamine(N05.DAILIES)
					.addUse(N05.ASK_WOMAN3);

		case HOBO:
			break;
		case HOBO_HAT:
			return new HoboHatControl();
		case NEWSPAPERS1:
			return createWalkControl("NewsAgent", Look.UP).addExamine(N05.NEWSPAPERS1)
					.addUse(N05.ASK_WOMAN1);
		case NEWSPAPERS2:
			return createWalkControl("S", Look.LEFT).addExamine(N05.NEWSPAPERS2)
					.addUse(N05.ASK_WOMAN2);
		case TRASH:
			return new TrashControl();
		default:
			break;
		}
		return null;
	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("S - HouseFront")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.HouseFront,
					getListener(), false));
		} else if (region.equals("D - BusStop")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.BusStop,
					getListener(), false));
		}
	}

	@Override
	protected SpeechEnum[] getAllSpeech() {
		return N05.values();
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		int key = event.getNativeKeyCode();
		if (key == KeyCodes.KEY_N) {
			NQ1Settings.get().setMoney(2000);
		} else {
			super.onKeyDown(event);
		}
	}

	@Override
	public void destroyStage() {
		NewsAgent.destroyInstance();
		Hobo.destroyInstance();
	}
}