package hu.norbisquest.nagbase.game;

import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.game.gui.Scalable;

public abstract class AbstractDialogManager implements DialogManager {

	private Scalable scaler;

	public void setScaler(Scalable scaler) {
		this.scaler = scaler;
	}

	public Scalable getScaler() {
		return scaler;
	}
}
