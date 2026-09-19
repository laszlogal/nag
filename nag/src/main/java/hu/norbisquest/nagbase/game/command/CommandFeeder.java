package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;

import java.util.List;

public interface CommandFeeder {
	void addCommand(Command cmd);
	void addCommandList(List<Command> cmd);
	void clearCommands();
}
