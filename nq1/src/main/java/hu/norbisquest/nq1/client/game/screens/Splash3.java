package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

class Splash3 extends Splash {

	public Splash3(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash3(), 700, -1,
				NQ1Ids.MainMenu);
		getBackground().setPermanent(true);
	}

}
