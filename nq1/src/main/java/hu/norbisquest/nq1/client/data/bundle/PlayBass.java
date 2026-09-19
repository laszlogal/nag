package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface PlayBass extends ClientBundle {
	PlayBass INSTANCE = GWT.create(PlayBass.class);

	class Data {
		public static List<ImageResource> boom = Arrays.asList(
				INSTANCE.boom1(),
				INSTANCE.boom2(),
				INSTANCE.boom3(),
				INSTANCE.boom5(),
				INSTANCE.boom6(),
				INSTANCE.boom7(),
				INSTANCE.boom8(),
				INSTANCE.boom9(),
				INSTANCE.boom10(),
				INSTANCE.boom11(),
				INSTANCE.boom12(),
				INSTANCE.boom13(),
				INSTANCE.boom14(),
				INSTANCE.boom15(),
				INSTANCE.boom16()

		);

	}
	// text

	// images
	@Source("hu/norbisquest/nq1/resources/images/boom/1.png")
	ImageResource boom1();

	@Source("hu/norbisquest/nq1/resources/images/boom/2.png")
	ImageResource boom2();

	@Source("hu/norbisquest/nq1/resources/images/boom/3.png")
	ImageResource boom3();

	@Source("hu/norbisquest/nq1/resources/images/boom/4.png")
	ImageResource boom4();

	@Source("hu/norbisquest/nq1/resources/images/boom/5.png")
	ImageResource boom5();

	@Source("hu/norbisquest/nq1/resources/images/boom/6.png")
	ImageResource boom6();

	@Source("hu/norbisquest/nq1/resources/images/boom/7.png")
	ImageResource boom7();

	@Source("hu/norbisquest/nq1/resources/images/boom/8.png")
	ImageResource boom8();

	@Source("hu/norbisquest/nq1/resources/images/boom/9.png")
	ImageResource boom9();

	@Source("hu/norbisquest/nq1/resources/images/boom/10.png")
	ImageResource boom10();

	@Source("hu/norbisquest/nq1/resources/images/boom/11.png")
	ImageResource boom11();

	@Source("hu/norbisquest/nq1/resources/images/boom/12.png")
	ImageResource boom12();

	@Source("hu/norbisquest/nq1/resources/images/boom/13.png")
	ImageResource boom13();

	@Source("hu/norbisquest/nq1/resources/images/boom/14.png")
	ImageResource boom14();

	@Source("hu/norbisquest/nq1/resources/images/boom/15.png")
	ImageResource boom15();

	@Source("hu/norbisquest/nq1/resources/images/boom/16.png")
	ImageResource boom16();

	@Source("hu/norbisquest/nq1/resources/images/items/marsal2egett.png")
	ImageResource marsal_burned();

	// audio
	@Source("hu/norbisquest/nq1/resources/sound/sound19.mp3")
	DataResource play_bass();

	@Source("hu/norbisquest/nq1/resources/sound/sound22.mp3")
	DataResource wire1();

	@Source("hu/norbisquest/nq1/resources/sound/sound23.mp3")
	DataResource wire2();

	@Source("hu/norbisquest/nq1/resources/sound/sound24.mp3")
	DataResource wire3();

	@Source("hu/norbisquest/nq1/resources/sound/sound32.mp3")
	DataResource shout();

	@Source("hu/norbisquest/nq1/resources/sound/sound26.mp3")
	DataResource wire4();

	@Source("hu/norbisquest/nq1/resources/sound/sound25.mp3")
	DataResource boom();

	@Source("hu/norbisquest/nq1/resources/sound/sound29.mp3")
	DataResource ash_sfx();

}