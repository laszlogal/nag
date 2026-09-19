package hu.norbisquest.nagbase.game.conversation;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Label;
import hu.norbisquest.nagbase.common.game.ConversationTopic;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.game.App;

class TopicLabel extends Label implements Destroyable {
    interface TopicAnimation {
         void onTopicOut(TopicLabel label);
    }
    private ConversationTopic topic;

    TopicLabel(ConversationTopic topic) {
        this.topic = topic;
        setText(topic.getText());
        addStyleName("topic");
    }

    void setAnimationDelay(double delay) {
        getElement().setAttribute("style", "animation-delay: " + delay + "s;");
    }

    ConversationTopic getTopic() {
        return topic;
    }

    void setTopicOutAnimation(final TopicAnimation callback) {
        App.runOnAnimation(() -> callback.onTopicOut(this),
                getElement(), "selected");
    }

    void removeAnimations(TopicAnimation callback) {
        removeEventListener(getElement(), callback);
    }
    native void removeEventListener(Element element, TopicAnimation callback) /*-{
        element.removeEventListener("animationend", callback);
    }-*/;

    @Override
    public void destroy() {
        topic = null;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
