package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.factory.NQ1StageFactory;

public class NQ1ScreenFactory {

	public static void createScreen(final NQ1Ids id, final ScreenParent p) {
		final FlowPanel parent = p.getPanel();
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				switch (id) {
				// Screens
				case Splash1:
					break;
				case Splash2:
					break;
				case Splash3:
					break;
				case MainMenu:
					break;
				case PoorNorbi:
					break;
				case Credits:
					break;
				case Actors:
					break;
				case End:
					break;
				case Setup:
					break;
				case Exit:
					break;

				default:
					App.debug("Creating stage");
					NQ1StageFactory.createStage(id, p);
				}
			}

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub

			}
		});
	}
}
