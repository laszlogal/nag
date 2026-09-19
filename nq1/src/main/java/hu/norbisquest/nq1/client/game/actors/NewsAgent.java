package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nq1.client.data.bundle.NewsStand;
import hu.norbisquest.nq1.client.data.model.ActorModel;

public class NewsAgent extends ActorTarget {
	private static NewsAgent INSTANCE = null;

	private NewsAgent() {
		super(ActorModel.getNewsAgent());
	}

	@Override
	protected void prepareForStage() {
		//
	}

	@Override
	protected void addAnimations() {
		setTalkAnimation(NewsStand.INSTANCE.newsagent_sit(), NewsStand.Data.newsagent_talk);
		changeAnimation(NORMAL);
		stop();
	}

	public static NewsAgent get() {
		if (INSTANCE == null) {
			INSTANCE = new NewsAgent();
		}
		return INSTANCE;
	}

    @Override
    protected Conversation createConversation() {
		return new NewsAgentConversation(this, Norbi.get(), getActor());
    }

    public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}