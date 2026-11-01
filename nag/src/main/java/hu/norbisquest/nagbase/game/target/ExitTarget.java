package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.game.App.GameModes;

public class ExitTarget extends SimpleTarget {
	private ScreenId screenId;
	private GameModes mode;
	public ExitTarget(ScreenId screenId, int left, int top, int right, int bottom,
                      GameModes mode) {
		super("", left, top, right, bottom);
		this.screenId = screenId;
		this.setMode(mode);
	}
	public ScreenId getScreenId() {
		return screenId;
	}
	public GameModes getMode() {
		return mode;
	}
	private void setMode(GameModes mode) {
		this.mode = mode;
	}
}
