package hu.norbisquest.nq1.client.game.stages;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.game.actors.Norbi;

public class S00TestRoom extends NQ1Stage {


	public S00TestRoom(StageFactory factory) {
		super(factory);
	}
	@Override
	protected void onStageReady(boolean isFirst) {
		super.onAvailable(isFirst);
		Norbi.get().putTo("A");
		Norbi.get().changeAnimation(Norbi.WALK_LEFT);
	}

	@Override
	protected NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		return null;
	}

	@Override
	protected void destroyStage() {

	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_A) {
			squat();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_Q) {
			standUp();
		}else {
			super.onKeyDown(event);
		}
	}

	private void squat() {
		addCommandList(Cmd.list(
				Norbi.Cmd.turnLeft(),
				Norbi.Cmd.setRole(Norbi.Role.SQUAT_LEFT)));

	}

	private void standUp() {
		addCommand(Norbi.Cmd.restoreRole());

	}
}