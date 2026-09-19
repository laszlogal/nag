package hu.norbisquest.nagbase.common.engine;

import java.util.List;

public interface CommandProcessor {
    void addCommand(Command cmd);
    void addCommands(Command... cmds);
    void addCommandList(List<Command> cmd);
    void clearCommands();
    boolean process();
    boolean isIdle();
}
