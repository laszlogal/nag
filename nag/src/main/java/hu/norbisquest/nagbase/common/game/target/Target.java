package hu.norbisquest.nagbase.common.game.target;

import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.Hitable;
import hu.norbisquest.nagbase.game.target.TargetControl;

public interface Target extends Destroyable, Hitable {
    TargetControl getControl();

    void addControl(TargetControl control);

    void setControl(TargetControl control);

    int getZIndex();
}
