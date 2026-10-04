package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

public final class PoorNorbi extends Splash {
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new PoorNorbi(parent.getPanel()));
	}

	private PoorNorbi(FlowPanel parent) {
		super(parent, Common.INSTANCE.poor(), 500, 100, NQ1Ids.Room);
		setId(NQ1Ids.PoorNorbi);

	}

}
