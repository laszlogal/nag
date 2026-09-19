package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface BusStop extends ClientBundle {
	BusStop INSTANCE = GWT.create(BusStop.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
		//@formatter:off
				INSTANCE.n233(), 
				INSTANCE.n234(), 
				INSTANCE.n235(), 
				INSTANCE.n236(), 
				INSTANCE.n237(), 
				INSTANCE.n238(), 
				INSTANCE.n239(), 
				INSTANCE.n240(), 
				INSTANCE.n241(), 
				INSTANCE.n242(), 
				INSTANCE.n243(), 
				INSTANCE.n244(), 
				INSTANCE.n245());
				//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		SEAT, TRASH, TIMETABLE, BALL, BUS, FRONT_DOOR, BACK_DOOR
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N06")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/BusStop")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S06_busstop.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S06_busstop_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S06_busstop_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/stages/S06_busstop_bus_mask.png")
	ImageResource bus_mask();

	@Source("hu/norbisquest/nq1/resources/images/items/troli1newmegy.png")
	ImageResource bus_going();

	@Source("hu/norbisquest/nq1/resources/images/items/troli2newmegy.png")
	ImageResource bus_coming_back();

	@Source("hu/norbisquest/nq1/resources/images/items/ajtoelso.png")
	ImageResource bus_front_door();

	@Source("hu/norbisquest/nq1/resources/images/items/ajtohatso.png")
	ImageResource bus_back_door();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N233.mp3")
	DataResource n233();

	@Source("hu/norbisquest/nq1/resources/speech/N234.mp3")
	DataResource n234();

	@Source("hu/norbisquest/nq1/resources/speech/N235.mp3")
	DataResource n235();

	@Source("hu/norbisquest/nq1/resources/speech/N236.mp3")
	DataResource n236();

	@Source("hu/norbisquest/nq1/resources/speech/N237.mp3")
	DataResource n237();

	@Source("hu/norbisquest/nq1/resources/speech/N238.mp3")
	DataResource n238();

	@Source("hu/norbisquest/nq1/resources/speech/N239.mp3")
	DataResource n239();

	@Source("hu/norbisquest/nq1/resources/speech/N240.mp3")
	DataResource n240();

	@Source("hu/norbisquest/nq1/resources/speech/N241.mp3")
	DataResource n241();

	@Source("hu/norbisquest/nq1/resources/speech/N242.mp3")
	DataResource n242();

	@Source("hu/norbisquest/nq1/resources/speech/N243.mp3")
	DataResource n243();

	@Source("hu/norbisquest/nq1/resources/speech/N244.mp3")
	DataResource n244();

	@Source("hu/norbisquest/nq1/resources/speech/N245.mp3")
	DataResource n245();

	@Source("hu/norbisquest/nq1/resources/sound/sound17.mp3")
    DataResource bus_arrive_sfx();

	@Source("hu/norbisquest/nq1/resources/sound/sound16.mp3")
    DataResource bus_leave_sfx();

}