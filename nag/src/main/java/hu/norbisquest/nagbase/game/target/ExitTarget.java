package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.Stage.Id;

public class ExitTarget extends SimpleTarget {
	private Id screenId;
	private GameModes mode;
	public ExitTarget(Id screenId, int left, int top, int right, int bottom,
			GameModes mode) {
		super("", left, top, right, bottom);
		this.screenId = screenId;
		this.setMode(mode);
	}
	public Id getScreenId() {
		return screenId;
	}
	public GameModes getMode() {
		return mode;
	}
	private void setMode(GameModes mode) {
		this.mode = mode;
	}
}
