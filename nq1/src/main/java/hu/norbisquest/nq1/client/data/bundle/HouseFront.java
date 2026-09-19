package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface HouseFront extends ClientBundle {
	HouseFront INSTANCE = GWT.create(HouseFront.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n192(), 
				INSTANCE.n193(), 
				INSTANCE.n194(), 
				INSTANCE.n195(), 
				INSTANCE.n196(), 
				INSTANCE.n197(), 
				INSTANCE.n198(), 
				INSTANCE.n199(), 
				INSTANCE.n200(), 
				INSTANCE.n201());
				//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		HOUSE_DOOR, DOOR_PHONE, NONSTOP, POSTER

	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N04")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/HouseFront")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S04_housefront.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S04_housefront_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S04_housefront_hotspots.png")
	ImageResource hotspots();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N192.mp3")
	DataResource n192();

	@Source("hu/norbisquest/nq1/resources/speech/N193.mp3")
	DataResource n193();

	@Source("hu/norbisquest/nq1/resources/speech/N194.mp3")
	DataResource n194();

	@Source("hu/norbisquest/nq1/resources/speech/N195.mp3")
	DataResource n195();

	@Source("hu/norbisquest/nq1/resources/speech/N196.mp3")
	DataResource n196();

	@Source("hu/norbisquest/nq1/resources/speech/N197.mp3")
	DataResource n197();

	@Source("hu/norbisquest/nq1/resources/speech/N198.mp3")
	DataResource n198();

	@Source("hu/norbisquest/nq1/resources/speech/N199.mp3")
	DataResource n199();

	@Source("hu/norbisquest/nq1/resources/speech/N200.mp3")
	DataResource n200();

	@Source("hu/norbisquest/nq1/resources/speech/N201.mp3")
	DataResource n201();

}