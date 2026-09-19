package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.target.SimpleTarget;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

class MainMenu extends NQ1Screen {
	private SimpleTarget trgStart;
	private SimpleTarget trgLoad;
	private SimpleTarget trgSetup;
	private SimpleTarget trgExit;

	MainMenu(FlowPanel parent) {
		super(App.LEFT, App.TOP, App.WIDTH, App.HEIGHT, parent);
		setBackground(new ImageLayer(Common.INSTANCE.mainmenu(),
				Layer.Z_BACKGROUND));
		App.getAudioManager()
				.loadMusic(Common.INSTANCE.mainmenu_music(), true);
		setEventListener(this);
		App.getCursor().normal();
		trgStart = new SimpleTarget("", 408, 42, 518, 92);
		trgLoad = new SimpleTarget("", 428, 98, 570, 134);
		trgSetup = new SimpleTarget("", 450, 144, 610, 180);
		trgExit = new SimpleTarget("", 505, 195, 605, 235);

	}

	@Override
	public void onClick(int x, int y) {
		if (trgStart.isHit(x, y)) {
			setFadeTo(NQ1Ids.PoorNorbi);
		} else if (trgLoad.isHit(x, y)) {
			gui.getDialogManager().showLoadDialog();
		} else if (trgSetup.isHit(x, y)) {
			setFadeTo(NQ1Ids.Setup);
		} else if (trgExit.isHit(x, y)) {
			Browser.exitApp();
			//setFadeTo(NQ1Ids.Exit);
		}
	}

	@Override
	public void onLongPress(int x, int y) {
	}

	@Override
	public void onDrop(int x, int y) {
		// TODO Auto-generated method stub

	}
}
