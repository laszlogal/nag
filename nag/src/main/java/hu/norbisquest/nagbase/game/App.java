package hu.norbisquest.nagbase.game;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.game.command.CommandFeeder;
import hu.norbisquest.nagbase.game.gui.Toolbar;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.HotSpot.Id;
import hu.norbisquest.nagbase.game.target.Inventory;

import java.util.Arrays;
import java.util.Collection;

public final class App {
	private static double timeThen;

	public static void resetMode() {
		setMode(getDefaultMode());
	}

	public static int toScaledX(int clientX) {
		return 0;
	}

	public static int toScaledY(int clientY) {
		return 0;
	}

    public static void setUserZoomEnabled(boolean b) {
    	game.setUserZoomEnabled(b);
	}


    public interface ModeListener {
		void modeChange(GameModes mode);
	}

	public enum GameModes {
		NORMAL, WALK, EXAMINE, USE, TALK, INVENTORY, NONE
	}

	private static GameModes normalMode = GameModes.WALK;
	private static NAGGame game;
	private final static String[] engines = new String[] { "", "webkit", "ms", "Moz", "O", "khtml" };

	public static int LEFT = 0;
	public static int TOP = 0;
	public static int WIDTH = 640;
	public static int HEIGHT = 400;

	static final String NAG_VERSION = "1.5.0";

	private static final double FONT_SIZE = 20;

	private static final String PILCZSTUDIOS_HOME = "http://pilczstudios.around.hu/";
	private static Cursor cursor;
	private static double timestamp;
	private static GameModes mode = GameModes.NORMAL;
	private static boolean block = false;
	private static Inventory inventory = null;
	private static AbstractDialogManager dialogManager = null;
	private static ModeListener modeListener = null;
	private static Settings settings = null;
	private static boolean suspend = false;
	private static CommandFeeder feeder;

	private static boolean modeLock = false;


	private static String debugMessage;

	/**
	 * Prints message to the JavaScript console.
	 * 
	 * @param msg
	 *            The debug message to print.
	 */

	private static native void log(String msg, String color) /*-{
		var str = '%c' + msg + 'color: ' + color + ';';
		$wnd.console.log(msg);
		str = null;
	}-*/;

	public static void debug(String msg) {
		log(msg, "#000000");
	}

	public static void warn(String msg) {
		log("[WARNING] " + msg, "yellow");
	}

	public static void error(String msg) {
		log("[ERROR] " + msg, "red");
	}

	public static void printNull(String name, Object obj) {
		debug("[NULLTEST] " + name + " IS " + (obj == null ? "NULL" : "OK"));
	}

