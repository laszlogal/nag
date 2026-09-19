package hu.norbisquest.nagbase.game.conversation;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.ConversationTopic;
import hu.norbisquest.nagbase.common.game.HasConversation;
import hu.norbisquest.nagbase.core.CommandFactory;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.target.ActorTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Conversation extends NAGObject implements HasConversation {

	private Actor questioner;
	private Actor partner;
	private Map<Enum, Topic> topicMap=new HashMap<>();

    public Conversation(Actor questioner, Actor partner) {
        this.questioner = questioner;
        this.partner = partner;
		createTopics();
        restore();
        activteExternalTopics();
    }

    public Conversation(Actor questioner, ActorTarget target) {
        this(questioner, target.getActor());
        target.setConversation(this);
    }

	private void addTopic(Topic topic) {
		topicMap.put(topic.getId(), topic);
	}

	protected void addTopic(Enum tid, SpeechEnum s, CommandFactory cf, boolean active) {
		addTopic(new Topic(tid, questioner, s, cf, active, false));
	}

	protected void addBye(Enum tid, SpeechEnum s, CommandFactory cf, boolean active) {
		addTopic(new Topic(tid, questioner, s, cf, active, true));
	}

	protected void setTopicActive(final Enum id, final boolean active) {
		Topic t = topicMap.get(id);
		if (t != null) {
			t.setActive(active);
		}
	}

	protected void activteExternalTopics() {
    	//
	}

	@Override
	public List<ConversationTopic> getActiveTopics() {
		return topicMap.values().stream().filter(Topic::isActive).collect(Collectors.toList());
	}


	private List<Topic> getActiveTopicsImpl() {
		return topicMap.values().stream().filter(Topic::isActive).collect(Collectors.toList());
	}

	List<Command> getTopicCommands(Enum id) {
		return topicMap.get(id).getCommands();
	}


	protected abstract void createTopics();

	/**
	 * Put topic active values to JSON for saving.
	 * 
	 * @return JSON array of which topic is active and which is not.
	 */
	public static JSONArray idsToJSON(List<String> ids) {
		JSONArray array = new JSONArray();
        for (String id: ids) {
            array.set(array.size(), new JSONString(id));
        }
		return array;
	}

	public static List<String> parseIds(JSONArray array) {
		List<String> list = new ArrayList<>();
		for (int i=0;i < array.size(); i++) {
			list.add(array.get(i).isString().toString());
		}
		return list;
	}

	// Commands to insert to the command lists processed by
	// the gui CommandQueue.

	private Command cmdSetTopicActive(final Enum id, final boolean active) {
		return new OneShotCommand() {

			@Override
			public void start() {
				Topic t = topicMap.get(id);
				if (t != null) {
					t.setActive(active);
				}
			}
		};
	}

	private Command cmdSetTopicActive(final boolean active, final Enum... ids) {
		return new OneShotCommand() {

			@Override
			public void start() {
				for (Enum id: ids) {
					setTopicActive(id, active);
				}
			}
		};
	}

	protected Command cmdDisableAll(final boolean refresh) {
		return new OneShotCommand() {

			@Override
			public void start() {
				for (Topic topic : topicMap.values()) {
					topic.setActive(false);
				}
			}
		};
	}

	protected Command cmdEnableTopic(final Enum ...ids) {
		return cmdSetTopicActive(true, ids);
	}

	protected Command cmdEnableTopic(Enum  id) {
		return cmdSetTopicActive(id, true);
	}

	protected Command cmdDisableTopic(Enum id) {
		return cmdSetTopicActive(id, false);
	}

	protected Command cmdDisableTopic(final Enum ...ids) {
		return cmdSetTopicActive(false, ids);
	}

	public void addEnabledIndex(Enum id) {
		topicMap.get(id).setActive(true);
	}

	protected Command heroSay(SpeechEnum s) {
		return questioner.sayEnum(s);
	}


	protected List<Command> heroSay(SpeechEnum ...enums) { return questioner.sayEnum(enums);}

	protected List<Command> partnerSay(SpeechEnum ...enums) {
		return partner.sayEnum(enums);
	}

	protected Command partnerSay(SpeechEnum s) {
		return partner.sayEnum(s);
	}


	@Override
	public void doDestroy() {
		topicMap.forEach((id, t) -> {
			t.destroy();
			t = null;

		});
		questioner = null;
		partner = null;
		topicMap.clear();
		topicMap = null;
	}

	// Saving

	public void save() {
		List<String> list = activesToList();
		App.debug("SAVE: " + list);
		App.getSettings().saveConversationState(partner.getName(), list);
	}

	private void restore() {
		List<String> ids = App.getSettings().getConversationState(partner.getName());
		if (ids != null) {
			App.debug("RESTORED FROM SAVE: " + ids);
			enabledFromList(ids);
		}

	}

	private void enabledFromList(List<String> list) {
		List<Enum> actives = new ArrayList<>();
		list.forEach(s -> actives.add(toSafeId(s)));
		topicMap.forEach((id, t) -> t.setActive(actives.contains(id)));
		actives.clear();
	}

	private List<String> activesToList() {
		return getActiveTopicsImpl().stream().map(Topic::getIdString).collect(Collectors.toList());
	}

	private Enum toSafeId(String s) {
	    return toId(s.replaceAll("\"", ""));
    }

	protected abstract Enum toId(String s);
}