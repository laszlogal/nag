package hu.norbisquest.nagbase.game.stage;

import hu.norbisquest.nagbase.common.engine.CommandProcessor;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.common.game.target.HotSpotContainer;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.HotSpotContainerSimple;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.command.CommandProcessorSimple;
import hu.norbisquest.nagbase.game.command.CommandQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class StageFactorySimple implements StageFactory {
    @Override
    public CommandProcessor createProcessor() {
        return new CommandProcessorSimple(new CommandQueue());
    }

    @Override
    public HotSpotContainer createHotSpotContainer(Stage.Id id) {
        return new HotSpotContainerSimple(id);
    }

    @Override
    public List<Target> createEmptyTargets() {
        return new ArrayList<>();
    }

    @Override
    public List<Actor> createEmptyActors() {
        return new ArrayList<>();
    }
}
