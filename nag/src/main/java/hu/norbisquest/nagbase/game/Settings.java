package hu.norbisquest.nagbase.game;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;

import java.util.*;

public abstract class Settings extends NAGObject {
	public enum AutoPilot {
		None,
		Minimal,
		Full
	}

	private Id startId;
    private String apiVersion;

	public static String getJsonKeyRegistry() {
		return JSON_KEY_REGISTRY;
	}

	private AutoPilot autoPilot = AutoPilot.None;

    public interface SettingsChanged {
		void onSettingsChanged();
	}

	public class Registry {
		private static final String JSON_KEY_BOOLS = "bools";
		private static final String JSON_KEY_NUMBERS = "numbers";
		private List<Integer> numbers = new ArrayList<>();
		private List<Boolean> bools = new ArrayList<>();

		public void addNumbers(Integer... nums) {
			for (Integer num : nums) {
				addNumber(num);
			}
		}

		public void addBooleans(Boolean... bools) {
			for (Boolean bool : bools) {
				addBoolean(bool);
			}
		}

		public Integer getNumber(int idx) {
			return numbers.get(idx);
		}

		void addNumber(Integer value) {
			numbers.add(value);
		}

		public Boolean getBoolean(int idx) {
			return bools.get(idx);
		}

		void addBoolean(Boolean value) {
			bools.add(value);
		}

		JSONObject toJSON() {
			clear();
			fillRegistry();

			App.debug("[SETTINGS] saving registry");
			JSONObject json = new JSONObject();
			JSONArray num = new JSONArray();
			JSONArray bool = new JSONArray();

			for (int i = 0; i < numbers.size(); i++) {
				Integer n = numbers.get(i);
				if (n != null) {
					App.debug("Saving num " + i + ". value: " + n);
					num.set(i, new JSONNumber(n));
				}
			}

			json.put(JSON_KEY_NUMBERS, num);

			for (int i = 0; i < bools.size(); i++) {
				Boolean b = bools.get(i);
				App.debug("Saving boolean " + i + ". value: " + b);
				bool.set(i, JSONBoolean.getInstance(b));
			}

			json.put(JSON_KEY_BOOLS, bool);

			return json;
		}

		void parseJSON(JSONObject json) {
			clear();

			App.debug("registry json: " + json);
			JSONArray num = json.isObject().get(JSON_KEY_NUMBERS).isArray();
			JSONArray bool = json.isObject().get(JSON_KEY_BOOLS).isArray();
			for (int i = 0; i < num.size(); i++) {
				addNumber((int) num.get(i).isNumber().doubleValue());
			}

			for (int i = 0; i < bool.size(); i++) {
				addBoolean(bool.get(i).isBoolean().booleanValue());
			}
			restoreFromRegistry();
		}

		void clear() {
			numbers.clear();
			bools.clear();
		}
	}

	private static final int DEFAULT_TEXT_SPEED = 180;

	public enum SpeechMode {
		Text, Audio, TextAndAudio
	}

	private Id screenId;
	private Id refererId;
	private String language;
	private Set<Id> visitedIds;
	private SpeechMode speechMode;
	private int textSpeed;
	private int screenWidth;
	private int screenHeight;
	private boolean musicOn;
	private Storage storage;
	private List<ActorTarget> actors;
	private List<Hero> heroes = new ArrayList<>();
	private Map<Id, HashMap<HotSpot.Id, Integer>> hotSpotStates;
	private Map<Id, HashMap<HotSpot.Id, Boolean>>  stageItemsVisibility;
	private Map<String, List<String>> conversationStates = new HashMap<>();
	private SettingsChanged listener;
	private boolean loaded = false;
	public static final String TAG_PREFIX = "nag.";
	private static final String JSON_KEY_CURRENT_SCREEN = "screenId";
	private static final String JSON_KEY_REFERER = "referer";
	private static final String JSON_KEY_VISITED = "visitedIds";
	private static final String JSON_KEY_HOTSPOTS = "hotspots";
	private static final String JSON_KEY_STAGEITEMS = "stageitem";
	private static final String JSON_KEY_SPEECHMODE = "speechMode";
	private static final String JSON_KEY_TEXT_SPEED = "textSpeed";
	private static final String JSON_KEY_VERSION = "version";
	private static final String JSON_KEY_INVENTORY = "inventory";
//	private static final String JSON_KEY_CUSTOM_SETTINGS = "custom";
	private static final String JSON_KEY_ACTORS = "actors";
	private static final String JSON_KEY_HEROES = "heroes";
	private static final String JSON_KEY_MUSIC = "musicOn";
	private static final String JSON_KEY_REGISTRY = "registry";
	private Registry registry;
	private boolean inventoryFill = false;
	private int animSpeed=8;
	private int moveSpeed=3;
	private int slopeSpeed=7;

