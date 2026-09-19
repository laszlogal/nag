package hu.norbisquest.nagbase.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.core.MultiAnimation;
import hu.norbisquest.nagbase.core.NAGLabel;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.command.SpeakCommand;
import hu.norbisquest.nagbase.game.target.AbstractTarget;

import java.util.*;

public abstract class Actor extends MultiAnimation implements Loadable {
	public static final int DEFAULT_DIRECTION = -1;

	private int talkAnimId = DEFAULT_DIRECTION;
	protected class ActTarget extends AbstractTarget {
		boolean zoomed = false;
		double zoom = 1;

		public int getPosX() {
			return posX;
		}

		public int getPosY() {
			return posY;
		}

		@Override
		public boolean isHit(int x, int y) {
			int x0 = getPosX();
			int y0 = getPosY();

			double w = getWidth();
			double h = getHeight();

			int right = 0;
			int bottom = 0;

			if (zoomed) {
				w *= zoom;
				h *= zoom;
				x0 -= w / 2;
				y0 -= h / 2;
				right = (int) (x0 + w);
				bottom = (int) (y0 + h);
			} else {
				right = (int) (x0 + w);
				bottom = (int) (y0 + h);
			}

			boolean boxHit = x > x0 && x < right && y > y0 && y < bottom;
			return boxHit && (zoomed || getImageData().getAlphaAt(x - x0, y - y0) != 0);
		}

	}

	private int posX;
	private int posY;
	private int textX = 0;
	private int textY = 0;
	private Integer talkBase;
	private boolean talking;
	private Speaker speaker;
	private hu.norbisquest.nagbase.common.game.target.Target target;
	private NAGLabel subLabel;

	protected Actor(String name) {
		super(name);
		talkBase = 0;

		talking = false;
		target = newTarget();
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				addAnimations();
				addStageDependentAnimations();
			}

			@Override
			public void onFailure(Throwable reason) {
			}
		});

	}

	protected abstract void addAnimations();
	protected void addStageDependentAnimations() {
	    // empty by default.
    }

	private ActTarget getActTarget() {
		return (ActTarget) target;
	}

	hu.norbisquest.nagbase.common.game.target.Target newTarget() {
		return new ActTarget();
	}

	protected Actor(String name, Map<Integer, String> lines,
					List<DataResource> audioData) {
		this(name);
		speaker = new Speaker(name, lines, audioData);
		subLabel = new NAGLabel();
		// subLabel.addStyleName("subtitle");
		// App.getMain().add(subLabel);
	}

	public void setAudio(List<DataResource> audioData) {
		speaker.setAudioData(audioData);
	}

	public void setLineMap(TextResource res) {
		String[] txt = res.getText().split("\n");
		Map<Integer, String> lines = new HashMap<>();
		Integer i = 1;
		for (String line : txt) {
			lines.put(i, line);
			i++;
		}
		speaker.setLineMap(lines);
	}

	public void setLines(TextResource res) {
		if (res == null) {
			speaker.setLines(null);
			return;
		}
		String[] txt = res.getText().split("\n");

		speaker.setLines(Arrays.asList(txt));
	}

	Actor(ActorData res) {
		this(res.name, res.getText("HU"), res.getAudio("HU"));
		setScale(res.scale);
		if (res.lines != null) {
			setLines(res.lines);
		}
		setTextPosition(res.textX, res.textY);
		addTextStyleName(res.textStyleName);
	}

	public Speech speak(int idx) {
		return speaker.speak(idx);
	}

	/**
	 * Makes the actor speak
	 * 
	 * @param direction
	 *            The direction where actor speaks to.
	 * @return The SpeakCommand that can be added to the process queue.
	 */
	public Command sayEnum(String text, DataResource res, int direction) {
	    if (text == null) {
	        return null;
        }
		return new SpeakCommand(this, text, res, direction);

	}

	public Command sayEnum(String text, DataResource res) {
		return new SpeakCommand(this, text, res, getTalkAnimId());

	}

	/**
	 *
	 * @return the anim id what the actor talks.
	 */
	public int getTalkAnimId() {
		return talkAnimId;
	}

	/**
	 * Makes the actor speak
	 * 
	 * @param speech
	 *            The speech that actor says.
	 * @param direction
	 *            The direction where actor speaks to.
	 * @return The SpeakCommand that can be added to the process queue.
	 */
	public Command sayEnum(Speech speech, int direction) {
		return sayEnum(speech.getText(), speech.getAudio(), direction);
	}

	public Command sayEnum(Speech speech) {
		return sayEnum(speech.getText(), speech.getAudio());
	}

	public Command sayEnum(SpeechEnum s) {
		return sayEnum(speak(s.value()), 0);
	}

	public List<Command> sayEnum(SpeechEnum ...enums) {
		List<Command> list = new ArrayList<>();
		for (SpeechEnum s: enums) {
			list.add(sayEnum(s));
		}
		return list;
	}

	public void stopTalking() {
		talking = false;
		subLabel.clear();
		changeAnimation(talkBase);
		stop();
	}

	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
		if (isValid()) {
			update();
		}
	}

	public void setTextPosition(int x, int y) {
		textX = x;
		textY = y;
	}

	public void setTextColor(String color) {
		subLabel.setColor(color);
	}

	int getTextX() {
		return textX;
	}

	int getTextY() {
		return textY;
	}

	public void setX(int x) {
		posX = x;
		update();
	}

	public void setY(int y) {
		posY = y;
		update();
	}

	public void drawTo(Layer layer) {
		layer.draw(getAnimation(), posX, posY);
	}

	public void clearOn(Layer layer) {
		layer.getCanvas().getContext2d().clearRect(posX, posY, getWidth(),
				getHeight());
	}

	public hu.norbisquest.nagbase.common.game.target.Target getTarget() {
		return target;
	}

	Integer getTalkBase() {
		return talkBase;
	}

	public void setTalkBase(Integer talkBase) {
		this.talkBase = talkBase;
	}

	public boolean isTalking() {
		return talking;
	}

	public void setTalking(boolean talking) {
		this.talking = talking;
	}

	NAGLabel getSubLabel() {
		return subLabel;
	}

	public void setSubtitle(String text) {
		if (Browser.isAndroid()) {
            subLabel.setText(text);
        } else {
			subLabel.putText(text, getTextX(), getTextY());
		}

	}

	public void addTextStyleName(String style) {
		subLabel.addStyleName(style);
	}

	public void addSubtitle() {
		subLabel.addStyleName(Browser.isAndroid() ? "subtitle-touch": "subtitle");
		subLabel.getElement().getStyle().setWidth(App.getSettings().getScreenWidth(), Style.Unit.PX);
		App.getGame().add(subLabel);
	}

	private void removeSubtitle() {
		subLabel.removeFromParent();
		subLabel = null;
	}

	public void setTargetZoom(double zoom) {
		getActTarget().zoomed = true;
		getActTarget().zoom = zoom;

	}

	public void setTalkAnimId(int talkAnimId) {
		this.talkAnimId = talkAnimId;
	}

	public void setTalkAnimation(int base, int talkAnimId) {
		this.talkAnimId = talkAnimId;
		this.talkBase = base;
	}

	@Override
	public void doDestroy() {
		App.debug("DESTROYING actor " + getName());
		removeSubtitle();
		speaker.destroy();
		speaker = null;
		target.destroy();
		target = null;
		destroyPerson();
	}

	protected abstract void destroyPerson();
}