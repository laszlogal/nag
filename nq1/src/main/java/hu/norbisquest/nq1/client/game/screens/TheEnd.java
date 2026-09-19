package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;

import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.Splash;

class TheEnd extends Splash {

	public TheEnd(FlowPanel parent) {
		super(parent, Common.INSTANCE.the_end(), 500, 100, NQ1Ids.Exit);
	}

}