	public int getAnimSpeed() {
		return animSpeed;
	}

	public void setAnimSpeed(int animSpeed) {
		this.animSpeed = animSpeed;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getSlopeSpeed() {
		return slopeSpeed;
	}

	public void setSlopeSpeed(int slopeSpeed) {
		this.slopeSpeed = slopeSpeed;
	}

	protected abstract void fillRegistry();

	protected abstract void restoreFromRegistry();

	protected Settings(SettingsChanged listener) {
		this.listener = listener;
		visitedIds = new HashSet<>();
		setSpeechMode(SpeechMode.TextAndAudio);
		textSpeed = DEFAULT_TEXT_SPEED;
		hotSpotStates = new HashMap<>();
		stageItemsVisibility = new HashMap<>();
		musicOn = true;
		storage = Storage.getLocalStorageIfSupported();
		setRegistry(new Registry());
	}

	protected void setDefaults() {
		registry.clear();
		fillRegistry();
	}

	/**
	 *
	 * @return The current screen Id the game displays
	 */
	public Id getScreenId() {
		return screenId;
	}

	public void setScreenId(Id currentScreen) {
		this.screenId = currentScreen;
	}

	/**
	 *
	 * @return The screen Id that the user came from
	 */
	public Id getReferer() {
		return refererId;
	}

	public void setReferer(Id referer) {
		this.refererId = referer;
		visit(referer);
	}

	private void visit(Id id) {
		visitedIds.add(id);
		App.debug("Screens visited: " + visitedIds);
	}

	private boolean isVisited(Id id) {
		boolean result = visitedIds.contains(id);
		App.debug(id + " visited: " + result);
		return result;
	}

	public boolean isScreenVisited() {
		return isVisited(screenId);
	}

	public SpeechMode getSpeechMode() {
		return speechMode;
	}

	public void setSpeechMode(SpeechMode mode) {
		speechMode = mode;
	}

	public Boolean hasSpeechAudio() {
		return (speechMode == SpeechMode.Audio || speechMode == SpeechMode.TextAndAudio)
		// && !App.isAndroid()
		;
	}

	public Boolean hasSpeechText() {
		return (speechMode == SpeechMode.Text || speechMode == SpeechMode.TextAndAudio);
	}

	public int getTextSpeed() {
		return textSpeed;
	}

	public void setTextSpeed(int textSpeed) {
		this.textSpeed = textSpeed;
	}

	void saveHotSpot(Id id, HotSpot hotSpot) {
		int controlIdx = hotSpot.getControlIndex();
		if (controlIdx != 0) {
			// Do not save the default value.
			HashMap<HotSpot.Id, Integer> roomMap = hotSpotStates.containsKey(id) ? hotSpotStates.get(id)
					: new HashMap<>();
			roomMap.put(hotSpot.getId(), controlIdx);
			hotSpotStates.put(id, roomMap);
			App.debug("[Hotspot] " + hotSpot + " is saved as controlIdx " + controlIdx);

		}

		if (hotSpot instanceof StageItem) {
			StageItem item = (StageItem) hotSpot;
			App.debug("[Hotspot] " + hotSpot + " is a StageItem and it is " + item.isVisible());
			HashMap<HotSpot.Id, Boolean> roomMap = stageItemsVisibility.containsKey(id) ? stageItemsVisibility.get(id)
					: new HashMap<>();
			roomMap.put(hotSpot.getId(), item.isVisible());
			stageItemsVisibility.put(id, roomMap);
			App.debug("[Hotspot] saving " + item + "ended");

		}
	}

	public void restoreHotSpot(Id id, HotSpot hotSpot) {

		HashMap<HotSpot.Id, Integer> roomMap = hotSpotStates.get(id);
		if (roomMap != null && roomMap.containsKey(hotSpot.getId())) {
			int controlIdx = roomMap.get(hotSpot.getId());
			App.debug("[Hotspot] " + hotSpot + " is restored as controlIdx " + controlIdx);
			hotSpot.setControlIndex(controlIdx);
		}
	}

	public void restoreStageItem(Id id, StageItem item) {
		App.debug("[Hotspot] Restoring stage item " + item);
		HashMap<HotSpot.Id, Boolean> roomMap = stageItemsVisibility.get(id);
		App.debug("[Hotspot] Room map " + roomMap + " id: " + id);
		if (roomMap != null && roomMap.containsKey(item.getId())) {
			boolean visible = roomMap.get(item.getId());
			App.debug("[Hotspot] " + item + " is a StageItem and restored as visible" + visible);
			item.setVisible(visible);

		}
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	protected void setScreenSize(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

	}

	public void save(String tag) {
		App.debug("[SETTINGS] saving " + tag);
		String jsonStr = toJSON().isObject().toString();

		getStorage().setItem(TAG_PREFIX + tag, jsonStr);
	}

	public void load(String tag) {
		String desc = TAG_PREFIX + tag;
		App.debug("[SETTINGS] loading " + desc);
		parseData(getStorage().getItem(desc));
		setLoaded(true);
	}

	private JSONArray visitedToJSON() {
        JSONArray array = new JSONArray();
        for (Id id : visitedIds) {
            if (id != null) {
                array.set(array.size(), new JSONString(id.toString()));
            }
        }
        return array;
    }

    private JSONObject hotSpotsToJSON() {
        JSONObject obj = new JSONObject();
        for (Id id : hotSpotStates.keySet()) {
            JSONObject stage = new JSONObject();
            HashMap<HotSpot.Id, Integer> v = hotSpotStates.get(id);
            for (HotSpot.Id hotSpotId : v.keySet()) {
                Integer state = v.get(hotSpotId);
                stage.put(hotSpotId.toString(), new JSONNumber(state));
            }
            obj.put(id.toString(), stage);
        }
        return obj;
    }

    private JSONObject stageItemsToJSON() {
        JSONObject obj = new JSONObject();

        for (Id id : stageItemsVisibility.keySet()) {
            JSONObject items = new JSONObject();
            HashMap<HotSpot.Id, Boolean> si = stageItemsVisibility.get(id);
            App.debug("[SETTINGS] map: " + si);
            if (si != null) {
                for (HotSpot.Id hotSpotId : si.keySet()) {
                    Boolean visible = si.get(hotSpotId);
                    App.debug("[SETTINGS] " + hotSpotId);
                    if (hotSpotId != null) {
                        items.put(hotSpotId.toString(), JSONBoolean.getInstance(visible));
                    }
                }

                if (id != null) {
                    obj.put(id.toString(), items);
                }
            }
        }
        return obj;
    }

    private JSONObject actorsToJSON() {
        JSONObject obj = new JSONObject();
        for (String name: conversationStates.keySet()) {
            List<String> ids = conversationStates.get(name);
            obj.put(name, Conversation.idsToJSON(ids));
        }
        return obj;
    }

    private JSONArray heroesToJSON() {
        JSONArray obj = new JSONArray();
        for (Hero hero : heroes) {
            obj.set(obj.size(), hero.toJSON());
        }
        return obj;
    }

	private JSONObject toJSON() {
		JSONObject api = new JSONObject();

		api.put(JSON_KEY_VERSION, new JSONString(App.NAG_VERSION));
		api.put(JSON_KEY_CURRENT_SCREEN, new JSONString(screenId.toString()));
		api.put(JSON_KEY_REFERER, new JSONString(refererId == null ? "" : refererId.toString()));
		api.put(JSON_KEY_VISITED, visitedToJSON());
		api.put(JSON_KEY_SPEECHMODE, new JSONString(speechMode.toString()));
		api.put(JSON_KEY_TEXT_SPEED, new JSONNumber(textSpeed));

		api.put(JSON_KEY_HOTSPOTS, hotSpotsToJSON());
		api.put(JSON_KEY_STAGEITEMS, stageItemsToJSON());
		api.put(JSON_KEY_INVENTORY, App.getInventory().toJSONValue());
		api.put(JSON_KEY_REGISTRY, getRegistry().toJSON());
		api.put(JSON_KEY_MUSIC, JSONBoolean.getInstance(musicOn));

		if (!conversationStates.isEmpty()) {
			api.put(JSON_KEY_ACTORS, actorsToJSON());
		}

		if (!heroes.isEmpty()) {
			api.put(JSON_KEY_HEROES, heroesToJSON());
		}
		return api;
	}


    private void parseCurrentScreen(JSONObject api) {
        JSONString screen = api.isObject().get(JSON_KEY_CURRENT_SCREEN).isString();
        screenId = createStageId(screen.stringValue());
    }

    private void parseReferer(JSONObject api) {
        JSONString referer = api.isObject().get(JSON_KEY_REFERER).isString();
        refererId = createStageId(referer.stringValue());
    }

    private void parseVisitedIds(JSONObject api) {
        JSONArray visited = api.isObject().get(JSON_KEY_VISITED).isArray();
        visitedIds.clear();
        for (int i = 0; i < visited.size(); i++) {
            visitedIds.add(createStageId(visited.get(i).isString().stringValue()));
        }
        visitedIds.add(getScreenId());
    }

    private void parseHotSpots(JSONObject api) {
		JSONObject hotspots = api.isObject().get(JSON_KEY_HOTSPOTS).isObject();
		if (hotspots.isObject() != null) {
			hotSpotStates.clear();
			for (String stage : hotspots.keySet()) {
				Stage.Id stageId = createStageId(stage);
				JSONObject items = hotspots.get(stage).isObject();
				HashMap<HotSpot.Id, Integer> stateMap = new HashMap<>();
				for (String name : items.keySet()) {
					HotSpot.Id hotSpotId = createHotSpotId(stageId, name);
					JSONNumber state = items.get(name).isNumber();
					Integer val = (int) state.doubleValue();
					stateMap.put(hotSpotId, val);
				}
				hotSpotStates.put(stageId, stateMap);
			}
		}
    }

    private void parseStageItems(JSONObject api) {
		JSONObject visibility = api.isObject().get(JSON_KEY_STAGEITEMS).isObject();
        if (visibility.isObject() != null) {
            stageItemsVisibility.clear();
            for (String stage : visibility.keySet()) {
                Stage.Id stageId = createStageId(stage);
                JSONObject items = visibility.get(stage).isObject();
                HashMap<HotSpot.Id, Boolean> visibleMap = new HashMap<>();
                for (String name : items.keySet()) {
                    HotSpot.Id hotSpotId = createHotSpotId(stageId, name);
                    JSONBoolean visible = items.get(name).isBoolean();
                    Boolean val = visible.booleanValue();
                    visibleMap.put(hotSpotId, val);
                }
                stageItemsVisibility.put(stageId, visibleMap);
            }
        }
    }

    private void parseActors(JSONObject api) {
		if (!api.isObject().containsKey(JSON_KEY_ACTORS)) {
			return;
		}
        JSONObject actorTargets = api.isObject().get(JSON_KEY_ACTORS).isObject();
        for (String name: actorTargets.keySet()) {
            List<String> ids = Conversation.parseIds(actorTargets.get(name).isArray());
            conversationStates.put(name, ids);
        }
        log("parsing actors" + conversationStates);
    }

    private void parseInventory(JSONObject api) {
		JSONArray inventory = api.isObject().get(JSON_KEY_INVENTORY).isArray();
        App.getInventory().parseItems(inventory);
    }

    private void parseRegistry(JSONObject api) {
        JSONObject reg = api.get(JSON_KEY_REGISTRY).isObject();
        getRegistry().parseJSON(reg);
    }

    private void parseApiVersion(JSONObject api) {
        JSONString version = api.isObject().get(JSON_KEY_VERSION).isString();
        apiVersion = version.isString().toString().replace("\"", "");

    }

    private void parseMusicSettings(JSONObject api) {
		musicOn = api.get(JSON_KEY_MUSIC).isBoolean().booleanValue();
	}

    private void parseData(String data) {
        JSONObject api = JSONParser.parseStrict(data).isObject();
        parseApiVersion(api);
		parseCurrentScreen(api);
		parseReferer(api);
        parseVisitedIds(api);
		parseHotSpots(api);
        parseStageItems(api);
		parseActors(api);
	    parseRegistry(api);
	    parseMusicSettings(api);
	    printSettingsInfo();
		listener.onSettingsChanged();

	}

	private void printSettingsInfo() {
        log("parsing done.");
        log("[Parse] version is: " + apiVersion);
        log("[Parse] screen is: " + screenId);
        log("[Parse] referer is: " + refererId);
        log("[Parse] visited ids: " + visitedIds);
        log("[Parse] hotspot states: " + hotSpotStates);
        log("[Parse] stageitem visibility: " + stageItemsVisibility);
        log("[Parse] inventory" + App.getInventory());
        log("parsing FINISHED");
    }

	protected abstract Stage.Id createStageId(String name);

	protected abstract HotSpot.Id createHotSpotId(Stage.Id id, String name);

	// protected abstract JSONObject getCustomJSON();

	// protected abstract void parseCustomJSON(JSONObject custom);

	private Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public List<ActorTarget> getActors() {
		return actors;
	}

	public void setActors(List<ActorTarget> actors) {
		this.actors = actors;
	}

	public List<String> getSavedKeys() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < storage.getLength(); i++) {
			String key = storage.key(i);
			if (key.startsWith(TAG_PREFIX)) {
				list.add(key);
			}
		}
		return list;
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isLoaded() {
		return loaded;
	}

	private void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public void addHero(Hero hero) {
		heroes.add(hero);

	}

	public Hero getMainHero() {
		if (heroes.isEmpty()) {
			App.error("NO HERO");
		}
		return heroes.get(0);

	}

	public void restoreHeroes() {
		for (Hero h : heroes) {
			h.restore();
		}
	}

	protected Registry getRegistry() {
		return registry;
	}

	private void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public boolean isInventoryFill() {
		return App.isDebug() && inventoryFill;
	}

	public void setInventoryFill(boolean inventoryFill) {
		this.inventoryFill = inventoryFill;
	}

	public Id getStartId() {
		return startId;
	}

	public void setStartId(Id startId) {
		this.startId = startId;
	}

	public void saveConversationState(String name, List<String> ids) {
		conversationStates.put(name, ids);
	}

	public List<String> getConversationState(String name) {
		return conversationStates.get(name);
	}


	AutoPilot getAutoPilot() {
		return autoPilot;
	}

	public void setAutoPilot(AutoPilot autoPilot) {
		this.autoPilot = autoPilot;
	}

	boolean hasAutoPilot() {
		return autoPilot != AutoPilot.None;
	}
}
