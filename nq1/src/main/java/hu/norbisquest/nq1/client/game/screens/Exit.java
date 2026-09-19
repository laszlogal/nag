package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nqcommon.client.Splash;

class Exit extends Splash {

	Exit(FlowPanel parent) {
		super(parent, Common.INSTANCE.the_end(), 0, 100, null);
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		App.setRootStyle("fade-out");
	}

	public void onFadeOut() {

		Browser.exitApp();
	}

	public boolean load() {
		return true;
	}

}
