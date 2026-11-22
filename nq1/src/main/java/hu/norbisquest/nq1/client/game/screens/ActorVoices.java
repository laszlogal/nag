package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class ActorVoices extends Splash {

	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new ActorVoices(parent.getPanel()));
	}

	private ActorVoices(FlowPanel parent) {
		super(parent, Common.INSTANCE.actor_voices(), 500, 100,
				NQ1Ids.End);
		setId(NQ1Ids.Actors);
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		super.onAvailable(isFirst);
		getAudioManager()
				.loadMusic(Common.INSTANCE.ending_music(), true);
	}

}
