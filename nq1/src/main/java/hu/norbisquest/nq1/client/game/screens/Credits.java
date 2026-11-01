package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class Credits extends Splash {

	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Credits(parent.getPanel()));
	}

	private Credits(FlowPanel parent) {
		super(parent, Common.INSTANCE.credits(), 800, 100, NQ1Ids.Actors);
		setId(NQ1Ids.Credits);
		App.getAudioManager()
				.loadMusic(Common.INSTANCE.ending_music(), true);
	}

}
