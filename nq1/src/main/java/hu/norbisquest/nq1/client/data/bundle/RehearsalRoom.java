package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface RehearsalRoom extends ClientBundle {
	RehearsalRoom INSTANCE = GWT.create(RehearsalRoom.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
		//@formatter:off
				INSTANCE.n109(), 
				INSTANCE.n110(), 
				INSTANCE.n111(), 
				INSTANCE.n112(), 
				INSTANCE.n113(), 
				INSTANCE.n114(), 
				INSTANCE.n115(), 
				INSTANCE.n116(), 
				INSTANCE.n117(), 
				INSTANCE.n118(), 
				INSTANCE.n119(), 
				INSTANCE.n120(), 
				INSTANCE.n121(), 
				INSTANCE.n122(), 
				INSTANCE.n123(), 
				INSTANCE.n124(), 
				INSTANCE.n125(), 
				INSTANCE.n126(), 
				INSTANCE.n127(), 
				INSTANCE.n128(), 
				INSTANCE.n129(), 
				INSTANCE.n130(), 
				INSTANCE.n131(), 
				INSTANCE.n132(), 
				INSTANCE.n133(), 
				INSTANCE.n134(), 
				INSTANCE.n135(), 
				INSTANCE.n136(), 
				INSTANCE.n137(), 
				INSTANCE.n138(), 
				INSTANCE.n139(), 
				INSTANCE.n140(), 
				INSTANCE.n141(), 
				INSTANCE.n142(), 
				INSTANCE.n143(), 
				INSTANCE.n144(), 
				INSTANCE.n145(), 
				INSTANCE.n146() 

				);
				//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		SOCKET, SWITCH, TRASH, RUBBISH, GLASS, KNIFE, COAT, MARSAL, MARSAL_BURNED
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N10")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/RehearsalRoom")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S10_fitting_room.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S10_fitting_room_mask_top.png")
	ImageResource mask_bottom();

	@Source("hu/norbisquest/nq1/resources/images/stages/S10_fitting_room_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S10_fitting_room_mask_marshall.png")
	ImageResource mask_marshall();

	@Source("hu/norbisquest/nq1/resources/images/stages/S10_fitting_room_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/items/marsal2.png")
	ImageResource marsal();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N109.mp3")
	DataResource n109();

	@Source("hu/norbisquest/nq1/resources/speech/N110.mp3")
	DataResource n110();

	@Source("hu/norbisquest/nq1/resources/speech/N111.mp3")
	DataResource n111();

	@Source("hu/norbisquest/nq1/resources/speech/N112.mp3")
	DataResource n112();

	@Source("hu/norbisquest/nq1/resources/speech/N113.mp3")
	DataResource n113();

	@Source("hu/norbisquest/nq1/resources/speech/N114.mp3")
	DataResource n114();

	@Source("hu/norbisquest/nq1/resources/speech/N115.mp3")
	DataResource n115();

	@Source("hu/norbisquest/nq1/resources/speech/N116.mp3")
	DataResource n116();

	@Source("hu/norbisquest/nq1/resources/speech/N117.mp3")
	DataResource n117();

	@Source("hu/norbisquest/nq1/resources/speech/N118.mp3")
	DataResource n118();

	@Source("hu/norbisquest/nq1/resources/speech/N119.mp3")
	DataResource n119();

	@Source("hu/norbisquest/nq1/resources/speech/N120.mp3")
	DataResource n120();

	@Source("hu/norbisquest/nq1/resources/speech/N121.mp3")
	DataResource n121();

	@Source("hu/norbisquest/nq1/resources/speech/N122.mp3")
	DataResource n122();

	@Source("hu/norbisquest/nq1/resources/speech/N123.mp3")
	DataResource n123();

	@Source("hu/norbisquest/nq1/resources/speech/N124.mp3")
	DataResource n124();

	@Source("hu/norbisquest/nq1/resources/speech/N125.mp3")
	DataResource n125();

	@Source("hu/norbisquest/nq1/resources/speech/N126.mp3")
	DataResource n126();

	@Source("hu/norbisquest/nq1/resources/speech/N127.mp3")
	DataResource n127();

	@Source("hu/norbisquest/nq1/resources/speech/N128.mp3")
	DataResource n128();

	@Source("hu/norbisquest/nq1/resources/speech/N129.mp3")
	DataResource n129();

	@Source("hu/norbisquest/nq1/resources/speech/N130.mp3")
	DataResource n130();

	@Source("hu/norbisquest/nq1/resources/speech/N131.mp3")
	DataResource n131();

	@Source("hu/norbisquest/nq1/resources/speech/N132.mp3")
	DataResource n132();

	@Source("hu/norbisquest/nq1/resources/speech/N133.mp3")
	DataResource n133();

	@Source("hu/norbisquest/nq1/resources/speech/N134.mp3")
	DataResource n134();

	@Source("hu/norbisquest/nq1/resources/speech/N135.mp3")
	DataResource n135();

	@Source("hu/norbisquest/nq1/resources/speech/N136.mp3")
	DataResource n136();

	@Source("hu/norbisquest/nq1/resources/speech/N137.mp3")
	DataResource n137();

	@Source("hu/norbisquest/nq1/resources/speech/N138.mp3")
	DataResource n138();

	@Source("hu/norbisquest/nq1/resources/speech/N139.mp3")
	DataResource n139();

	@Source("hu/norbisquest/nq1/resources/speech/N140.mp3")
	DataResource n140();

	@Source("hu/norbisquest/nq1/resources/speech/N141.mp3")
	DataResource n141();

	@Source("hu/norbisquest/nq1/resources/speech/N142.mp3")
	DataResource n142();

	@Source("hu/norbisquest/nq1/resources/speech/N143.mp3")
	DataResource n143();

	@Source("hu/norbisquest/nq1/resources/speech/N144.mp3")
	DataResource n144();

	@Source("hu/norbisquest/nq1/resources/speech/N145.mp3")
	DataResource n145();

	@Source("hu/norbisquest/nq1/resources/speech/N146.mp3")
	DataResource n146();

}