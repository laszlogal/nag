package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class Splash2 extends Splash {
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Splash2(parent.getPanel()));
	}

	private Splash2(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash2(), 500, 100,
				NQ1Ids.Splash3);
		setId(NQ1Ids.Splash2);
	}

}
