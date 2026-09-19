package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

class Credits extends Splash {

	public Credits(FlowPanel parent) {
		super(parent, Common.INSTANCE.credits(), 800, 100, NQ1Ids.Actors);
		App.getAudioManager()
				.loadMusic(Common.INSTANCE.ending_music(), true);
	}

}
