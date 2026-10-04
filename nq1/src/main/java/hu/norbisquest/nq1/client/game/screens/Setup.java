package hu.norbisquest.nq1.client.game.screens;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import hu.norbisquest.nagbase.core.layer.ImageLayer;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.Settings.SpeechMode;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nagbase.game.target.SimpleTarget;
import hu.norbisquest.nq1.client.data.bundle.Common;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nqcommon.client.NQSetup;

public final class Setup extends NQSetup {
	private final FlowPanel controlsParent;
	public static void create(ScreenParent parent) {
		parent.onScreenCreated(new Setup(parent.getPanel()));
	}
	private class ImageTarget extends SimpleTarget {
		private Image img;
		private boolean valid;

		ImageTarget(final int left, final int top, ImageResource res) {
			super("", left, top, 0, 0);
			img = new Image(res);
			img.setVisible(false);
			img.getElement().getStyle().setPosition(Position.ABSOLUTE);
			img.getElement().getStyle().setZIndex(Layer.Z_BACKGROUND + 1);
			img.addLoadHandler(event -> {
                valid = true;
                img.addStyleName("imageTarget");
                img.getElement().getStyle().setLeft(left, Unit.PX);
                img.getElement().getStyle().setTop(top, Unit.PX);
                setRight(left + img.getWidth());
                setBottom(top + img.getHeight());

            });

			controlsParent.add(img);
		}

		void remove() {
			img.removeFromParent();
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		void setOn(boolean value) {
			img.setVisible(value);
		}

	}

	private Setup(FlowPanel parent) {
		super(parent, new ImageLayer(Common.INSTANCE.setup(),
				Layer.Z_BACKGROUND));
		setId(NQ1Ids.Setup);
		controlsParent = parent;
		setMainMenuId(NQ1Ids.MainMenu);
	}

	@Override
	public SimpleTarget createAudioTarget(SpeechMode mode) {
		switch (mode) {
		case Audio:
			return new ImageTarget(412, 250,
					Common.INSTANCE.setup_audio_only());
		case Text:
			return new ImageTarget(408, 129,
					Common.INSTANCE.setup_text_only());
		case TextAndAudio:
			return new ImageTarget(50, 131,
					Common.INSTANCE.setup_text_and_audio());
		default:
			return null;

		}
	}

	@Override
	public void setAudioTarget(SpeechMode mode, boolean value) {
		((ImageTarget) getAudioTarget(mode)).setOn(value);

	}

	@Override
	public SimpleTarget createBackTarget() {
		return new SimpleTarget("", 105, 260, 200, 295);
	}

	@Override
	protected void onBack() {
		setFadeTo(getMainMenuId());
	}

	@Override
	protected void doDestroy() {
		for (SpeechMode mode : SpeechMode.values()) {
			SimpleTarget target = getAudioTarget(mode);
			if (target instanceof ImageTarget) {
				((ImageTarget) target).remove();
			}
		}
		super.doDestroy();
	}

	@Override
	public void onDrop(int x, int y) {
		// TODO Auto-generated method stub

	}
}
