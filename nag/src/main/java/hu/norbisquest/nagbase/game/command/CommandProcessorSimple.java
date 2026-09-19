package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.CommandProcessor;

import java.util.List;

public class CommandProcessorSimple implements CommandProcessor {
    private CommandQueue queue;

    public CommandProcessorSimple(CommandQueue queue) {
        this.queue = queue;
    }

    @Override
    public void addCommand(Command cmd) {
        queue.add(cmd);
    }

    @Override
    public void addCommands(Command... cmds) {
        for (Command cmd : cmds) {
            addCommand(cmd);
        }
    }

    @Override
    public void addCommandList(List<Command> list) {
        queue.append(list);
    }

    @Override
    public void clearCommands() {
        queue.clear();
    }

    @Override
    public boolean process() {
        return queue.process();
    }

    @Override
    public boolean isIdle() {
        return !queue.hasCommand();
    }
}
