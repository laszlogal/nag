package hu.norbisquest.nagbase.game;

import com.google.gwt.core.client.RunAsyncCallback;

class StageAsync implements RunAsyncCallback {
	private Stage stage;

	public StageAsync(Stage stage) {
		this.stage = stage;
	}

	public void onSuccess() {
		App.setCurrentScreen(stage);
	}

	@Override
	public void onFailure(Throwable reason) {
		App.error("Stage creation failed");
	}

}