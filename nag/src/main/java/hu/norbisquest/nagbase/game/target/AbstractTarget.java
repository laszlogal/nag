package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTarget extends NAGObject implements hu.norbisquest.nagbase.common.game.target.Target {
	private List<TargetControl> controls;
	private int controlIndex;
	private int zIndex = 0;

	protected AbstractTarget() {
		controls = new ArrayList<>();
		controlIndex = 0;
	}

	@Override
	public TargetControl getControl() {
		if (controlIndex >= controls.size()) {
			return null;
		}
		return controls.get(getControlIndex());
	}

	@Override
	public void addControl(TargetControl control) {
		controls.add(control);
	}

	@Override
	public void setControl(TargetControl control) {
		controls.add(control);

	}

	public int getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(int controlIndex) {
		this.controlIndex = controlIndex;
	}

	@Override
	public int getZIndex() {
		return zIndex;
	}

	void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	@Override
    public void doDestroy() {
	    for (TargetControl ctrl: controls) {
			App.infoDestroy(" control " + ctrl);
			ctrl.destroy();
	        ctrl = null;
        }
        controls.clear();
    }

    @Override
    public String getPrefix() {
	    return "";
    }

}
