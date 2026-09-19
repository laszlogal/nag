package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.dom.client.CanvasElement;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.core.NAGAnimation;
import hu.norbisquest.nagbase.core.NAGAnimation.IAnimationListener;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.data.bundle.Room;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N01;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nq1.client.game.actors.Norbi.Role;

import java.util.ArrayList;
import java.util.List;

public class S01Room extends NQ1Stage {

	private static final int TV_X = 123;
	private static final int TV_Y = 159;
	private NAGAnimation tvProgram;
	private Layer tvLayer;
	private boolean onFloor;
	private static final int TV_PROGRAM_SPEED = 23;

	private class BooksControl extends NQControl {

		BooksControl() {
			setLabel("WARDROBE");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N01.BOOKS, N01.DONT_READ_HALF);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(App
					.getTimestamp() % 2 == 0
							? N01.DONT_READ_NOW
							: N01.NO_SECRET_PASS);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}
	}

	private class TVControl extends NQControl {
		TVControl() {
			setLabel("TV");
			if (NQ1Settings.get().isTvWatched()) {
				tvProgram = null;
			} else {
				createProgram();
			}
		}

		private void createProgram() {
			tvProgram = new NAGAnimation(Room.Data.tv_program);
			tvProgram.setAnimationSpeed(TV_PROGRAM_SPEED);
			tvProgram.setLoop(false);
			tvProgram.setVisible(false);
			tvProgram.setAnimListener(new IAnimationListener() {

				@Override
				public void onLoop(NAGAnimation animation) {
				}

				@Override
				public void onAnimationEnd(NAGAnimation animation) {
					tvLayer.clear();
                }
			});
			tvLayer = new Layer();
			tvLayer.setZIndex(Layer.Z_FOREGROUND3);
			setLayer("tvLayer", tvLayer);

		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N01.MY_TV);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (NQ1Settings.get().isTvWatched()) {
				return walkAndSay(N01.NO_TV_NOW);
			}
			return Cmd.list(
					walkAndSay(N01.ANY_INTERESTING),
					Norbi.walk(getGraph().getVertex("S")),
					new OneShotCommand() {
						@Override
						public void start() {
							getForeground().setMaskEnable(false);

							tvProgram.start();
							NQ1Settings.get().setTvWatched(true);
						}
					},
					Norbi.Cmd.putTo("SIT"),
					Norbi.Cmd.face(Norbi.SIT_ON_BED),
					Cmd.playSfx(Room.INSTANCE.tv_sound()),
					new OneShotCommand() {

						@Override
						public void start() {
							getForeground().setMaskEnable(true);

						}
					},
					Norbi.Cmd.turnFront(),
					Norbi.Cmd.putTo("S"),
					Norbi.say(N01.NOTHING));

		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			if (id == InventoryModel.Ids.TOILET_BRUSH) {
				return walkAndSay(N01.NO_REHASH);

			}
			return null;
		}

	}

	private class DeskControl extends NQControl {

		DeskControl() {
			setLabel("E");
			setLook(Look.FRONT);
		}

		List<Command> openDrawer(boolean open) {
			return Cmd.asList(
					Norbi.Cmd.face(Norbi.GRAB_FRONT),
					Cmd.playSfx(Room.INSTANCE.drawer_sound(), false),
					Cmd.pause(5),
					Cmd.setItemVisible(S01Room.this, Room.HotSpots.DRAWER, open),
					Norbi.Cmd.turnFront());
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(N01.DESK);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (getStageItem(Room.HotSpots.DRAWER).isVisible()) {
				return walkAndSay(N01.NOTHING_FROM_HERE);
			}

			return Cmd.list(walkLabel(), openDrawer(true));

		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}
	}

	private class DrawerControl extends DeskControl {
		@Override
		public List<Command> onExamine(int x, int y) {
			List<Command> list = Cmd.list(walkLabel(),
					Norbi.Cmd.turnFront(), Norbi.say(N01.WHATS_IN_DRAWER));

			if (NQ1Settings.get().isKeyFoundInDrawer()) {
				list.addAll(Cmd.list(Norbi.say(N01.KEY_FOR_SLEEVE),
						Norbi.say(N01.TAKE_IT),
						Norbi.Cmd.take(S01Room.this,
								null, InventoryModel.Ids.SLEEVE_KEY),
						Norbi.Cmd.face(Norbi.GRAB_FRONT),
						Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
						Cmd.pause(10),
						Cmd.resetMode(),
						Norbi.Cmd.face(Norbi.WALK_FRONT)));
			} else {
				list.add(Norbi.say(N01.EVERYTHING_I_DONT_NEED));
			}
			return list;
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(), openDrawer(false));

		}

	}

	public S01Room(StageFactory factory) {
		super(factory);
	}

	private void sayIntroSpeech() {
		addCommandList(Cmd.list(
				Norbi.Cmd.turnFront(),
				Norbi.sayAll(N01.SZURDI_IN_THE_CLUB,
						N01.NEED_GET_MONEY_FOR_FEE)));
	}

	class WardrobeControl extends NQControl {

		WardrobeControl() {
			setLabel("WARDROBE_DOOR");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			if (NQ1Settings.get().isWardrobeOpened()) {
                List<Command> list = new ArrayList<>(Cmd.list(walkLabel(),
                        Norbi.Cmd.turnLeft(),
                        Norbi.say(N01.LETS_SEE),
                        Norbi.Cmd.setRole(Role.SQUAT_LEFT)));
				if (!App.getInventory().has(InventoryModel.Ids.SLEEVE)) {
					list.addAll(Cmd.list(Norbi.sayAll(N01.SLEEVE_WITH_MONEY,
							N01.NEED_THIS_NOW),
							Cmd.playSfx(Common.INSTANCE.take_sfx(), false),
							Norbi.Cmd.take(S01Room.this,
									null, InventoryModel.Ids.SLEEVE)));
				} else {
					list.add(Norbi.say(N01.NOTHING_I_NEED));
				}
				list.add(Norbi.Cmd.restoreRole());
				list.add(Norbi.Cmd.putTo("WARDROBE"));
				return list;
			}
			return walkAndSay(N01.MY_BELONGINGS);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(),
					Norbi.Cmd.face(Norbi.GRAB_LEFT),
					new OneShotCommand() {

						@Override
						public void start() {
							NQ1Settings.get().toggleWardrobeDoor();
						}
					},
					Cmd.setItemVisible(S01Room.this, Room.HotSpots.WARDROBE_DOOR,
							!NQ1Settings.get().isWardrobeOpened()),
					Cmd.playSfx(Room.INSTANCE.wardrobe_sound(), false),
					Cmd.pause(5),
					Norbi.Cmd.face(Norbi.WALK_LEFT));
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}

	}

	private class PosterControl extends NQControl {
		PosterControl() {
			setLabel("TV");
		}

		@Override
		public List<Command> onExamine(int x, int y) {
			return Cmd.list(walkLabel(), lookUp(),
					Norbi.say(N01.PRISON_BREAK),
					lookFront(), Norbi.say(N01.HOWTO_ESCAPE));
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(walkLabel(), lookUp(),
					Norbi.say(N01.NO_PULL_DOWN));
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}

	}

	private class DoorControl extends NQControl {

		@Override
		public List<Command> onExamine(int x, int y) {
			return lookAndSay(N01.DOOR);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			if (!onFloor) {
				return lookAndSay(N01.WHY_CLOSE);
			}
			return null;
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return null;
		}

	}

	@Override
	protected void onStageReady(boolean isFirst) {
		addCommand(Norbi.Cmd.setRole(Role.WALK));

		if (App.getSettings().getReferer() == NQ1Ids.Corridor) {
			addCommand(Norbi.Cmd.putTo("Corridor"));
			addCommand(Norbi.walk(getGraph().getVertex("G")));
			walkOnFloor();
		} else {
			addCommand(Norbi.Cmd.putTo("S"));
			walkOnCarpet();
		}

		if (isFirst) {
			sayIntroSpeech();
		}
//		addCommand(Cmd.wrap(this::musicOn));
	}

	private void musicOn() {
		App.getAudioManager().setMusicOn(true);
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		switch ((Room.HotSpots) hotSpotId) {
		case BED:
			return createLookControl()
					.addExamine(N01.BED)
					.addUse(N01.NO_RELAX);
		case BOOKS:
			return new BooksControl();
		case TV:
			return new TVControl();
		case WARDROBE:
			return new WardrobeControl();
		case BOX:
			return createWalkControl("WARDROBE", Look.UP)
					.addExamine(N01.STORE_EVERYTHING)
					.addUse(N01.NO_TIME_FOR_THIS);
		case DESK:
			return new DeskControl();
		case DOOR:
			return new DoorControl();

		case DRAWER:
			return new DrawerControl();
		case DVD_PLAYER:
			return createWalkControl("TV", Look.UP)
					.addExamine(N01.FOR_MOVIES)
					.addUse(N01.NO_TIME_FOR_MOVIES);

		case PICTURE:
			return createWalkControl("F", Look.UP)
					.addExamine(N01.FUNNY_CHARACTERS)
					.addUse(N01.RIGHT_PLACE);
		case POSTER:
			return new PosterControl();
		case POWERSTRIP:
			return createWalkControl("POWERSTRIP", Look.LEFT)
					.addExamine(N01.POWERSTRIP)
					.addUse(N01.DONT_TOUCH);
		case WARDROBE_DOOR:
		case WARDROBE_INSIDE:
		case WINDOW:
			return createWalkControl("A", Look.LEFT)
					.addExamine(N01.SEE_HALF_TOWN, N01.NOTHING_SPECIAL)
					.addUse(N01.NO_NEED_TO_OPEN);
		default:
			return null;

		}
	}

	@Override
	public void execute(double timestamp) {
		super.execute(timestamp);

		if (tvProgram != null && tvProgram.tick(timestamp) && tvProgram.isRunning()) {
			tvLayer.clear();
			CanvasElement frame = tvProgram.getCurrentFrame();
			tvLayer.draw(frame, TV_X, TV_Y);
			frame = null;
		}
	}

	@Override
	public void onRegionEnter(String region) {
            super.onRegionEnter(region);

		if (region.equals("G2 - Corridor")) {

			addCommandList(Cmd.fadeOutToScreen(NQ1Ids.Corridor,
					getListener(), false));
		}

		if (region.equals("Y2 - F")) {
			walkOnFloor();
		}

	}

	@Override
	public void reload() {
		addCommandList(Cmd.fadeOutToScreen(NQ1Ids.Room,
				getListener(), false));
	}

	@Override
	public void onRegionLeave(String region) {
		super.onRegionLeave(region);

		if (region.equals("B - Y") || region.equals("B - Y2")) {
			walkOnFloor();

		} else if (region.equals("F - Y") || region.equals("F - Y2")) {
			walkOnCarpet();
		}
	}

	@Override
    void walkOnFloor() {
		super.walkOnFloor();
		onFloor = true;
	}

	@Override
	public void destroyStage() {
		if (tvProgram != null) {
			tvProgram.destroy();
			tvProgram = null;
			tvLayer.destroy();
			tvLayer = null;
		}
	}

	private void walkOnCarpet() {
		onFloor = false;
		Norbi.get().setWalkSfx(
				Room.INSTANCE.walk_carpet1(),
				Room.INSTANCE.walk_carpet2());

	}

	@Override
	public void autoPilot(Settings.AutoPilot state) {
		click(Room.HotSpots.WARDROBE_DOOR);
		App.setMode(App.GameModes.EXAMINE);
		onClick(279, 270); // wardrobe
		App.setMode(App.GameModes.USE);
		click(Room.HotSpots.TV);
	}
}
