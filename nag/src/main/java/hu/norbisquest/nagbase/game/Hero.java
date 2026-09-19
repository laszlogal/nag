package hu.norbisquest.nagbase.game;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.core.IAudioChannel;
import hu.norbisquest.nagbase.game.command.*;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.InventoryItem;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nagbase.game.walk.WalkPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hero extends Walker {
	public static class Cmd {
		public static Command putTo(final String label) {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().putTo(label);
				}
			};
		}

		public static Command start() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().start();
				}
			};
		}

		public static Command show() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().setVisible(true);
				}
			};
		}

		public static Command hide() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().setVisible(false);
				}
			};
		}

		public static Command setFreePosition(int x, int y, double scale) {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().setScale(scale);
					Hero.getInstance().setFreePosition(x,y);
				}
			};
		}

		public static Command runOnce() {
			return new ConditionCommand() {

				private boolean restoreLoop;

				@Override
				public void start() {
					restoreLoop = Hero.getInstance().isLoop();
					Hero.getInstance().setLoop(false);
					Hero.getInstance().start();

				}

				@Override
				public boolean isBlocker() {
					return true;
				}

				@Override
				public void process() {
					// unused
				}

				@Override
				public boolean hasFinished() {
					if (Hero.getInstance().isRunning()) {
						return false;
					}

					Hero.getInstance().setLoop(restoreLoop);
					return true;
				}

			};
		}

		public static Command face(final Integer animId) {
			return changeAnimation(animId, false);

		}

		public static Command turnLeft() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().turnLeft();
				}
			};

		}

		public static Command turnRight() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().turnRight();
				}
			};

		}

		public static Command turnFront() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().turnFront();
				}
			};

		}

		public static Command turnBack() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().turnBack();
				}
			};

		}

		static Command changeAnimation(final Integer animId, final boolean start) {
			return new Command() {
				boolean finished=false;
				@Override
				public void start() {

				}

				@Override
				public boolean isBlocker() {
					return true;
				}

				@Override
				public void process() {
					if (Hero.getInstance().changeAnimation(animId)) {
						if (start) {
							Hero.getInstance().start();
						}
						finished = true;
					}
				}

				@Override
				public boolean hasFinished() {
					return finished;
				}

				@Override
				public void setCondition(boolean condition) {

				}

				@Override
				public boolean getCondition() {
					return true;
				}
			};
		}

		public static Command stop() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().stop();
				}
			};
		}

		public static Command restoreState() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().restoreAnimState();
				}
			};
		}

		public static Command saveState() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Hero.getInstance().saveAnimState();
				}
			};
		}

		public static Command take(Stage stage, HotSpot.Id stageItemId, HotSpot.Id inventoryItemId) {
			return new TakeCommand(stage != null ? stage.getStageItem(stageItemId)
					: null,
					App.getInventory().getFactory().createItem(inventoryItemId));
		}

		public static Command loose(HotSpot.Id itemId) {
			return new LooseCommand(itemId);
		}

	}

	private class RestoreData {
		int x;
		int y;
		int face;
	}

	public interface Role {
		String getName();
	}

    private DataResource dsWalk1 = null;
	private DataResource dsWalk2 = null;
	private IAudioChannel sfxWalk1;
	private IAudioChannel sfxWalk2;

	private int idxSfxWalk = 0;
	private RestoreData restoreData = null;

	protected static Hero INSTANCE = null;

	private Hero(String name, Map<Integer, String> textLines, List<DataResource> audioData, int startPhase) {
		super(name, textLines, audioData);
		super.changeAnimation(startPhase);
		start();

	}

	protected Hero(ActorData res) {
		super(res);
	}

	protected static Hero getInstance() {
		return INSTANCE;
	}

	public static void createHero(String name, Map<Integer, String> textLines, List<DataResource> audioData,
			int startPhase) {
		if (INSTANCE == null) {
			INSTANCE = new Hero(name, textLines, audioData, startPhase);
		}
	}

	@Override
	public void start() {
		super.start();
		if (isWalking() && dsWalk1 != null) {
			idxSfxWalk = 1;
			sfxWalk1.load(dsWalk1);
		}
	}

	@Override
	public void stop() {
		reset();
		super.stop();
	}

	public void nextAnimation() {
		Integer id = getCurrentAnimationId();
		if (id == WALK_RIGHT) {
			id = WALK_FRONT;
		} else {
			id++;
		}

		changeAnimation(id);
	}

	protected void createWalkSfx() {
		sfxWalk1 = AudioManager.createAudioChannel();
		sfxWalk2 = AudioManager.createAudioChannel();
		sfxWalk1.setLoop(true);
		sfxWalk2.setLoop(true);
	}
	private static List<Command> saySimple(int speechIdx, int direction) {
		List<Command> list = new ArrayList<>();
		list.add(INSTANCE.sayEnum(INSTANCE.speak(speechIdx), direction));
		return list;
	}

	public static List<Command> saySimple(SpeechEnum s, int direction) {
		return saySimple(s.value(), direction);
	}

	public static List<Command> say(String text) {
		List<Command> list = new ArrayList<>();
		list.add(INSTANCE.sayEnum(text, null, DEFAULT_DIRECTION));
		return list;
	}

	@Deprecated
	public static List<Command> sayDefault(int speechIdx) {
		return saySimple(speechIdx, INSTANCE.getTalkDirection());
	}

	public static List<Command> saySimple(SpeechEnum s) {
		return sayAll(s);
	}

	@Deprecated
	public static List<Command> sayAll(Integer... indexes) {
		List<Command> list = new ArrayList<>();
		for (Integer idx : indexes) {
			list.add(say(idx));

		}
		return list;
	}

	public static List<Command> sayAll(SpeechEnum... speeches) {
		List<Command> list = new ArrayList<>();
		for (SpeechEnum s : speeches) {
			list.add(say(s));

		}
		return list;
	}

	public static List<Command> sayAll(List<SpeechEnum> speeches, int direction) {
		List<Command> list = new ArrayList<>();
		for (SpeechEnum s : speeches) {
			list.add(say(s.value(), direction));
		}
		return list;
	}

	private static SpeakCommand say(int speechIdx) {
		return (SpeakCommand) INSTANCE.sayEnum(INSTANCE.speak(speechIdx), INSTANCE.getTalkDirection());
	}

	public static SpeakCommand say(SpeechEnum s) {
		App.setDebugMessage(s + " " + s.value());
		return say(s.value());
	}

	private static SpeakCommand say(int speechIdx, int direction) {
		return (SpeakCommand) INSTANCE.sayEnum(INSTANCE.speak(speechIdx), direction);
	}

	public static SpeakCommand say(SpeechEnum s, int direction) {
		return say(s.value(), direction);
	}


	@Deprecated
	public static Command take(StageItem stageItem, InventoryItem item) {
		return new TakeCommand(stageItem, item);
	}

	@Deprecated
	public static Command loose(hu.norbisquest.nagbase.game.target.HotSpot.Id itemId) {
		return new LooseCommand(itemId);
	}

	@Deprecated
	public static Command animateCmd(final int duration) {
		return new TimeoutCommand() {

			@Override
			public void start() {
				super.start();
				setTimeout(duration);
				Hero.INSTANCE.start();
			}

			@Override
			public void setCondition(boolean condition) {
				// unused
			}

			@Override
			public boolean isBlocker() {
				return true;
			}

			@Override
			public boolean getCondition() {
				return true;
			}
		};
	}

	public static Command walk(int x, int y) {
		return new WalkCommand(INSTANCE, x, y, true);
	}

	public static Command walk(WalkPoint wp) {
		return new WalkCommand(INSTANCE, (int) wp.getX(), (int) wp.getY(), false);
	}

	public void setWalkSfx(DataResource walk1, DataResource walk2) {
		dsWalk1 = walk1;
		dsWalk2 = walk2;
	}

	private void loadWalkSound() {
		if (idxSfxWalk == 0) {
			sfxWalk1.load(dsWalk1);
		} else {
			sfxWalk2.load(dsWalk2);

		}
		idxSfxWalk = 1 - idxSfxWalk;

	}

	@Override
	protected void processRoute() {

		App.markTime();
		WalkPoint last = getPosition();
		WalkPoint current = getRoute().getPointAt(1);

		super.processRoute();

		if (current == null) {
			return;
		}

		double dx = last.getX() - current.getX();
		double dy = last.getY() - current.getY();
		// App.debug("dx: " + dx + "dy: " + dy);

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx < 0) {
				turnRight();
			} else {
				turnLeft();
			}
		} else {
			if (last.getY() < current.getY()) {
				turnFront();
			} else {
				turnBack();
			}
		}
	}


	public Command cmdSetTalkBase(final int phase) {
		return new OneShotCommand() {

			@Override
			public void start() {
				setTalkBase(phase);
			}
		};
	}

	@Override
	public boolean isValid() {
		return super.isLoaded();
	}

	private static String JSON_KEY_FACE = "face";
	private static String JSON_KEY_X = "x";
	private static String JSON_KEY_Y = "y";

	JSONObject toJSON() {
		JSONObject api = new JSONObject();
		api.put(JSON_KEY_FACE, new JSONNumber(getCurrentAnimationId()));
		api.put(JSON_KEY_X, new JSONNumber(getPosition().getX()));
		api.put(JSON_KEY_Y, new JSONNumber(getPosition().getY()));
		return api;
	}

	public void parseJSON(JSONObject data) {
		restoreData = new RestoreData();
		restoreData.face = (int) data.get(JSON_KEY_FACE).isNumber().doubleValue();
		restoreData.x = (int) data.get(JSON_KEY_X).isNumber().doubleValue();
		restoreData.y = (int) data.get(JSON_KEY_Y).isNumber().doubleValue();
	}

	void restore() {
		if (restoreData == null) {
			return;
		}
		changeAnimation(restoreData.face);
		setPosition(restoreData.x, restoreData.y);
		restoreData = null;
	}

	public boolean isRestored() {
		return restoreData != null;
	}

	@Override
	protected void addAnimations() {
	}


	@Override
	public void destroyPerson() {
		dsWalk1 = null;
		dsWalk2 = null;
		sfxWalk1.destroy();
		sfxWalk2.destroy();
		sfxWalk1 = null;
		sfxWalk2 = null;
		App.infoDestroy("Hero");
	}
}
