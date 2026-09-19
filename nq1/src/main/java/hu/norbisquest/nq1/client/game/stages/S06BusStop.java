package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.core.IAudioChannel;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.core.layer.MaskedLayer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.BusStop;
import hu.norbisquest.nq1.client.data.speech.N06;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.Arrays;
import java.util.List;

public class S06BusStop extends NQ1Stage {
	private static final int FRONT_DOOR_X = 490;
	private static final int FRONT_DOOR_Y = 260;

	private static final int BACK_DOOR_X = 145;

	private static final int BACK_DOOR_Y = 260;
	private static final double BUS_SCHEDULE = 1500;
	private static final int BUS_WAIT_TIME = 200;
	private BusCommand busCommand;
	private int waitCounter;
	private int scheduleCounter;

	private abstract class BusCommand extends NAGObject implements Command {
		MaskedLayer busLayer;
		NAGImage bus;
		private static final int tick = 2;
		static final int STATE_ARRIVE = 0;
		static final int STATE_WAITING = 1;
		static final int STATE_LEAVE = 2;
		static final int STATE_GONE = 3;
		int speed;
		int busX;
		int busY;
		int state;
		int animCounter;
		IAudioChannel sfx;
		private int busStopX;

		BusCommand(ImageResource resBus, ImageResource resMask,
                   int busStopX) {
			bus = new NAGImage(resBus);
			this.setBusStopX(busStopX);

			busLayer = new MaskedLayer(Layer.Z_BACKGROUND);
			busLayer.setBottomMask(resMask);
			// To work in Firefox
			busLayer.setSourceCheck(true);

			busLayer.load();
			bus.load();
			setLayer("bus", busLayer);

			sfx = AudioManager.createAudioChannel();
			sfx.setAutoPlay(true);
			// sfx.setLoop(true);

		}


		@Override
		public void process() {
			if (!isValid()) {
				return;
			}
			animCounter++;
			if (!(busLayer.isValid() && bus.isValid() && animCounter % tick == 0)) {
				App.debug("nemvalid!!!!");
				return;
			}

			if (state != STATE_WAITING) {
				draw();
				busX += speed;
				if (state == STATE_ARRIVE && isBusArrived()) {
					state = STATE_WAITING;
					onWaiting();
				}

				if (state == STATE_LEAVE) {
					onLeaving();
				}

			} else {
				onWaiting();
			}

		}

		void draw() {
			busLayer.clear();
			busLayer.draw(bus, busX, busY);
		}

		protected abstract boolean isBusArrived();

		protected abstract void stop();

		protected abstract void onWaiting();

		protected abstract void onLeaving();

		@Override
		public void setCondition(boolean condition) {
		}

		@Override
		public boolean getCondition() {
			return true;
		}

		boolean isWaiting() {
			return state == STATE_WAITING;
		}

		void setWaiting() {
			state = STATE_WAITING;
		}

		@Override
		public boolean isBlocker() {
			return false;
		}

		int getBusStopX() {
			return busStopX;
		}

		void setBusStopX(int busStopX) {
			this.busStopX = busStopX;
		}

		boolean isBusGone() {
			return state == STATE_GONE;
		}

		boolean isReturnWay() {
			return speed < 0;
		}

		@Override
		public void doDestroy() {
			bus.destroy();
			sfx.destroy();
			destroyDoors();
			bus = null;
			sfx = null;
		}

		protected abstract void destroyDoors();
	}

	private class BusToRockKlubCommand extends BusCommand {
		private static final int BUS_START_X = -800;
		private static final int BUS_START_Y = -20;
		private static final int BUS_STOP_X = -320;
		private static final int BUS_STOP_Y = -20;
		private static final int BUS_GONE_X = 650;
		private StageItem firstDoor;
		private StageItem backDoor;

		BusToRockKlubCommand() {
			super(BusStop.INSTANCE.bus_going(), BusStop.INSTANCE
					.bus_mask(), BUS_STOP_X);

			firstDoor = getStageItem(BusStop.HotSpots.FRONT_DOOR);
			backDoor = getStageItem(BusStop.HotSpots.BACK_DOOR);
			speed = 10;
			busX = BUS_STOP_X;
			busY = BUS_STOP_Y;

		}

