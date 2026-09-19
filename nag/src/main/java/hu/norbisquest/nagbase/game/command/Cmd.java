package hu.norbisquest.nagbase.game.command;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.Walker;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cmd {

	public enum Look {
		LEFT, RIGHT, UP, FRONT
	}

    private static Command walk(Walker walker, int x, int y, boolean look) {
		return new WalkCommand(walker, x, y, look);
	}

	/**
	 * Default is look at where go.
	 * 
	 * @param walker the character.
	 * @param x coordinate to go.
	 * @param y coordinate to go.
	 */
	public static Command walk(Walker walker, int x, int y) {
		return walk(walker, x, y, false);
	}

	public static Command lookAt(final Walker walker, final int x, final int y) {
		return new OneShotCommand() {

			@Override
			public void start() {
				walker.lookAt(x, y);
			}
		};
	}

	public static Command changeScreen(Id screenId, ScreenListener listener) {
		return new ChangeScreenCommand(screenId, listener);
	}

    public static List<Command> fadeOutToScreen(Id screenId, ScreenListener listener, boolean slow) {
		int waitForFadeOut = slow ? 50 : 20;
		return Arrays.asList(
				slow ? fadeOutSlow() : fadeOut(),
				pause(waitForFadeOut),
				changeScreen(screenId, listener));
	}

    public static Command pause(int frames) {
		return new Pause(frames);
	}

	public static Command playSfx(DataResource res) {
		return new SFXCommand(res);
	}

	public static Command playSfx(DataResource res, boolean wait) {
		return new SFXCommand(res, wait);
	}

	public static List<Command> asList(Command... cmds) {
		List<Command> list = new ArrayList<>();
		Collections.addAll(list, cmds);
		return list;
	}

	public static Command setItemVisible(final Stage stage, final HotSpot.Id id, final boolean visible) {
		return new OneShotCommand() {

			@Override
			public void start() {
				stage.getStageItem(id).setVisible(visible);
			}
		};
	}

	public static Command fadeIn() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.setRootStyle("fade-in");
			}
		};

	}

	private static Command fadeOut() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.setRootStyle("fade-out");
			}
		};

	}

	public static Command fadeOutSlow() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.setRootStyle("fade-out-slow");
			}
		};

	}

	public static Command setItemZIndex(final Stage stage, final StageItem.Id id, final int zIndex) {
		return new OneShotCommand() {
			@Override
			public void start() {
				stage.getStageItem(id).getLayer().setZIndex(zIndex);
			}
		};
	}

	public static Command enableHotSpot(final Stage stage, final HotSpot.Id id, final boolean enabled) {
		return new OneShotCommand() {
			@Override
			public void start() {
				stage.getHotSpot(id).setEnabled(enabled);
			}
		};
	}

	public static Command normalCursor() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.setMode(App.GameModes.NORMAL);
			}
		};

	}

	/*
	 * Damn rude, but effective :)
	 */
	public static List<Command> list(Object... objs) {
		List<Command> l = new ArrayList<>();
		for (Object o : objs) {
			if (o instanceof Command) {
				l.add((Command) o);
			} else if (o instanceof List) {
				for (Object item : (List<?>) o) {
					if (item instanceof Command) {
						l.add((Command) item);
					}
				}
			}
		}
		return l;
	}

	public static List<Command> listIf(boolean condition, Object... objs) {
		if (condition) {
			return list(objs);
		}
		return null;
	}

	public static Command resetMode() {
		return setMode(App.getDefaultMode());
	}

	private static Command setMode(final GameModes mode) {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.setMode(mode);
			}
		};
	}

	public static Command pauseAmbient() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.getAudioManager().pauseAmbientNoise();
			}
		};
	}

	public static Command pauseMusic() {
		return new OneShotCommand() {

			@Override
			public void start() {
				App.getAudioManager().pauseAmbientNoise();
			}
		};
	}

	public static Command wrap(Runnable cmd) {
		return new OneShotCommand() {

			@Override
			public void start() {
				cmd.run();			}
		};
	}
}