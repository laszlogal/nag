package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public class TestScreen1 extends Splash {

	public TestScreen1(FlowPanel parent) {
		super(parent, Common.INSTANCE.splash1(), 250, 100,
				NQ1Ids.TestScreen);
	}

	@Override
	public void execute(double timestamp) {

	}

	@Override
	public void onClick(int x, int y) {
		getListener().changeScreen(NQ1Settings.get().getReferer());
	}


}
