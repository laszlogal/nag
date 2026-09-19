package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

class Splash1 extends Splash {

	Splash1(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash1(), 250, 100,
				NQ1Ids.Splash2);
		App.getAudioManager()
				.loadMusicNoLoop(Common.INSTANCE.splash_music(), true);
	}

}
