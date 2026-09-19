package hu.norbisquest.nagbase.common.game;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.Destroyable;

import java.util.List;

public interface ConversationTopic extends Destroyable {
    String getText();
    List<Command> getCommands();
    boolean isBye();
    boolean isActive();
}
