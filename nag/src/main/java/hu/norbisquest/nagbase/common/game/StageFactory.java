package hu.norbisquest.nagbase.common.game;

import hu.norbisquest.nagbase.common.engine.CommandProcessor;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.game.target.HotSpotContainer;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.common.gui.ScreenDimension;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;

import java.util.List;

public interface StageFactory {
    CommandProcessor createProcessor();
    HotSpotContainer createHotSpotContainer(ScreenId id);
    List<Target> createEmptyTargets();
    List<Actor> createEmptyActors();
    StageModel getModel();
    ScreenParent getParent();
    ScreenDimension getDimension();
}
