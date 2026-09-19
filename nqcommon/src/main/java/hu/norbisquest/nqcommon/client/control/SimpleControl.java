package hu.norbisquest.nqcommon.client.control;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Speech;
import hu.norbisquest.nagbase.game.Walker;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.TargetControl;
import hu.norbisquest.nagbase.game.walk.WalkPoint;

import java.util.List;

public abstract class SimpleControl extends Cmd implements TargetControl {

	private static Walker walker;
	private HotSpot hotSpot;
	private boolean destroyed = false;

	protected SimpleControl(HotSpot hotSpot) {
		this.hotSpot = hotSpot;
	}

	protected SimpleControl() {
		this.hotSpot = null;
	}

    protected Command walk(WalkPoint walkPoint) {
		return walk((int) walkPoint.getX(), (int) walkPoint.getY());
	}

	Command say(Speech speech, int direction) {
		return walker.sayEnum(speech, direction);
	}

	Command walk(int x, int y) {
		return Cmd.walk(walker, x, y);
	}

    protected Command walkHotSpot() {
		if (hotSpot == null) {
			App.error("[SimpleControl] no hotSpot is set!");
			return null;
		}
		return Cmd.walk(walker, hotSpot.getLookX(), hotSpot.getLookY());
	}

	protected Command lookHotSpot() {
		if (hotSpot == null) {
			App.error("[SimpleControl] no hotSpot is set!");
			return null;
		}
		return Cmd.lookAt(walker, hotSpot.getLookX(), hotSpot.getLookY());
	}

	protected Command lookUp() {
		if (hotSpot == null) {
			App.error("[SimpleControl] no hotSpot is set!");
			return null;
		}
		return Cmd.lookAt(walker, hotSpot.getLookX(), 0);
	}

    protected Command lookRight() {
		if (hotSpot == null) {
			App.error("[SimpleControl] no hotSpot is set!");
			return null;
		}
		return Cmd.lookAt(walker, 9999, hotSpot.getLookY());
	}

	protected Command lookFront() {
		return Cmd.lookAt(walker, 0, 99999);
	}

	@Override
	public List<Command> onWalk(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

    protected static void setWalker(Walker walker) {
		SimpleControl.walker = walker;
	}

    public void setHotSpot(HotSpot hotSpot) {
		this.hotSpot = hotSpot;
	}

	@Override
	public void destroy() {
		if (isDestroyed()) {
			return;
		}
		if (hotSpot != null) {
			hotSpot.destroy();
			hotSpot = null;
		}
		walker = null;
		destroyCore();
		doDestroy();
		destroyed = true;
	}

	protected void destroyCore() {}
	protected void doDestroy() {}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}
}