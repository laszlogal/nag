package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface BusBack extends ClientBundle {
	BusBack INSTANCE = GWT.create(BusBack.class);

	// Text
	@Source("hu/norbisquest/nq1/resources/walk/BusBack")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S07_busback.png")
	ImageResource background();
}