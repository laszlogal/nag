package hu.norbisquest.nagbase.core;

import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

public class NAGAnimation extends NAGCanvas implements Loadable, Tickable, IAnimation {
	private static final int OFFSET_MARGIN = 2;
	private int unloadIdx;
	private double tickCount=0;

	public interface IAnimationListener {
		void onAnimationEnd(NAGAnimation animation);

		void onLoop(NAGAnimation animation);
	}

	private static final double DEFAULT_SPEED = 4;
	private List<NAGImage> frames;
	private int frameCount;
	private int maxFrame;
	private int frameIdx;
	private double speed;
	private boolean started;
	private boolean updated;
	private boolean beforePaused;
	private boolean loop;
	// size of the biggest frame
	private double maxWidth;
	private double maxHeight;
	private Point topMiddle;
	private boolean visible;
	private boolean skipFirst = false;
	private IAnimationListener animListener;

	public class Point {
		private double x;
		private double y;

		void setPoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
	}


	NAGAnimation() {
		frames = new ArrayList<>();
		frameCount = 0;
		setFrameIdx(0);
		maxWidth = 0;
		maxHeight = 0;
		maxFrame = -1;
		speed = DEFAULT_SPEED;
		started = false;
		updated = false;
		visible = true;
		loop = true;
		topMiddle = new Point();
		animListener = null;
	}

	public NAGAnimation(List<ImageResource> resources) {
		this();
		resoucesToFrames(resources);
	}

	NAGAnimation(ImageResource resource) {
		this();
		addFrame(resource);
		maxFrame = 1;
		frameCount = 0;
	}

	void resoucesToFrames(List<ImageResource> resources) {
		maxFrame = resources.size();
		frameCount = 0;
		for (ImageResource res : resources) {
			// log("Adding resource " + res + " valid: " + isValid());
			addFrame(res);
		}
	}

	private void addFrame(ImageResource res) {
		final NAGImage image = new NAGImage(res);
		image.addLoadHandler(event -> {
            frames.add(image);
            frameCount++;
            if (image.getWidth() > maxWidth) {
                maxWidth = image.getWidth();
            }

            if (image.getHeight() > maxHeight) {
                maxHeight = image.getHeight();
            }

            if (isLoaded()) {
                onAnimationLoad();
            }
        });
		image.load();
	}

	private void onAnimationLoad() {
		CanvasElement frame = getCurrentFrame();
		log("Animation loaded: size (" + frame.getWidth() + ", " + frame.getHeight() + ")");
		adjustSize();
		update();

	}

	@Override
	public List<NAGImage> getFrames() {
		return frames;
	}

	@Override
	public void setScale(double value) {
		if (getScale() == value) {
			return;
		}
		super.setScale(value);

		CanvasElement frame0 = getFrame(0);
		if (frame0 != null) {
			adjustSize();
			update();

		}
	}

	private void adjustSize() {
		log("MAXSIZE (" + maxWidth + ", " + maxHeight + ")");
		int scaledWidth = (int) Math.round(maxWidth * getScale());
		int scaledHeight = (int) Math.round(maxHeight * getScale());
		log(" scale: " + getScale() + "(" + scaledWidth + ", " + scaledHeight + ")");
		setSize(scaledWidth, scaledHeight + 2);
	}

	@Override
	public boolean isLoaded() {
		return (frameCount == maxFrame);
	}

	@Override
	public boolean isValid() {
		return isLoaded();

	}

	@Override
	public void start() {
		started = isLoaded();
	}

	@Override
	public void stop() {
		reset();
		update();
		started = false;
		if (animListener != null) {
			animListener.onAnimationEnd(this);
		}

	}

	@Override
	public void pause() {
		beforePaused = started;
		stop();
	}

	@Override
	public void resume() {
		started = beforePaused;
	}

	@Override
	public void update() {
		if (!isValid()) {
			return;
		}

		if (!isVisible()) {
			clear();
			updated = true;
			return;
		}

		CanvasElement frame = getCurrentFrame();
		if (frame != null) {
			clear();
			// Align to the bottom.
			// Don't ask about OFFSET_MARGIN :D
			double offsetY = getHeight() - (frame.getHeight() * getScale()) - OFFSET_MARGIN;
			draw(frame, 0, offsetY);
			topMiddle.setPoint(getWidth() / 2, offsetY);
			updated = true;
		}

	}

	@Override
	public CanvasElement getCurrentFrame() {
		return getFrame(getFrameIdx());
	}

	private String getCurrentFrameUrl() {
		return frames.get(frameIdx).getUrl();
	}

	private CanvasElement getFrame(int index) {
		CanvasElement frame = null;

		if (index < maxFrame && index < frames.size()) {
			frame = frames.get(index).asCanvasElement();
		}

		return frame;
	}

	@Override
	public boolean tick(double timestamp) {
		if (!isRunning()) {
			boolean result = updated;
			updated = false;
			return result;
		}

		if (getAnimationSpeed() == tickCount) {
			tickCount=0;
			animate();
			return true;
		}
		tickCount++;
		return false;
	}

	private void animate() {
		update();
		if (getFrameIdx() < maxFrame - 1) {
			setFrameIdx(getFrameIdx() + 1);
		} else {
			setFrameIdx(skipFirst ? 1 : 0);
			if (!loop) {
				stop();
			} else if (animListener != null) {
				animListener.onLoop(this);
			}
		}

	}

	@Override
	public boolean unload() {
		if (unloadIdx == -1) {
			unloadIdx = 0;
		}

		if (unloadIdx < frames.size()) {
			App.debug("ANIM unloading frame " + unloadIdx);
			frames.get(unloadIdx).unload();
			unloadIdx++;
			if (unloadIdx < frames.size()) {
				return false;
			}
		}
		frameCount = 0;
		frames.clear();
		setFrameIdx(0);
		maxWidth = 0;
		maxHeight = 0;
		maxFrame = -1;
		unloadIdx=-1;
		return true;
	}

	@Override
	public void reset() {
		setFrameIdx(0);
		tickCount=0;
		update();
	}

	@Override
	public boolean isRunning() {
		return started;
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += frames.size() + " frames. Started: " + started + "Current frame is " + getFrameIdx();
		return result;
	}

	@Override
	public boolean load() {
		return false;
	}

	@Override
	public double getAnimationSpeed() {
		return speed;
	}

	@Override
	public void setAnimationSpeed(double speed) {
		this.speed = speed;
		log("Animation speed is " + speed);
	}

	@Override
	public Point getTopMiddle() {
		return topMiddle;
	}

	private boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		update();
	}

	@Override
	public boolean isLoop() {
		return loop;
	}

	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	@Override
	public String getPrefix() {
		return "[NAGAnimation]";
	}

	public IAnimationListener getAnimListener() {
		return animListener;
	}

	public void setAnimListener(IAnimationListener listener) {
		this.animListener = listener;
	}

	@Override
	public int  getFrameIdx() {
		return frameIdx;
	}

	private void setFrameIdx(int frameIdx) {
		this.frameIdx = frameIdx;
	}

	protected boolean hasUpdated() {
		return updated;
	}

	@Override
	public boolean isSkipFirst() {
		return skipFirst;
	}

	void setSkipFirst(boolean skipFirst) {
		this.skipFirst = skipFirst;
	}

	@Override
	public void cleanup() {
		animListener = null;
	}

	@Override
	public void doDestroy() {
		int i = 0;
		for (NAGImage frame: frames) {
			App.debug(getPrefix() + " DESTROYING frame  " + i);
			frame.destroy();
			i++;
		}
		frames.clear();
        animListener = null;
		super.doDestroy();
	}
}