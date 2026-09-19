package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.core.layer.EventLayerListener;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nqcommon.client.NQScreen;

abstract class NQ1Screen extends NQScreen implements EventLayerListener {

	NQ1Screen(int left, int top, int width, int height, ScreenParent parent) {
		super(left, top, width, height, parent);
	}

	NQ1Screen(int left, int top, int width, int height, FlowPanel parent)
	{
		super(left, top, width, height, parent);
	}
}
