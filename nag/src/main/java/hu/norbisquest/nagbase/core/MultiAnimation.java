package hu.norbisquest.nagbase.core;

import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiAnimation extends NAGObject implements IAnimation {
	private static final String PREFIX = "[MULTIANIM]  ";
	private Map<Integer, AnimDescriptor> frameMap;
	private Map<Integer, NAGAnimation> preloaded;
	private String name;
	private Integer currentAnimationId;
	private NAGAnimation anim=null;
	private NAGAnimation tmpAnim=null;
	private double scale;

	@Override
	public List<NAGImage> getFrames() {
		return anim.getFrames();
	}

	@Override
	public void setScale(double value) {
		scale = value;
		if (anim != null) {
			anim.setScale(scale);
		}
	}

	@Override
	public boolean isLoaded() {
		return anim != null && anim.isLoaded() && isPreloaded();
	}

	private boolean isPreloaded() {
		if (preloaded.isEmpty()) {
			return true;
		}
		boolean loaded = true;
		for (NAGAnimation a: preloaded.values()) {
			loaded = loaded && a != null && a.isLoaded();
		}
		return loaded;
	}

	@Override
	public boolean isValid() {
		return super.isValid() && isPreloaded() && anim != null && anim.isValid();
	}

	@Override
	public void start() {
		anim.start();
	}

	@Override
	public void stop() {
		anim.stop();
	}

	@Override
	public void pause() {
		anim.pause();
	}

	@Override
	public void resume() {
		anim.resume();
	}

	@Override
	public void update() {
		if (!isValid()) {
			return;
		}

		anim.update();
	}

	@Override
	public CanvasElement getCurrentFrame() {
		if (!isValid()) {
			return null;
		}
		return anim.getCurrentFrame();
	}

	@Override
	public boolean tick(double timestamp) {
		return anim.tick(timestamp);
	}

	@Override
	public boolean unload() {
		cleanup();
		return anim != null && anim.unload();
	}

	@Override
	public void reset() {
		anim.reset();
	}

	@Override
	public boolean isRunning() {
		return anim.isRunning();
	}

	@Override
	public boolean load() {
		return anim.load();
	}

	@Override
	public double getAnimationSpeed() {
		return anim.getAnimationSpeed();
	}

	@Override
	public void setAnimationSpeed(double speed) {
		if (anim == null) {
			return;
		}
		anim.setAnimationSpeed(speed);
	}

	@Override
	public NAGAnimation.Point getTopMiddle() {
		return anim.getTopMiddle();
	}

	@Override
	public void setVisible(boolean visible) {
		anim.setVisible(visible);
		for (NAGAnimation a : preloaded.values()) {
			a.setVisible(visible);
		}
	}

	@Override
	public boolean isLoop() {
		return anim.isLoop();
	}

	@Override
	public void setLoop(boolean loop) {
		anim.setLoop(loop);
	}

	@Override
	public int getFrameIdx() {
		return anim.getFrameIdx();
	}

	@Override
	public boolean isSkipFirst() {
		return false;
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}

	private class AnimDescriptor {
		List<ImageResource> frames;
		int speed;
		boolean skipFirst;

		AnimDescriptor(List<ImageResource> frames, int speed, boolean skipFirst) {
			this.frames = frames;
			this.speed = speed;
			this.skipFirst = skipFirst;
		}
	}

	protected MultiAnimation(String name) {
		super();
		this.setName(name);
		frameMap = new HashMap<>();
		preloaded = new HashMap<>();
		currentAnimationId = null;
	}

	public void addAnimationPreloaded(Integer id, List<ImageResource> frames, int speed) {
		addAnimationPreloaded(id, frames, speed, false);
	}

	@Override
	public double getWidth() {
		return anim.getWidth();
	}

	@Override
	public double getHeight() {
		return anim.getHeight();
	}

	@Override
	public void cleanup() {

	}

	protected void addAnimationPreloaded(Integer id, List<ImageResource> frames, int speed, boolean skipFirst) {
		NAGAnimation a = new NAGAnimation(frames);
		a.setAnimationSpeed(speed);
		a.setScale(scale);
		a.setSkipFirst(skipFirst);
		preloaded.put(id, a);
	}


	public void addAnimationPreloaded(Integer id, List<ImageResource> frames) {
		NAGAnimation a = new NAGAnimation(frames);
		a.setScale(scale);
		preloaded.put(id, a);
	}

	public void addAnimationPreloaded(Integer id, ImageResource frame) {
		NAGAnimation a = new NAGAnimation(frame);
		a.setScale(scale);
		preloaded.put(id, a);
	}

	protected void addAnimation(Integer id, List<ImageResource> frames, int speed) {
		addAnimation(id, frames, speed, false);
	}

	private void addAnimation(Integer id, List<ImageResource> frames, int speed, boolean skipFirst) {
		AnimDescriptor animDesc = new AnimDescriptor(frames, speed, skipFirst);
		frameMap.put(id, animDesc);
	}

	protected void addAnimation(Integer id, ImageResource frame) {
		List<ImageResource> frames = new ArrayList<>();
		frames.add(frame);
		frameMap.put(id, new AnimDescriptor(frames, 0, false));
	}

	protected boolean setAnimationId(Integer id) {
		if (!preloaded.containsKey(id)) {
			return false;
		}

		currentAnimationId = id;
		anim = preloaded.get(id);
		return true;
	}

	public boolean changeAnimation(Integer id) {
		if (currentAnimationId != null && currentAnimationId.equals(id)) {
			return true;
		}

        boolean running = anim != null && anim.isRunning();
        if (setAnimationId(id)) {
			anim.setScale(scale);
			anim.reset();
			if (running) {
				anim.start();
			} else {
				anim.stop();
			}
			anim.update();
			return true;
		}


		if (!frameMap.containsKey(id)) {
			App.debug(PREFIX + "No such anim for " + getName() + ": " + id);
			return true;
		}
		App.markTime();
		if (!unload()) {
			return false;
		}
		if (tmpAnim == null) {
			tmpAnim = new NAGAnimation();
		}
		anim = tmpAnim;
		currentAnimationId = id;

		App.debug(PREFIX + "Changing animation to " + id);
		AnimDescriptor desc = frameMap.get(id);
		App.markTime();
		anim.resoucesToFrames(desc.frames);
		App.timeElapsed("resourcesToFrames");
		anim.setAnimationSpeed(desc.speed);
		anim.setSkipFirst(desc.skipFirst);
		return true;
	}

	@Override
	public String toString() {
		String result = super.toString() + "\n";
		result += "\tNumber of animations: " + frameMap.size() + "\n";
		return result;
	}

	public Integer getCurrentAnimationId() {
		return currentAnimationId;
	}

	public void setCurrentAnimationId(Integer currentAnimationId) {
		this.currentAnimationId = currentAnimationId;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public void playPause() {
		if (isRunning()) {
			stop();
		} else {
			start();
		}
	}

	protected NAGAnimation getAnimation() {
		return anim;
	}

	protected ImageData getImageData() {
		return anim.getImageData();
	}

	@Override
	public void doDestroy() {
		frameMap.clear();
		frameMap = null;
		if (preloaded != null) {
			int i = 0;
			for (NAGAnimation a : preloaded.values()) {
				App.debug("destroying anim " + i);
				a.destroy();
				i++;
			}
		}
		if (anim != null) {
			anim.destroy();
			anim = null;
		}
		if (preloaded != null) {
			preloaded.clear();
		}
		preloaded = null;
		tmpAnim = null;
	}
}