		private void openDoors(boolean open) {
			boolean backDoorToo = NQ1Settings.get().hasUsedTicket();
			if (open) {
				firstDoor.setVisible(true);
				if (backDoorToo) {
					backDoor.setVisible(true);
				}

			} else {
				firstDoor.setVisible(false);
				if (backDoorToo) {
					backDoor.setVisible(false);
				}

			}
		}

		@Override
		public void start() {

			if (state == STATE_WAITING) {
				state = STATE_LEAVE;
				busX = BUS_STOP_X;
				busY = BUS_STOP_Y;
				sfx.load(BusStop.INSTANCE.bus_leave_sfx());

			} else {
				state = STATE_ARRIVE;
				busX = BUS_START_X;
				busY = BUS_START_Y;
				sfx.load(BusStop.INSTANCE.bus_arrive_sfx());

			}
			animCounter = 0;
			openDoors(false);
		}

		@Override
		public boolean hasFinished() {
			return state == STATE_WAITING || state == STATE_GONE;
		}

		@Override
		protected void onWaiting() {
			openDoors(true);
		}

		@Override
		protected void onLeaving() {
			openDoors(false);
			if (busX >= BUS_GONE_X) {
				state = STATE_GONE;
			}
		}

		@Override
		protected void stop() {
			firstDoor.setVisible(false);
			backDoor.setVisible(false);

		}

		@Override
		protected boolean isBusArrived() {
			return (busX >= getBusStopX());
		}

		@Override
		public void destroyDoors() {
			firstDoor.destroy();
			backDoor.destroy();
			firstDoor = null;
			backDoor = null;
		}
	}

	private class BusFromRockKlubCommand extends BusCommand {
		private static final int BUS_START_X = 650;
		private static final int BUS_START_Y = -40;
		private static final int BUS_STOP_X = 152;
		private static final int BUS_GONE_X = -800;
		private static final int WAIT_TIME = 100;
		private int waitCounter;

		BusFromRockKlubCommand() {
			super(BusStop.INSTANCE.bus_coming_back(), BusStop.INSTANCE
					.bus_mask(), BUS_STOP_X);
			busLayer.setZIndex(Layer.Z_FOREGROUND2);
			speed = -10;
		}

		@Override
		public void start() {
			state = STATE_ARRIVE;
			busX = BUS_START_X;
			busY = BUS_START_Y;
			waitCounter = 0;
			sfx.load(BusStop.INSTANCE.bus_arrive_sfx());
		}

		@Override
		public boolean hasFinished() {
			return state == STATE_GONE;
		}

		@Override
		protected void stop() {
		}

		@Override
		protected void onWaiting() {
			waitCounter++;
			if (waitCounter == WAIT_TIME) {
				state = STATE_LEAVE;
				sfx.load(BusStop.INSTANCE.bus_leave_sfx());
			}
		}

		@Override
		protected void onLeaving() {
			if (busX <= BUS_GONE_X) {
				state = STATE_GONE;
				addCommandList(walkAcrossTheRoad());
			}

			Norbi.get().setVisible(true);
		}

		@Override
		protected void destroyDoors() {
			// not used.
		}

		@Override
		protected boolean isBusArrived() {
			return (busX <= getBusStopX());

		}
	}

	private class BusDoorControl extends WalkControl {

		private static final int GET_ON_PAUSE = 60;
		private NQ1Ids exitId;
		private String lbTo;

		BusDoorControl(String lbFrom, String lbTo, NQ1Ids id) {
			setLabel(lbFrom);
			setLook(Look.UP);
			exitId = id;
			this.lbTo = lbTo;
			addExamine(N06.OPEN_DOOR_WAITS_FOR_YOU);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			getGraph().connect(getLabel(), lbTo);
			return Cmd.list(walkLabel(),
					walk(getGraph().getVertex(lbTo)),
					Cmd.pause(GET_ON_PAUSE),
					Cmd.fadeOutToScreen(exitId, getListener(), true));
		}

	}

	public S06BusStop(StageFactory factory) {
		super(factory);
	}

