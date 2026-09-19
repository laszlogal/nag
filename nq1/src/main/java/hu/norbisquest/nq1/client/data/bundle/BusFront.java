package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface BusFront extends ClientBundle {
	BusFront INSTANCE = GWT.create(BusFront.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n20(), 
				INSTANCE.n21(), 
				INSTANCE.n22(), 
				INSTANCE.n23(), 
				INSTANCE.n24(), 
				INSTANCE.n303());
				//@formatter:on

		public static List<DataResource> busDriver = Arrays.asList(
				//@formatter:off
				INSTANCE.b1(), 
				INSTANCE.b2(), 
				INSTANCE.b3(), 
				INSTANCE.b4(), 
				INSTANCE.b5());
				//@formatter:on

		public static List<ImageResource> bus_driver_talk = Arrays.asList(
				INSTANCE.bus_driver(),
				INSTANCE.bus_driver_talk());
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N07")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/text/hu/BusDriver")
	TextResource busDriverLines();

	@Source("hu/norbisquest/nq1/resources/walk/BusFront")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S07_busfront.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S07_busfront_mask.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/sofor/sofor.png")
	ImageResource bus_driver();

	@Source("hu/norbisquest/nq1/resources/images/sofor/sofortalk.png")
	ImageResource bus_driver_talk();

	@Source("hu/norbisquest/nq1/resources/images/sofor/mask.png")
	ImageResource bus_driver_mask();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N20.mp3")
	DataResource n20();

	@Source("hu/norbisquest/nq1/resources/speech/N21.mp3")
	DataResource n21();

	@Source("hu/norbisquest/nq1/resources/speech/N22.mp3")
	DataResource n22();

	@Source("hu/norbisquest/nq1/resources/speech/N23.mp3")
	DataResource n23();

	@Source("hu/norbisquest/nq1/resources/speech/N24.mp3")
	DataResource n24();

	@Source("hu/norbisquest/nq1/resources/speech/N303.mp3")
	DataResource n303();

	@Source("hu/norbisquest/nq1/resources/speech/S1.mp3")
	DataResource b1();

	@Source("hu/norbisquest/nq1/resources/speech/S2.mp3")
	DataResource b2();

	@Source("hu/norbisquest/nq1/resources/speech/S3.mp3")
	DataResource b3();

	@Source("hu/norbisquest/nq1/resources/speech/S4.mp3")
	DataResource b4();

	@Source("hu/norbisquest/nq1/resources/speech/S5.mp3")
	DataResource b5();

	@Source("hu/norbisquest/nq1/resources/sound/sound14.mp3")
    DataResource use_ticket_sfx();
}