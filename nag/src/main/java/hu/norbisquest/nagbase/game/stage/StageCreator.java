package hu.norbisquest.nagbase.game.stage;

import hu.norbisquest.nagbase.common.game.StageFactory;

public interface StageCreator {
    StageFactory createFactory();
    void createStage(StageFactory factory);
}
