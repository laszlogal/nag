package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface Corridor extends ClientBundle {
	Corridor INSTANCE = GWT.create(Corridor.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n147(), 
				INSTANCE.n148(), 
				INSTANCE.n149(), 
				INSTANCE.n150(), 
				INSTANCE.n151(), 
				INSTANCE.n152(), 
				INSTANCE.n153(), 
				INSTANCE.n154(), 
				INSTANCE.n155(), 
				INSTANCE.n156(), 
				INSTANCE.n157(), 
				INSTANCE.n158(), 
				INSTANCE.n159(), 
				INSTANCE.n160(), 
				INSTANCE.n161(), 
				INSTANCE.n162(), 
				INSTANCE.n163(), 
				INSTANCE.n164()); 
	
				//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		WARDROBE, SHOES, FRONT_DOOR, DOOR_PHONE, KEYS, SWITCH, BATHROOM_DOOR, TOILET_DOOR

	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N02")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/Corridor")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S02_corridor.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S02_corridor_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S02_corridor_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/speech/N147.mp3")
	DataResource n147();

	@Source("hu/norbisquest/nq1/resources/speech/N148.mp3")
	DataResource n148();

	@Source("hu/norbisquest/nq1/resources/speech/N149.mp3")
	DataResource n149();

	@Source("hu/norbisquest/nq1/resources/speech/N150.mp3")
	DataResource n150();

	@Source("hu/norbisquest/nq1/resources/speech/N151.mp3")
	DataResource n151();

	@Source("hu/norbisquest/nq1/resources/speech/N152.mp3")
	DataResource n152();

	@Source("hu/norbisquest/nq1/resources/speech/N153.mp3")
	DataResource n153();

	@Source("hu/norbisquest/nq1/resources/speech/N154.mp3")
	DataResource n154();

	@Source("hu/norbisquest/nq1/resources/speech/N155.mp3")
	DataResource n155();

	@Source("hu/norbisquest/nq1/resources/speech/N156.mp3")
	DataResource n156();

	@Source("hu/norbisquest/nq1/resources/speech/N157.mp3")
	DataResource n157();

	@Source("hu/norbisquest/nq1/resources/speech/N158.mp3")
	DataResource n158();

	@Source("hu/norbisquest/nq1/resources/speech/N159.mp3")
	DataResource n159();

	@Source("hu/norbisquest/nq1/resources/speech/N160.mp3")
	DataResource n160();

	@Source("hu/norbisquest/nq1/resources/speech/N161.mp3")
	DataResource n161();

	@Source("hu/norbisquest/nq1/resources/speech/N162.mp3")
	DataResource n162();

	@Source("hu/norbisquest/nq1/resources/speech/N163.mp3")
	DataResource n163();

	@Source("hu/norbisquest/nq1/resources/speech/N164.mp3")
	DataResource n164();

}