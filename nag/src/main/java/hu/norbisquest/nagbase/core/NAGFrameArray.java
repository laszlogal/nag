package hu.norbisquest.nagbase.core;

import java.util.ArrayList;
import java.util.List;

import hu.norbisquest.nagbase.game.App;

import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.resources.client.ImageResource;

public abstract class NAGFrameArray {
	private List<CanvasElement> frames;
	private int frameCount;
	private int maxFrame;
	
	private NAGFrameArray() {
		frames = new ArrayList<>();
		frameCount = 0;
	}
	
	public NAGFrameArray(List<ImageResource> resources) {
		this();
		maxFrame = resources.size();
		App.debug("maxFrame " + maxFrame + "frameCount " + frameCount);
		for (ImageResource res: resources) {
			addFrame(res);
		}
	}
	
	private void addFrame(ImageResource res) {
		final NAGImage image = new NAGImage(res);
		image.addLoadHandler(event -> {
            frames.add(image.asCanvasElement());
            frameCount++;
            if (isLoaded()) {
                onFramesLoad();
            }
        });
		image.load();
	}

	private boolean isLoaded() {
		return frames.size() == maxFrame;
	}

	public CanvasElement get(int index) {
		CanvasElement frame = null;
		
		if (index < frames.size()) {
			frame = frames.get(index);
		}
		
		return frame;
	}

	protected abstract void onFramesLoad();

	public void clear() {
		frames.clear();
	}
 }
