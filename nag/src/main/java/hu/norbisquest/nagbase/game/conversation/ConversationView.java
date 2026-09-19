package hu.norbisquest.nagbase.game.conversation;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.ConversationTopic;
import hu.norbisquest.nagbase.common.game.HasConversation;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.NAGPopupPanel;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.gui.Scalable;

import java.util.ArrayList;
import java.util.List;

public class ConversationView extends NAGPopupPanel implements ClickHandler,
		TopicLabel.TopicAnimation {
	private static final int MIN_FONT_SIZE = 8;
	private static final int MAX_FONT_SIZE = 20;
	public static ConversationView INSTANCE = null;
	private FlowPanel main;
	private HasConversation conversation;
	private boolean enableAutoFeed = true;
	private List<Command> selectCmds = null;
	private List<TopicLabel> labels = null;
	private TopicLabel selected;

	private ConversationView(Scalable scaler) {
		super(scaler);
		setStyleName(Browser.isAndroid() ? "conversationPanel-android" : "conversationPanel");
		main = new FlowPanel();

		setGlassEnabled(false);
		setModal(true);
		setAutoHideEnabled(false);
		setWidget(main);
		labels = new ArrayList<>();
	}

    public static ConversationView get(Scalable scaler) {
	    if (INSTANCE == null) {
            INSTANCE = new ConversationView(scaler);
        }
        return INSTANCE;
	}

    public void open(HasConversation conversation) {
		this.conversation = conversation;
		List<ConversationTopic> topics = conversation.getActiveTopics();
		App.debug("ACTIVE TOPICS: " + topics.size());
		if (topics.size() == 1 && isEnableAutoFeed()) {
			feedTopic(topics.get(0));
		} else {
			createQuestions(topics);
			centerDeferred();
		}
	}

	@Override
	public int getOffsetWidth() {
		return main.getOffsetWidth();
	}

	@Override
	public int getOffsetHeight() {
		return main.getOffsetHeight();
	}

	private void centerDeferred() {
		show();
		Scheduler.get().scheduleDeferred(this::center);
	}

	private void createQuestions(final List<ConversationTopic> topics) {
		main.clear();
		int size = topics.size();

		double diff = 0.3 / size;
		double delay = 0;

		for (ConversationTopic topic : topics) {
			TopicLabel lbTopic = new TopicLabel(topic);
			lbTopic.setAnimationDelay(delay);
			delay += diff;
			lbTopic.addClickHandler(this);
			labels.add(lbTopic);
			main.add(lbTopic);
		}
	}

	private void onBye() {
		if (conversation == null) {
			App.debug("nullllll");
			return;
		}
		conversation.save();
		conversation.destroy();
		conversation = null;
	}

	private void topicsOut() {
		double diff = 0.3 / labels.size();
		double delay = 0.3;
		for (TopicLabel label: labels) {
			label.setStyleName(label == selected ? "selected":"out");
			label.setTopicOutAnimation(this);
			label.setAnimationDelay(delay);
			delay -= diff;
		}
	}

	private void close() {
        App.getFeeder().addCommand(new ConversationClose());
	}

	private void reopen()
	{
		App.debug("reopening   ");
		App.getFeeder().addCommand(new ConversationOpen(conversation));
	}

	private boolean isEnableAutoFeed() {
		return enableAutoFeed;
	}

	public void setEnableAutoFeed(boolean enableAutoFeed) {
		this.enableAutoFeed = enableAutoFeed;
	}

	private void destroyCommands() {
		if (selectCmds == null) {
			return;
		}

		for (Command cmd: selectCmds) {
			if (cmd instanceof Destroyable) {
				((Destroyable)cmd).destroy();
				cmd = null;
			}
			selectCmds.clear();
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		if (source instanceof TopicLabel) {
			onClick((TopicLabel)source);
		}
	}

	private void onClick(TopicLabel topicLabel) {
		selected = topicLabel;
		App.debug("- click to " + selected.getText());
		feedTopic(selected.getTopic());
	}

	private void feedTopic(ConversationTopic topic) {
		App.getFeeder().addCommand(new OneShotCommand() {
			@Override
			public void start() {
				topicsOut();
				setModal(false);
				selectCmds = topic.getCommands();
				App.getFeeder()
						.addCommandList(selectCmds);
				App.getFeeder().addCommand(new OneShotCommand() {
											   @Override
											   public void start() {
												   destroyCommands();
											   }
										   }
				);
				}
		});
	}

	@Override
	public void onTopicOut(TopicLabel label) {
		close();
		label.addStyleName("hidden");
		if (label.getTopic().isBye()) {
			onBye();
		} else {
			reopen();
		}
		App.debug("onTopicOut");
	}

	@Override
	public void destroy() {
		super.destroy();
		App.infoDestroy("ConversationView");
		labels.forEach(this::removeAnimation);
		labels.clear();
		labels = null;
		if (conversation != null) {
			conversation.destroy();
	 		conversation = null;
		}
		main.clear();
	}

	public static void destroyInstace() {
		if (INSTANCE == null) {
			return;
		}
		INSTANCE.destroy();
		INSTANCE = null;
	}

	private void removeAnimation(TopicLabel topicLabel) {
		topicLabel.removeAnimations(this);
	}
}
