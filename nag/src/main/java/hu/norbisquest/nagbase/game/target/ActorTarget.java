package hu.norbisquest.nagbase.game.target;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.Tickable;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.*;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.conversation.ConversationOpen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ActorTarget extends SimpleTarget implements Tickable {
	private int talkSpeed = 12;

	public static final int NORMAL=0;
	public static final int TALK=1;

	public ActorId getId() {
		return id;
	}

	public void setId(ActorId id) {
		this.id = id;
	}

	public interface ActorId {}

	private Actor actor;
	private Conversation conversation;
	private Layer layer;
	private ActorId id;


	public ActorTarget(String name, int left, int top, int right, int bottom) {
		super(name, left, top, right, bottom);
		actor = new Actor(name) {
			@Override
			protected void addAnimations() {
				ActorTarget.this.addAnimations();
			}

			@Override
			protected void destroyPerson() {
				ActorTarget.this.destroyPerson();
			}
		};
		actor.setPosition(left, top);
	}

	private ActorTarget(String name, int left, int top, int right, int bottom,
                        Map<Integer, String> lines, List<DataResource> audioData) {
		super(name, left, top, right, bottom);
		actor = new Actor(name, lines, audioData) {

			@Override
			protected void addAnimations() {
				ActorTarget.this.addAnimations();
				ActorTarget.this.getActor().load();

			}

			@Override
			protected void destroyPerson() {
				ActorTarget.this.destroyPerson();
			}
		};
		actor.setPosition(left, top);
	}

	protected ActorTarget(ActorData res) {
		this(res.name, res.left, res.top, res.right, res.bottom,
				res.getText("HU"), res.getAudio("HU"));
		actor.setScale(res.scale);
		if (res.lines != null) {
			actor.setLines(res.lines);
		}
		setTextPosition(res.textX, res.textY);
		setTextStyle(res.textStyleName);
		layer = createLayer();

	}

	protected Layer createLayer() {
		Layer l = new Layer();
		l.setZIndex(Layer.Z_BACKGROUND2);
		return l;
	}

	private int getTalkAnimId() {
		return 1;
	}

	@Override
	public boolean tick(double timestamp) {
		return actor.tick(timestamp);
	}

	private Speech speak(int idx) {
		return actor.speak(idx);
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	protected void addAnimation(Integer id, List<ImageResource> frames,
                                int speed) {
		actor.addAnimationPreloaded(id, frames, speed);
	}

	protected void addAnimation(Integer id, ImageResource frame) {
		actor.addAnimationPreloaded(id, frame);
	}

	public void changeAnimation(Integer id) {
		actor.changeAnimation(id);
		actor.update();
	}

	protected void start() {
		actor.start();
		actor.update();

	}

	protected void stop() {
		actor.stop();
	}

	private void drawTo(Layer layer) {
		actor.drawTo(layer);
	}

	public void clearOn(Layer layer) {
		actor.clearOn(layer);
	}

	public boolean isValid() {
		return actor.isValid();
	}

	private Command say(Speech speech, int direction) {
		if (speech == null) {
			App.error("say1: Speech is null!");
			return null;
		}
		return actor.sayEnum(speech.getText(), speech.getAudio(), direction);
	}

	public Command say(Speech speech) {
		if (speech == null) {
			App.error("say2: Speech is null!");
			return null;
		}
		return actor.sayEnum(speech.getText(), speech.getAudio());
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public JSONObject toJSON() {
		return null;
	}

	private void setTalkBase(int talkBase) {
		actor.setTalkBase(talkBase);
	}

	public Command cmdSetTalkBase(final int talkBase) {
		return new OneShotCommand() {

			@Override
			public void start() {
				setTalkBase(talkBase);
			}
		};
	}

	@Deprecated
	public Command cmdEnableTopic(final Enum id) {
		return new OneShotCommand() {

			@Override
			public void start() {
				conversation.addEnabledIndex(id);
			}
		};
	}

	private Command say(int idx) {
		return say(speak(idx), getTalkAnimId());

	}

	public Command say(SpeechEnum s) {
		return say(s.value());

	}

	protected List<Command> say(SpeechEnum... speeches) {
		List<Command> list = new ArrayList<>();
		for (SpeechEnum s : speeches) {
			list.add(say(s));
		}
		return list;

	}

	protected Command face(final int phase) {
		return new OneShotCommand() {

			@Override
			public void start() {
				actor.changeAnimation(phase);
			}
		};

	}

	public void onAvailable() {
		App.getCurrentScreen().setLayer(actor.getName(), getLayer());
		addAsTarget(App.getCurrentStage());
		prepareForStage();
		draw();
	}

	protected abstract void prepareForStage();

	private Layer getLayer() {
		if (layer == null) {
			layer = createLayer();
		}
		return layer;
	}

	public void draw() {
		layer.clear();
		drawTo(layer);
	}

	public void setControl(TargetControl control) {
		actor.getTarget().setControl(control);
	}

	private void addAsTarget(Stage stage) {
		stage.addTarget(actor.getTarget());
	}

	private void setTextPosition(int x, int y) {
		actor.setTextPosition(x, y);
	}

	private void setTextStyle(String style) {
		actor.addTextStyleName(style);
	}

	public void execute(double timestamp) {
		if (actor.tick(timestamp)) {
			actor.drawTo(layer);
		}
	}

	protected abstract void addAnimations();

	protected void setTalkAnimation(ImageResource base, List<ImageResource> data) {
		addAnimation(NORMAL, base);
		addAnimation(TALK, data, getTalkSpeed());
		actor.setTalkAnimation(NORMAL, TALK);
	}

	@Override
	public final void doDestroy() {
		App.infoDestroy(" actor " + actor.getName());
		actor.doDestroy();
		layer = null;
	}
	protected int getTalkSpeed() {
		return talkSpeed;
	}

	protected void setTalkSpeed(int value) {
		talkSpeed = value;
	}

	protected abstract Conversation createConversation();

	public Command openConversation() {
		conversation = createConversation();
		return new ConversationOpen(conversation);
	}

	protected void destroyPerson() {}
}
