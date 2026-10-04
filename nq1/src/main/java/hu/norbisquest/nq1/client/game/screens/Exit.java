package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class Exit extends Splash {
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Exit(parent.getPanel()));
	}

	private Exit(FlowPanel parent) {
		super(parent, Common.INSTANCE.the_end(), 0, 100, null);
		setId(NQ1Ids.Exit);
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
