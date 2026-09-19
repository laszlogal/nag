package hu.norbisquest.nq1.client.game.stages;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.core.IAudioChannel;
import hu.norbisquest.nagbase.core.NAGAnimation;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nq1.client.data.bundle.PlayBass;
import hu.norbisquest.nq1.client.data.bundle.RehearsalRoom;
import hu.norbisquest.nq1.client.factory.NQ1Ids;
import hu.norbisquest.nq1.client.game.actors.Norbi;

import java.util.Arrays;
import java.util.List;

public class S11PlayBass extends NQ1Stage {

	private static final int PLAY_X = 360;
	private static final int PLAY_Y = 240;
	private static final double BOOM_X = 300;
	private static final double BOOM_Y = 70;
	private NAGAnimation boom;
	private Layer boomLayer;


	public S11PlayBass(StageFactory factory) {
		super(factory);
		boom = new NAGAnimation(PlayBass.Data.boom);
		boom.setLoop(false);
		boomLayer = new Layer();
		boomLayer.setZIndex(Layer.Z_FOREGROUND2);
		setLayer("boom", boomLayer);
	}

	@Override
	public boolean isValid() {
		return super.isValid() && boom.isValid();
	}

	@Override
	protected void destroyStage() {
		boom.destroy();
		boomLayer.destroy();
	}

	@Override
	public void onStageReady(boolean isFirst) {
 		App.setUserZoomEnabled(false);
		App.removeToolbar();
		Norbi.get().setPosition(PLAY_X, PLAY_Y);
		getForeground().setMaskEnable(false);
		addCommandList(play());
	}

	@Override
	public void execute(double timestamp) {
		if (boom.isRunning() && boom.tick(timestamp)) {
			boomLayer.clear();
			boomLayer.draw(boom, BOOM_X, BOOM_Y);

		}
		super.execute(timestamp);
	}

	private List<Command> play() {
		final IAudioChannel wire1 = AudioManager.createAudioChannel();
		final IAudioChannel wire2 = AudioManager.createAudioChannel();
		final IAudioChannel wire3 = AudioManager.createAudioChannel();
		final IAudioChannel wire4 = AudioManager.createAudioChannel();
		final IAudioChannel shout = AudioManager.createAudioChannel();
		wire1.load(PlayBass.INSTANCE.wire1());
		wire2.load(PlayBass.INSTANCE.wire2());
		wire3.load(PlayBass.INSTANCE.wire3());
		wire4.load(PlayBass.INSTANCE.wire4());
		shout.load(PlayBass.INSTANCE.shout());

		return (Arrays.asList(Norbi.Cmd.face(Norbi.PLAY_BASS),
				Cmd.playSfx(PlayBass.INSTANCE.play_bass(), false),
				new OneShotCommand() {

					@Override
					public void start() {
						Norbi.get().setLoop(false);
						Norbi.get().start();
					}

					@Override
					public boolean isBlocker() {
						return true;
					}

					@Override
					public void process() {
						int idx = Norbi.get().getFrameIdx();
						IAudioChannel sfx = null;
						switch (idx) {
						case 19:
						case 25:
							sfx = wire1;
							break;
						case 29:
							sfx = wire2;
							break;
						case 35:
							sfx = wire3;
							break;
						case 47:
							sfx = shout;
							break;
						case 81:
							sfx = wire4;
							break;

						}
						if (sfx != null) {
							sfx.play(false);

						}
					}

					@Override
					public boolean hasFinished() {
						return !Norbi.get().isRunning();
					}
				},
				Cmd.pause(50),
				Norbi.Cmd.face(Norbi.BOOM),
				Cmd.playSfx(PlayBass.INSTANCE.boom(), false),
				Cmd.setItemVisible(S11PlayBass.this, RehearsalRoom.HotSpots.MARSAL, false),
				Cmd.setItemVisible(S11PlayBass.this, RehearsalRoom.HotSpots.MARSAL_BURNED, true),
				new OneShotCommand() {

					@Override
					public void start() {
						boom.start();
					}

					@Override
					public boolean hasFinished() {
						if (boom.isRunning()) {
							return false;
						} else {
							boomLayer.clear();
							return true;
						}
					}

				},
				Cmd.pause(100),
				Norbi.Cmd.face(Norbi.BURN),
				new OneShotCommand() {

					@Override
					public void start() {
						Norbi.get().setLoop(false);
						Norbi.get().start();
					}

					@Override
					public boolean hasFinished() {
						if (Norbi.get().isRunning()) {
							return false;
						}
						Norbi.get().setVisible(false);
						return true;
					}
				},
				Cmd.playSfx(PlayBass.INSTANCE.ash_sfx(), true),
				Cmd.fadeOutSlow(),
				Cmd.pause(100),
				Cmd.changeScreen(NQ1Ids.Credits, getListener())));
	}
}
