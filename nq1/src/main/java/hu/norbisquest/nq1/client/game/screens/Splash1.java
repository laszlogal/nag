package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class Splash1 extends Splash {
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Splash1(parent.getPanel()));
	}

	private Splash1(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash1(), 250, 100,
				NQ1Ids.Splash2);
		setId(NQ1Ids.Splash1);
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		super.onAvailable(isFirst);
		App.getAudioManager().loadMusicNoLoop(Common.INSTANCE.splash_music(), true);
	}

}
