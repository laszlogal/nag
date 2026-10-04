package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class Splash3 extends Splash {
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Splash3(parent.getPanel()));
	}

	private Splash3(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash3(), 700, -1,
				NQ1Ids.MainMenu);
		setId(NQ1Ids.Splash3);
		getBackground().setPermanent(true);
	}

}
