package hu.norbisquest.nq1.client.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.game.screens.TestScreen1;

class TestScreen {
	public static void create(final ScreenParent parent) {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				parent.onScreenCreated(new TestScreen1(parent.getPanel()));

			}

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub

			}
		});
	}
}