	public static String comma(Collection<?> collection) {
		StringBuilder sb = new StringBuilder();
		for (Object o : collection) {
			sb.append(o).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static Settings getSettings() {
		return settings;
	}

	public static Cursor getCursor() {
		return cursor;
	}

	public static void setCursor(Cursor value) {
		cursor = value;
	}

	public static GameModes getMode() {
		return mode;
	}

	public static void setMode(GameModes mode) {
		setMode(mode, false);
	}

	private static void setMode(GameModes mode, boolean silent) {
		App.mode = mode == GameModes.NORMAL ? getDefaultMode() : mode;

		switch (App.mode) {
		case EXAMINE:
			cursor.examine();
			break;
		case NORMAL:
			cursor.normal();
			break;
		case TALK:
			cursor.talk();
			break;
		case USE:
			cursor.use();
			break;
		case WALK:
			cursor.walk();
			break;
		case INVENTORY:
			cursor.inventory();
			break;
		default:
			break;
		}

		if (!silent) {
			notifyModeChange();
		}

	}

	public static void notifyModeChange() {
		if (modeListener != null) {
			modeListener.modeChange(App.mode);
		}
	}
	public static void block() {
		block = true;
		cursor.busy();
		Stage stage = getCurrentStage();
		if (stage != null) {
			stage.onBlock();
		}
	}

	public static void unblock() {
		block = false;
		setMode(mode, true);
		Stage stage = getCurrentStage();
		if (stage != null) {
			stage.onUnblock();
		}
	}

	public static boolean isBlocked() {
		return block;
	}

	public static void setBlocked(boolean value) {
		block = value;
		App.debug("[BLOCK] " + value);
	}

	public static double getTimestamp() {
		return timestamp;
	}

	public static boolean isTick(int speed) {
		return timestamp % speed == 0;
	}

	public static void setTimestamp(double timestamp) {
		App.timestamp = timestamp;
	}

	public static Inventory getInventory() {
		if (inventory == null) {
			inventory = new Inventory();
		}
		return inventory;
	}

	public static boolean isSelectedItem(HotSpot.Id id) {
		return getInventory().isSelected(id);
	}

	public static AbstractDialogManager getDialogManager() {
		return dialogManager;
	}

	public static void setDialogManager(AbstractDialogManager dialogManager) {
		App.dialogManager = dialogManager;
	}

	public static ModeListener getModeListener() {
		return modeListener;
	}

	public static void setModeListener(ModeListener modeListener) {
		App.modeListener = modeListener;
	}

	public static void setSettings(Settings settings) {
		App.settings = settings;
	}

	public static void suspend() {
		suspend = true;
	}

	public static void resume() {
		suspend = false;
	}

	static boolean isSuspended() {
		return suspend;
	}

	public static void nextMode() {
		if (modeLock) {
			return;
		}
		switch (mode) {
		case EXAMINE:
			setMode(GameModes.USE);
			break;
		case INVENTORY:
			setMode(GameModes.WALK);
			break;
		case NONE:
			setMode(GameModes.WALK);
			break;
		case NORMAL:
			setMode(GameModes.EXAMINE);
			break;
		case TALK:
			if (getInventory().getSelectedItem() != null) {
				setMode(GameModes.INVENTORY);
			} else {
				setMode(GameModes.WALK);

			}
			break;
		case USE:
			setMode(GameModes.TALK);

			break;
		case WALK:
			setMode(GameModes.EXAMINE);

			break;
		default:
			break;

		}
	}

	public static CommandFeeder getFeeder() {
		return feeder;
	}

	public static void setFeeder(CommandFeeder feeder) {
		App.feeder = feeder;
	}

	public static RootPanel getMain() {
		return RootPanel.get("gui");

	}

	public static void setRootStyle(String style) {
		game.setStyleName(style);
		game.addStyleName("nagGame");
	}

	public static AudioManager getAudioManager() {
		return AudioManager.get();
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static void debugVect(String prefix, Object... vars) {
		App.debug(prefix + Arrays.toString(vars));
	}




	public static int getFontSize() {
		return (int) (FONT_SIZE);
	}


	public static void homePage() {
		Window.Location.replace(PILCZSTUDIOS_HOME);
	}

	public static void enableViewport() {
		getMain().getElement().getStyle().setOverflow(Overflow.HIDDEN);

	}

	public static void disableViewport() {
		getMain().getElement().getStyle().setOverflow(Overflow.VISIBLE);

	}

	public static void selectAndUseItem(Id id) {
		inventory.setSelectedItem(id);
		cursor.setInventory(inventory.getSelectedItem());
		setMode(GameModes.INVENTORY);
	}

	public static void lockMode() {
		modeLock = true;
	}

	public static void unlockMode() {
		modeLock = false;
	}

	public static Screen getCurrentScreen() {
		return game.getCurrentScreen();
	}

	public static void setCurrentScreen(Screen screen) {
//		if (screen == null) {
//
//			return;
//		}
//		screen.setListener(game.ge);
//		screen.load();
//		AbstractDialogManager mgr = App.getDialogManager();
//		if (mgr != null) {
//			mgr.setParent(screen);
//		}
//
//		game.setScreen(screen);
	}


	public static native void scrollTouchScreen() /*-{
		$wnd.window.addEventListener('load', function(e) {
			setTimeout(function() {
				window.scrollTo(0, 1);
			}, 1);
		}, false);
	}-*/;

	public static Stage getCurrentStage() {
		return game.getCurrentStage();
	}

	public static NAGGame getGame() {
		return game;
	}

	public static void setGame(NAGGame game) {
		App.game = game;
	}

	public static boolean isDebug() {
		return RootPanel.get("beta") != null;
		// return false;
	}

	static String getDebugMessage() {
		return debugMessage;
	}

	static void setDebugMessage(String debugMessage) {
		App.debugMessage = debugMessage;
	}

	public static void setToolbarVisible(boolean visible) {
		Toolbar toolbar = game.getToolbar();
		if (toolbar != null) {
			toolbar.setVisible(visible);
		}
	}

	public static void setToolbarEnabled(boolean value) {
		Toolbar toolbar = game.getToolbar();
		if (toolbar != null) {
			toolbar.setEnabled(value);
		}
	}

		public static GameModes getDefaultMode() {
		return normalMode;
	}

	public static void setDefaultMode(GameModes normalMode) {
		App.normalMode = normalMode;
		mode = normalMode;
	}

	public static double now() {
		return System.currentTimeMillis();
	}

	public static void markTime() {
		timeThen = System.currentTimeMillis();
	}

	public static void timeElapsed(String msg) {
		timeElapsed(msg, 0);
	}

	private static void timeElapsed(String msg, int threshold) {
		double delta = System.currentTimeMillis() - timeThen;
		if (delta > threshold) {
			App.debug(msg + " took " + delta + "ms");
		}

	}

	public static boolean has(Feature feature) {
		return feature == Feature.SLOPE_ROUTE;
	}

	public static native void printStacktrace(String message)/*-{
		if ($wnd.console && $wnd.console.trace) {
			$wnd.console.trace(message);
		}
	}-*/;

	public static void infoDestroy(String msg) {
		debug("[DESTROY] " + msg);
	}

	public static native void runOnAnimation(Runnable runnable, Element root,
											 String classname) /*-{
		var reClass = RegExp(classname);
		var callback = function() {
			root.removeEventListener("animationend", callback);
			if (root.className.match(reClass)) {
				root.className = root.className.replace(reClass, "");
				runnable.@java.lang.Runnable::run()();
			}
		};
		if ((root.style.animation || root.style.animation === "")
				&& root.className.match(reClass)) {

			root.addEventListener("animationend", callback);
			return;
		}
		$wnd.setTimeout(callback, 0);

	}-*/;


	public static void removeToolbar() {
		if (game == null || game.getToolbar() == null) {
			return;
		}
		game.getToolbar().removeFromParent();
	}
}

