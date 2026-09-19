package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.core.layer.MaskedLayer;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.command.Pause;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.BusFront;
import hu.norbisquest.nq1.client.data.bundle.BusStop;
import hu.norbisquest.nq1.client.data.model.ActorModel;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.B;
import hu.norbisquest.nq1.client.data.speech.N07;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.stages.NQ1Stage;
import hu.norbisquest.nqcommon.client.NQStage;

import java.util.List;

public class BusDriver extends ActorTarget {

	private static final int LEFT = 290;
	private static final int LEFT_LOOK = 316;

	private static BusDriver INSTANCE = null;
	private int left;
	private boolean goingBack;
	private BusDriverConversation cvBusDriver;
	private ScreenListener listener;
	private MaskedLayer maskedLayer;

	private static final int PAUSE_BUS_STARTS = 50;
	private enum Topics {NOPE, TICKET, JOKING}

	private class BusDriverConversation extends Conversation {
		private static final int SIT_Y = 197;
		private static final int SIT_X = 124;
		private static final int PAUSE_USE_TICKET = 10;
		private static final int STAMP_X = 430;
		private static final int STAMP_Y = 260;

		private static final int SEAT_X = 200;
		private static final int SEAT_Y = 420;

		BusDriverConversation(Actor questioner, ActorTarget target) {
			super(questioner, target);
		}


		@Override
        protected Enum toId(String s) {
            return Topics.valueOf(s);
        }

        @Override
		public void createTopics() {
			Norbi.get().setTalkBase(Norbi.TALK_LEFT);
			addTopic(Topics.NOPE, N07.NOPE, this::nope, true);
			addTopic(Topics.TICKET, N07.TICKET, this::ticket, true);
			addTopic(Topics.JOKING, N07.JOKING, this::joking, false);
		}

		@Override
		protected void activteExternalTopics() {
			setTopicActive(Topics.NOPE, App.getInventory().has(InventoryModel.Ids.TICKET));
		}

		List<Command> travel() {
			return Cmd.list(
					Norbi.walk(STAMP_X, STAMP_Y),
					Norbi.Cmd.face(Norbi.USE_TICKET_ON_BUS),
					Cmd.playSfx(BusFront.INSTANCE.use_ticket_sfx(), false),
					new Pause(PAUSE_USE_TICKET),
					new Command() {
						boolean finished;

						@Override
						public void start() {
							goingBack = true;
							finished = false;
						}

						@Override
						public boolean isBlocker() {
							return false;
						}

						@Override
						public void process() {
							finished = goBack();
						}

						@Override
						public boolean hasFinished() {
							return finished;
						}

						@Override
						public void setCondition(boolean condition) {

						}

						@Override
						public boolean getCondition() {
							return true;
						}
					},
					Norbi.walk(SEAT_X, SEAT_Y),
					Norbi.Cmd.loose(InventoryModel.Ids.TICKET),
					Norbi.Cmd.take(null, null, InventoryModel.Ids.USED_TICKET),
					new OneShotCommand() {

						@Override
						public void start() {
							Norbi.get().setFreePosition(SIT_X, SIT_Y);
							Norbi.get().setScale(1);

						}

					},
					cmdDisableTopic(Topics.TICKET),
					Norbi.Cmd.face(Norbi.SIT_ON_BUS));
		}

		private List<Command> nope() {
			List<Command> list = Cmd.list(Norbi.say(N07.NOPE), say(B.GET_OFF_THEN));
			if (NQ1Settings.get().hasTicket()) {
				list.addAll(Cmd.list(cmdEnableTopic(Topics.JOKING),
						cmdDisableTopic(Topics.NOPE, Topics.TICKET)));
			} else {
				list.addAll(Cmd.list(Cmd.list(
						Norbi.say(N07.TRIED),
						cmdDisableTopic(Topics.TICKET),
						Cmd.fadeOutToScreen(NQ1Ids.BusStop, NQ1Stage.current().getListener(), true))));
			}
			return list;
		}

		private List<Command> ticket() {
			return Cmd.list(
					Norbi.say(N07.TICKET),
					say(B.GO_ON_IM_BUSY),
					Norbi.say(N07.DONT_WORRY),
					travel(),
					busStarts());
		}

		private List<Command> joking() {
			return Cmd.list(
					Norbi.say(N07.JOKING),
					say(B.DONT_FOOL_ON_ME),
					say(B.ACCEPT_OR_GET_OFF),
					Norbi.say(N07.SLOW_DOWN_MAN),
					cmdDisableTopic(Topics.JOKING),
					travel(),
					busStarts());
		}
	}

	private BusDriver() {
		super(ActorModel.getBusDriver());
		left = LEFT;
	}

	public void reset() {
		left = LEFT;

	}

	public boolean cameOut() {
		boolean result = left == LEFT_LOOK;
		if (!result) {
			left++;
			getActor().setX(left);
		}
		return result;
	}

	public boolean goBack() {
		boolean result = left == LEFT;
		if (!result) {
			left--;
			getActor().setX(left);
		}
		if (result) {
			goingBack = false;
		}
		return result;
	}

	public ScreenListener getListener() {
		return listener;
	}

	public void setListener(ScreenListener listener) {
		this.listener = listener;
	}

	public boolean isGoingBack() {
		return goingBack;
	}

	public static List<Command> busStarts() {
		return Cmd.list(Cmd.pause(PAUSE_BUS_STARTS),
				Cmd.fadeOutSlow(),
				Cmd.pauseAmbient(),
				Cmd.playSfx(BusStop.INSTANCE.bus_leave_sfx()),
				Cmd.fadeOutToScreen(NQ1Ids.VRKFront, NQStage.current().getListener(),
						false));
	}

	@Override
	protected void addAnimations() {
		setTalkAnimation(BusFront.INSTANCE.bus_driver(), BusFront.Data.bus_driver_talk);
		changeAnimation(NORMAL);
		goingBack = false;
		stop();
	}

	@Override
	protected Conversation createConversation() {
		return new BusDriverConversation(Norbi.get(), this);
	}

	public static BusDriver get() {
		if (INSTANCE == null) {
			INSTANCE = new BusDriver();
		}
		return INSTANCE;
	}

	@Override
	public boolean isValid() {
		return INSTANCE != null && super.isValid() && maskedLayer.isValid();
	}

	@Override
	protected Layer createLayer() {
		maskedLayer = new MaskedLayer(Layer.Z_BACKGROUND2);
		maskedLayer.setBottomMask(BusFront.INSTANCE.bus_driver_mask());
		maskedLayer.load();
		return maskedLayer;
	}

	@Override
	protected void prepareForStage() {
		//
	}

	@Override
	public void destroyPerson() {
		maskedLayer.destroy();
		maskedLayer = null;
		cvBusDriver.destroy();
		cvBusDriver = null;
	}

	public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}