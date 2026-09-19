package hu.norbisquest.nqcommon.client;

import com.google.gwt.user.client.ui.FlowPanel;

import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings.SpeechMode;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.target.SimpleTarget;

public abstract class NQSetup extends NQScreen {
	private SimpleTarget trgBack;
	private SimpleTarget trgTextAndAudio;
	private SimpleTarget trgAudio;
	private SimpleTarget trgText;
	private Id mainMenuId;
	protected NQSetup(FlowPanel parent, ImageLayer bgLayer) {
		super(App.LEFT, App.TOP, App.WIDTH, App.HEIGHT, parent);
		setBackground(bgLayer);
		setEventListener(this);
		App.getCursor().normal();
		createTargets();

	}

	private void createTargets() {
		trgTextAndAudio = createAudioTarget(SpeechMode.TextAndAudio);
		trgText = createAudioTarget(SpeechMode.Text);
		trgAudio = createAudioTarget(SpeechMode.Audio);
		trgBack = createBackTarget();

	}

	protected abstract SimpleTarget createAudioTarget(SpeechMode mode);
	protected abstract void setAudioTarget(SpeechMode mode, boolean value);
	protected abstract SimpleTarget createBackTarget();

	private void setTargets() {

		SpeechMode mode = App.getSettings().getSpeechMode();
		setAudioTarget(SpeechMode.TextAndAudio,
				mode == SpeechMode.TextAndAudio);
		setAudioTarget(SpeechMode.Text, mode == SpeechMode.Text);
		setAudioTarget(SpeechMode.Audio, mode == SpeechMode.Audio);

	}

	@Override
	protected void onAvailable(boolean isFirst) {
		setTargets();
		super.onAvailable(isFirst);
	}

	protected void onBack() {
		getListener().changeScreen(getMainMenuId());
	}

	@Override
	public void onClick(int x, int y) {
		if (trgBack.isHit(x, y)) {
			onBack();
		} else {
			if (trgTextAndAudio.isHit(x, y)) {
				App.debug("textiiii&audio");
				App.getSettings().setSpeechMode(SpeechMode.TextAndAudio);
			} else

				if (trgText.isHit(x, y)) {
				App.debug("text");
				App.getSettings().setSpeechMode(SpeechMode.Text);
			} else

					if (trgAudio.isHit(x, y)) {
				App.debug("audio");
				App.getSettings().setSpeechMode(SpeechMode.Audio);

			} else {
				App.debug("no hit");
			}

			setTargets();
		}
	}

	@Override
	public void onLongPress(int x, int y) {
		// TODO Auto-generated method stub

	}

	protected Id getMainMenuId() {
		return mainMenuId;
	}

	protected void setMainMenuId(Id mainMenuId) {
		this.mainMenuId = mainMenuId;
	}

	protected SimpleTarget getAudioTarget(SpeechMode mode) {
		switch (mode) {
			case Audio :
				return trgAudio;
			case Text :
				return trgText;
			case TextAndAudio :
				return trgTextAndAudio;
			default :
				return null;

		}
	}
}
