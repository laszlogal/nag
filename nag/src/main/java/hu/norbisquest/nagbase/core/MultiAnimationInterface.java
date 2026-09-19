package hu.norbisquest.nagbase.core;

import java.util.List;

import com.google.gwt.resources.client.ImageResource;

public interface MultiAnimationInterface extends NAGAnimationInterface {

	void addAnimation(Integer id, List<ImageResource> frames, int speed);

	void addAnimation(Integer id, ImageResource frame);

	void changeAnimation(Integer id);

	Integer getCurrentAnimationId();

	void setCurrentAnimationId(Integer currentAnimationId);

	String getName();

	void setName(String name);

	void playPause();

}
