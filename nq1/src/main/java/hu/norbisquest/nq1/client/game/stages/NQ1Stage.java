 package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.event.dom.client.ContextMenuEvent;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nq1.client.NQ1TextsEN;
import hu.norbisquest.nq1.client.NQ1TextsHUN;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.InventoryFactory;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nqcommon.client.NQStage;

import java.util.List;

public abstract class NQ1Stage extends NQStage {
	class DoorControl extends WalkControl {

		private static final int GRAB_TIME = 10;
		private ScreenListener listener;
		private Id exitId;
		private int grabDirection;

		DoorControl(String label, Look look, Id exitId) {
			super();
			setLabel(label);
			setLook(look);
			this.exitId = exitId;
			this.listener = NQ1Stage.this.getListener();
			switch (look) {
				case FRONT:
					break;
				case LEFT:
					grabDirection = Norbi.KNOB_LEFT;
					break;
				case RIGHT:
					grabDirection = Norbi.GRAB_RIGHT;
					break;
				case UP:
					grabDirection = Norbi.GRAB_BACK;
					break;
				default:
					break;

			}

		}

		@Override
		public List<Command> onUse(int x, int y) {
			return Cmd.list(
					walkLabel(),
					Norbi.Cmd.face(grabDirection),
					Cmd.playSfx(Common.INSTANCE.knob_sfx(), false),
					Cmd.pause(GRAB_TIME),
					Cmd.fadeOutToScreen(exitId, listener, false));
		}
	}

	NQ1Stage(StageFactory factory) {
		super(factory);
//		NQ1Settings.get().addHero(Norbi.get());
		addActor(Norbi.get());
		StageModel res = getResource();
		if (res.heroAudio != null) {
			Norbi.get().setAudio(res.heroAudio);
		}

		if (res.heroLines != null) {
			Norbi.get().setLines(res.heroLines);
		}
	}

	@Override
	protected Norbi getNorbi() {
		return Norbi.get();
	}

	@Override
	protected String getText(int textId, String lang) {
		return "EN".equals(lang)
				? NQ1TextsEN.texts.get(textId)
				: NQ1TextsHUN.texts.get(textId);
	}

	void walkOnFloor() {
		Norbi.get().setWalkSfx(Common.INSTANCE.walk_floor1(),
				Common.INSTANCE.walk_floor2());
	}

	@Override
	public void onInventoryClose() {
		InventoryFactory.restoreSpeech();

	}

	DoorControl createDoorControl(String label, Look look, Id exitId) {
		return new DoorControl(label, look, exitId);
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		if (App.getMode() == GameModes.EXAMINE) {
			App.setMode(GameModes.USE);
		} else {
			App.setMode(GameModes.EXAMINE);
		}
	}

	@Override
	public void destroyHeroes() {
		removeActor(Norbi.get());
		Norbi.destroyInstance();
	}

	@Override
	public void destroyActors() {

	}
}