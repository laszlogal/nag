package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;

import java.util.List;

public abstract class ExitControl implements TargetControl {
	private GameModes mode;
	public ExitControl(GameModes mode) {
		this.mode = mode;
	}
	
	@Override
	public List<Command> onWalk(int x, int y) {
		if (mode == GameModes.WALK) {
			App.debug("onWalk: goToExit");
			return goToExit(x, y);
		}
		
		return null;
	}

	@Override
	public List<Command> onUse(int x, int y) {
		if (mode == GameModes.USE) {
			return goToExit(x, y);
		}
		return null;
	}
	
	abstract List<Command> goToExit(int x, int y);
	
	
}