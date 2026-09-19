package hu.norbisquest.nagbase.core;

import hu.norbisquest.nagbase.common.engine.Command;

import java.util.List;

public interface CommandFactory {
    List<Command> get();
}
