package hu.norbisquest.nagbase.common.stage;

import hu.norbisquest.nagbase.common.engine.CommandProcessor;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.common.gui.HasZoom;

public interface TargetList {
    void add(Target target);
    void add(int index, Target target);
    Target get(int x, int y);
    boolean handle(int x, int y);
    void setProcessor(CommandProcessor processor);
    void setZoomer(HasZoom zoomer);
}
