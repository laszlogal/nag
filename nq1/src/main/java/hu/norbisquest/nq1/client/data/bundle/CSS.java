package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

import java.util.Arrays;
import java.util.List;

public interface CSS extends ClientBundle {

	CSS INSTANCE = GWT.create(CSS.class);
	class Data {
		public static List<TextResource> all = Arrays.asList(
				INSTANCE.actorStyle(),
				INSTANCE.buttonStyle(),
				INSTANCE.conversationStyle(),
				INSTANCE.inventoryStyle(),
				INSTANCE.mainStyle(),
				INSTANCE.burgerMenuStyle(),
				INSTANCE.toolbarStyle(),
				INSTANCE.popupStyle()
		);
	}

	@Source("hu/norbisquest/nq1/client/data/css/actors.css")
	TextResource actorStyle();

	@Source("hu/norbisquest/nq1/client/data/css/buttonpanel.css")
	TextResource buttonStyle();


	@Source("hu/norbisquest/nq1/client/data/css/conversation.css")
	TextResource conversationStyle();

	@Source("hu/norbisquest/nq1/client/data/css/inventory.css")
	TextResource inventoryStyle();

	@Source("hu/norbisquest/nq1/client/data/css/main.css")
	TextResource mainStyle();

	@Source("hu/norbisquest/nq1/client/data/css/burgermenu.css")
	TextResource burgerMenuStyle();

	@Source("hu/norbisquest/nq1/client/data/css/toolbar.css")
	TextResource toolbarStyle();

	@Source("hu/norbisquest/nq1/client/data/css/popups.css")
	TextResource popupStyle();
}
