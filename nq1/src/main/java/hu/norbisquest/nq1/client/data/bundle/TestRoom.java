package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface TestRoom extends ClientBundle {
	TestRoom INSTANCE = GWT.create(TestRoom.class);

	// text
	@Source("hu/norbisquest/nq1/resources/walk/TestRoom")
	TextResource graph();

	// images

}