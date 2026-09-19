package hu.norbisquest.nagbase.core;

import java.util.List;

import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.resources.client.ImageResource;

public interface NAGAnimationInterface extends Loadable, Tickable {
	interface IAnimationListener {
		/**
		 * 
		 * @return true if animation should stop
		 */
		boolean onAnimationEnd(NAGAnimationInterface animation);

		void onLoop(NAGAnimationInterface animation);
	}

	void addFrame(ImageResource res);

	List<NAGImage> getFrames();

	boolean isLoaded();

	void start();

	void stop();

	void pause();

	void resume();

	void update();

	CanvasElement getCurrentFrame();

	CanvasElement getFrame(int index);

	void reset();

	boolean isRunning();

	double getAnimationSpeed();

	void setAnimationSpeed(double speed);

	boolean isVisible();

	void setVisible(boolean visible);

	boolean isLoop();

	void setLoop(boolean loop);

	IAnimationListener getAnimListener();

	void setAnimListener(IAnimationListener listener);

	int getFrameIdx();

	void setFrameIdx(int frameIdx);

	boolean isValid();

	double getScale();

	void setScale(double scale);

	double getWidth();

	double getHeight();
}
