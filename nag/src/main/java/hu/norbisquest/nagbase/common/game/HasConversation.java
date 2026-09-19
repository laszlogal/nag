package hu.norbisquest.nagbase.common.game;

import java.util.List;

public interface HasConversation {
    List<ConversationTopic> getActiveTopics();
    void save();
    void destroy();
}
