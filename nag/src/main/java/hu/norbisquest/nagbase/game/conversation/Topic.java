package hu.norbisquest.nagbase.game.conversation;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.ConversationTopic;
import hu.norbisquest.nagbase.core.CommandFactory;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.SpeechEnum;

import java.util.List;

public class Topic extends NAGObject implements ConversationTopic  {
    private Enum id;
    private Actor actor;
    private boolean active;
    private boolean bye;
    private String text;
    private CommandFactory cf=null;

    Topic(Enum id, Actor actor, SpeechEnum se, CommandFactory cf, boolean active, boolean bye) {
        this.id = id;
        this.actor = actor;
        this.text = actor.speak(se.value()).getText();
        this.cf = cf;
        this.active = active;
        setBye(bye);
    }

    @Override
    public final List<Command> getCommands() {
        return cf != null ? cf.get(): null;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public Enum getId() {
        return id;
    }

    String getIdString() {
        return id.toString();
    }

    public Actor getActor() {
        return actor;
    }

    public boolean isBye() {
        return bye;
    }

    private void setBye(boolean bye) {
        this.bye = bye;
    }

    @Override
    public void doDestroy() {
        App.infoDestroy("Topic: " + text);
        id = null;
        cf = null;
        actor = null;
        text = null;
    }
}
