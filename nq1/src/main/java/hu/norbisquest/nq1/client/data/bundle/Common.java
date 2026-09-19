package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface Common extends ClientBundle {

	Common INSTANCE = GWT.create(Common.class);

	// Screens
	@Source("hu/norbisquest/nq1/resources/images/screens/splash1.png")
	ImageResource splash1();

	@Source("hu/norbisquest/nq1/resources/images/screens/splash2.png")
	ImageResource splash2();

	@Source("hu/norbisquest/nq1/resources/images/screens/splash3.png")
	ImageResource splash3();

	@Source("hu/norbisquest/nq1/resources/images/screens/credits.png")
	ImageResource credits();

	@Source("hu/norbisquest/nq1/resources/images/screens/szinkron.png")
	ImageResource actor_voices();

	@Source("hu/norbisquest/nq1/resources/images/screens/end.png")
	ImageResource the_end();

	@Source("hu/norbisquest/nq1/resources/images/screens/mainmenu.png")
	ImageResource mainmenu();

	@Source("hu/norbisquest/nq1/resources/images/screens/poor02.png")
	ImageResource poor();

	@Source("hu/norbisquest/nq1/resources/images/screens/setup02.png")
	ImageResource setup();

	@Source("hu/norbisquest/nq1/resources/images/screens/hangfelirat.png")
	ImageResource setup_text_and_audio();

	@Source("hu/norbisquest/nq1/resources/images/screens/hang.png")
	ImageResource setup_audio_only();

	@Source("hu/norbisquest/nq1/resources/images/screens/felirat.png")
	ImageResource setup_text_only();

	// Stages

	@Source("hu/norbisquest/nq1/resources/cursors/pointer.png")
	ImageResource cursor_normal();

	@Source("hu/norbisquest/nq1/resources/cursors/wait.png")
	ImageResource cursor_busy();

	@Source("hu/norbisquest/nq1/resources/cursors/look1.png")
	ImageResource cursor_examine();

	@Source("hu/norbisquest/nq1/resources/cursors/use_1.png")
	ImageResource cursor_use();

	@Source("hu/norbisquest/nq1/resources/cursors/talk.png")
	ImageResource cursor_talk();

	@Source("hu/norbisquest/nq1/resources/music/music1.mp3")
    DataResource splash_music();

	@Source("hu/norbisquest/nq1/resources/music/music2.mp3")
    DataResource warren_music();

	@Source("hu/norbisquest/nq1/resources/music/music3.mp3")
    DataResource mainmenu_music();

	@Source("hu/norbisquest/nq1/resources/music/music4.mp3")
    DataResource rockklub_music();

	@Source("hu/norbisquest/nq1/resources/music/music5.mp3")
    DataResource ending_music();

	@Source("hu/norbisquest/nq1/resources/sound/sound1.mp3")
    DataResource knob_sfx();

	@Source("hu/norbisquest/nq1/resources/sound/sound9.mp3")
    DataResource walk_floor1();

	@Source("hu/norbisquest/nq1/resources/sound/sound10.mp3")
    DataResource walk_floor2();

	@Source("hu/norbisquest/nq1/resources/sound/sound6.mp3")
    DataResource take_sfx();

	@Source("hu/norbisquest/nq1/resources/sound/sound11.mp3")
    DataResource suburb_noise();

	@Source("hu/norbisquest/nq1/resources/sound/sound20.mp3")
    DataResource rockklub_noise();
}
