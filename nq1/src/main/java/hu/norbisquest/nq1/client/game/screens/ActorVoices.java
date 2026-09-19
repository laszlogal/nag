package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

class ActorVoices extends Splash {

	public ActorVoices(FlowPanel parent) {
		super(parent, Common.INSTANCE.actor_voices(), 500, 100,
				NQ1Ids.End);
		App.getAudioManager()
				.loadMusic(Common.INSTANCE.ending_music(), true);
	}

}