	@Override
	protected void onStageReady(boolean isFirst) {
		walkOnFloor();
		getStageItem(BusStop.HotSpots.FRONT_DOOR).setVisible(false);
		getStageItem(BusStop.HotSpots.BACK_DOOR).setVisible(false);
		Id referer = NQ1Settings.get().getReferer();
		if (referer == NQ1Ids.BusFront) {
			busCommand = new BusToRockKlubCommand();
			addCommand(Norbi.Cmd.putTo("C"));
			addCommand(Norbi.Cmd.turnRight());
			busCommand.setWaiting();
			busLeave();

		} else if (referer == NQ1Ids.VRKFront) {
			busArrivedBack();
		} else {
			addCommand(Norbi.Cmd.putTo("S"));
			addCommand(Norbi.Cmd.turnRight());
			busCommand = new BusToRockKlubCommand();
//			onDebug();
		}
	}

	private void onDebug() {
		busCommand = null;
		getGraph().connect("A", "BackDoor");
		getStageItem(BusStop.HotSpots.BACK_DOOR).setVisible(true);
		getStageItem(BusStop.HotSpots.BACK_DOOR).setControl(null);
	}
	@Override
	public void execute(double timestamp) {
		super.execute(timestamp);
		if (busCommand == null || !busCommand.isValid()) {
			return;
		}
		if (busCommand.isReturnWay()) {
			if (busCommand.isBusGone()) {
				busCommand = new BusToRockKlubCommand();
			}
			return;
		}

		if (busCommand.isWaiting()) {
			waitCounter++;
			if (waitCounter % BUS_WAIT_TIME == 0) {
				busLeave();
			}
		} else {
			scheduleCounter++;
			if (scheduleCounter % BUS_SCHEDULE == 0) {
				busArrive();
			}
		}
	}

	private void busArrive() {
		addCommand(busCommand);

	}

	private void busLeave() {
		addCommand(busCommand);

	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((BusStop.HotSpots) hotSpotId) {
		case BACK_DOOR:
			return new BusDoorControl("A", "BackDoor", NQ1Ids.BusBack);
		case BALL:
			return createLookControl(Look.UP)
					.addExamine(N06.BALL_ON_ROOF)
					.addUse(N06.NO_CLIMB_FOR_IT);
		case BUS:
			return createLookControl(Look.UP)
					.addExamine(N06.HERE_IS_THE_BUS);
		case FRONT_DOOR:
			return new BusDoorControl("C", "FrontDoor", NQ1Ids.BusFront);
		case SEAT:
			return createLookControl(Look.UP)
					.addExamine(N06.SOMEBODY_BROKE_IT)
					.addUse(N06.CANT_BE_REPAIRED);
		case TIMETABLE:
			return createWalkControl("C", Look.RIGHT)
					.addExamine(N06.LETS_SEE_WHEN_BUS_COMES,
							N06.COMES_VERY_OFTEN)
					.addUse(N06.WHATS_FOR);

		case TRASH:
			return createWalkControl("C", Look.RIGHT)
					.addExamine(N06.MUST_BE_EMPTIED_RECENTLY)
					.addUse(N06.EMPTY_BUT_NO_RAG_AND_BONE);
		default:
			break;
		}
		return null;
	}

	@Override
	public void onRegionEnter(String region) {
		super.onRegionEnter(region);
		if (region.equals("S - NewsStand")) {
			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.NewsStand,
					getListener(), false));
		} else if (region.equals("B - Town")) {
			addCommands(Norbi.Cmd.turnRight(),
					Norbi.say(N06.NO_WALKING));
		}

	}

	private void busArrivedBack() {
		getGraph().setScaleRange(0.8, 1.5);
		Norbi.get().setVisible(false);
		getGraph().addVertex(490, 220, "D");
		getGraph().connect("C", "D");
		Norbi.get().setPosition(getGraph().getVertex("D"));
		addCommand(Norbi.Cmd.turnFront());
		busCommand = new BusFromRockKlubCommand();
		addCommand(busCommand);

	}

	private List<Command> walkAcrossTheRoad() {
		return Arrays.asList(Norbi.walk(getGraph().getVertex("B")),
				new OneShotCommand() {

					@Override
					public void start() {
						getGraph().getModel().removeVertex("D");
						getGraph().setScaleRange(1.0, 1.5);
					}
				});
	}

	@Override
	public void destroyStage() {
		busCommand.destroy();
		busCommand = null;
	}
}