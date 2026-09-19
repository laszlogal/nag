package hu.norbisquest.nqcommon.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.CommandFactory;
import hu.norbisquest.nagbase.core.NAGLabel;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.*;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.Cmd.Look;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.command.WalkCommand;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nagbase.game.walk.WalkAlgorithm;
import hu.norbisquest.nagbase.game.walk.WalkGraph;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel;
import hu.norbisquest.nagbase.game.walk.WalkPoint;
import hu.norbisquest.nqcommon.client.control.SimpleControl;

import java.util.*;

public abstract class NQStage extends Stage {

	private NAGLabel info;
	private StageModel resource;
	private HotSpot hotSpotSelected;
	private WalkGraph graph;
	private boolean drawHotSpot;
	protected NQStage() {}
	protected NQStage(StageFactory factory) {
		super(factory);

		setResource(factory.getModel());
		setId(resource.id);
		info = new NAGLabel();
		info.addStyleName("hotspotInfo");
		factory.getParent().getPanel().add(info);
	}

	public abstract class NQControl extends SimpleControl {
		private String label;
		private Look look = null;

		public NQControl() {
			setWalker(getNorbi());
		}

		public NQControl(HotSpot hotSpot) {
			super(hotSpot);
			setWalker(getNorbi());
		}

		List<Command> walkAndSay(String vertex, Look look, SpeechEnum... speeches) {
			List<Command> list = new ArrayList<>();
			list.add(walkTo(vertex));
			switch (look) {
			case FRONT:
				list.add(Hero.Cmd.turnFront());
				break;
			case LEFT:
				list.add(Hero.Cmd.turnLeft());
				break;
			case RIGHT:
				list.add(Hero.Cmd.turnRight());
				break;
			case UP:
				list.add(Hero.Cmd.turnBack());
				break;
			default:
				break;

			}

			for (SpeechEnum s : speeches) {
				list.add(Hero.say(s));
			}
			return list;

		}

		protected Command walkLabel() {
			return walkTo(label);
		}

		protected Command walkTo(String l) {
			return walk(getGraph().getVertex(l));
		}

		protected List<Command> walkAndSay(SpeechEnum... speeches) {
			if (getLook() != null && label != null) {
				return walkAndSay(label, getLook(), speeches);
			}

			List<Command> list = new ArrayList<>();
			list.add(label == null ? walkHotSpot() : walkLabel());
			list.addAll(lookAndSay(speeches));
			return list;
		}

		protected List<Command> lookAndSay(SpeechEnum... speeches) {
			List<Command> list = new ArrayList<>();
			list.add(lookHotSpot());
			for (SpeechEnum s : speeches) {
				list.add(Hero.say(s));
			}
			return list;
		}

		protected List<Command> walkAndDo(List<Command> commands) {
			List<Command> list = new ArrayList<>();
			list.add(walkLabel());
			list.addAll(commands);
			return list;
		}

		protected String getLabel() {
			return label;
		}

		protected void setLabel(String label) {
			this.label = label;
		}

		Look getLook() {
			return look;
		}

		public NQControl setLook(Look look) {
			this.look = look;
			return this;
		}

		@Override
		protected void destroyCore() {
			look = null;
			label = null;
		}
	}

	public class WalkControl extends NQControl {
		SpeechEnum[] examine = null;
		SpeechEnum[] use = null;
		private Map<HotSpot.Id, CommandFactory> inventory = new HashMap<>();

		@Override
		public List<Command> onExamine(int x, int y) {
			return walkAndSay(examine);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return walkAndSay(use);
		}

		@Override
		public List<Command> onInventory(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return inventory.get(id).get();

		}

		public WalkControl addExamine(SpeechEnum... speeches) {
			examine = speeches;
			return this;
		}

		public WalkControl addUse(SpeechEnum... speeches) {
			use = speeches;
			return this;
		}

		public WalkControl addInventory(HotSpot.Id itemId, CommandFactory cf) {
			inventory.put(itemId, cf);
			return this;
		}

		@Override
		protected void doDestroy() {
			examine = null;
			use = null;
			inventory.clear();
			inventory = null;
		}
	}

	public class LookControl extends WalkControl {
		@Override
		public List<Command> onExamine(int x, int y) {
			return lookAndSay(examine);
		}

		@Override
		public List<Command> onUse(int x, int y) {
			return lookAndSay(use);
		}
	}

	protected WalkControl createWalkControl(String label, Look look) {
		WalkControl ctrl = new WalkControl();
		ctrl.setLabel(label);
		if (look != null) {
			ctrl.setLook(look);
		}
		return ctrl;
	}

	protected LookControl createLookControl(Look look) {
		LookControl ctrl = new LookControl();
		if (look != null) {
			ctrl.setLook(look);
		}
		return ctrl;
	}

	protected LookControl createLookControl() {
		return createLookControl(null);
	}

	protected abstract Hero getNorbi();

	@Override
	public boolean load() {
		if (getResource() == null) {
			App.warn("Stage Resources NOT set!!!");
			return false;
		}

		if (getResource().backround != null) {
			setBackground(getResource().backround);
		}

		getHotSpots().setMask(getResource().hotSpotMask);

		setEventListener(this);

		setMasks(getResource().maskTop, getResource().maskBottom);

		setWalker(getNorbi());
		return true;
	}

	@Override
	public boolean unload() {
		super.unload();
		detachWalker(getNorbi());
		return true;
	}

	@Override
	protected void onAvailable(boolean isFirst) {
		StageModel res = getResource();
		disableDebug();
		super.onAvailable(isFirst);
		setWalkGraph(res.graphModel, res.graphData);
		setHotSpots(res.hotSpots);
		setStageItems(res.stageItems);
		updateActors();
		getNorbi().setRegionListener(this);
		setAudio();
		addCommand(Cmd.wrap(App.getSettings()::restoreHeroes));
        onStageReady(isFirst);
	}

	protected abstract void onStageReady(boolean isFirst);

	private void setWalkGraph(WalkGraphModel model, String data) {
		if (data != null) {
			graph = new WalkGraph(data);
			getNorbi().setAlgorithm(graph);

		} else if (model != null) {
			graph = new WalkGraph(model);
			getNorbi().setAlgorithm(graph);
		}
	}

	private void setHotSpots(List<HotSpot> hotSpots) {
		if (hotSpots != null) {
			for (HotSpot hotSpot : hotSpots) {
				addHotSpot(hotSpot);
				App.getSettings().restoreHotSpot(getId(), hotSpot);
			}
		}
	}

	private void setStageItems(List<StageItem> items) {
		for (StageItem item : items) {
			item.setScreen(this);

			App.getSettings().restoreStageItem(getId(), item);
			// item.getLayer().moveLeft(res.bgXDiff);
			item.update();
			// App.debug("DRAW ITEM MASK");
			item.putMaskTo(getHotSpots().getImage());
			getHotSpots().imageToMask();
			addHotSpot(item);

		}
	}

	private void setAudio() {
		StageModel res = getResource();
		boolean musicOn = App.getSettings().isMusicOn();

		if (!musicOn) {
			App.getAudioManager().stopMusic();
		}

		if (res.music != null) {
			App.getAudioManager().loadMusic(res.music, res.autoPlayMusic && musicOn);
		} else {
			App.getAudioManager().stopMusic();

		}

		if (res.ambientNoise != null) {
			App.getAudioManager().loadAmbientNoise(res.ambientNoise);
		} else {
			App.getAudioManager().stopAmbientNoise();
		}

	}

	private void updateActors() {
        List<ActorTarget> actors = getActors();
        if (actors == null) {
            return;
        }
        for (ActorTarget actor: actors) {
            actor.getActor().update();
        }
    }

	@Override
	public void appear() {
		App.setRootStyle("fade-in");
	}

	@Override
	public void onMouseMove(int x, int y) {
		if (!isValid()) {
			return;
		}
		selectHotSpotAt(x, y);
	}

	private void selectHotSpotAt(int x, int y) {
		Target target = getTargetAt(x, y);
		if (target == null) {
			clearHotSpot();
			return;
		}

		if (target instanceof HotSpot) {
			HotSpot hotSpot = (HotSpot) target;
			if (hotSpot != hotSpotSelected) {
				selectHotSpot(hotSpot);
			}
		}

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		gui.setJustZoomed(false);
		Touch t = event.getTargetTouches().get(0);
		CanvasElement eventCanvas = getEventLayer().getCanvas().getCanvasElement();
		int x = gui.toScaledX(t.getRelativeX(eventCanvas));
		int y = gui.toScaledY(t.getRelativeY(eventCanvas));
		selectHotSpotAt(x, y);
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		if (App.isBlocked() || gui.justZoomed()) {
			return;
		}
		Touch t = event.getTargetTouches().length() == 0
				? event.getChangedTouches().get(0) : event.getTargetTouches().get(0);
		CanvasElement eventCanvas = getEventLayer().getCanvas().getCanvasElement();
		int x = gui.toScaledX(t.getRelativeX(eventCanvas));
		int y = gui.toScaledY(t.getRelativeY(eventCanvas));
		onClick(x, y);
	}

	private void clearHotSpot() {
		hotSpotSelected = null;
		info.addStyleName("hide");
	}

	private void selectHotSpot(HotSpot hotSpot) {
		hotSpotSelected = hotSpot;
		if (!App.isBlocked()) {
			drawHotSpotName();
		}

	}

	private void drawHotSpotName() {
		if (hotSpotSelected == null) {
			return;
		}

		int textId = hotSpotSelected.getTitleId();
		if (textId != -1) {
			info.setText(getText(textId, App.getSettings().getLanguage()));
			info.removeStyleName("hide");
		} else {
			info.addStyleName("hide");
		}
	}

	protected abstract String getText(int textId, String lang);

	@Override
	public void onMouseOut(int x, int y) {
		// not implemented
	}

	@Override
	public void execute(double timestamp) {
		super.execute(timestamp);

		if (!isValid()) {
			return;
		}
		update();
//		updateNorbi();
		if (isProcessorIdle()) {
			App.getAudioManager().restoreMusicVolume();
		}
		}

	private void updateNorbi() {
		if (getNorbi().tick(App.getTimestamp())) {
			Walker norbi = getNorbi();
			if (norbi.isWalking()) {
				int middleX = App.getWidth() / 2;
				WalkPoint wp = norbi.getPosition();
				int diff = norbi.getWalkSpeed();
				double x = wp.getX() + getLeft();
				if (norbi.isFacingLeft() && getLeft() < 0 && x < middleX) {
					moveLeft(diff);
				} else if (norbi.isFacingRight() && getLeft() > App.WIDTH - getWidth() && x > middleX) {
					moveLeft(-diff);
				}
			}
		}
	}

	private void update() {
		Layer fg = getForeground();
		fg.save();
		fg.clear();
		getNorbi().drawTo(fg);
		fg.restore();
		drawDebug();
	}

	private void drawDebug() {
		Layer dbg = getDebugLayer();
		if (dbg == null) {
			return;
		}
		WalkAlgorithm wa = getNorbi().getAlgorithm();

		if (wa == null) {
			return;
		}

		wa.visualize(dbg);
		if (drawHotSpot) {
			getHotSpots().drawMask(dbg);
		}

		int x = App.getWidth() / 2;
		Context2d ctx = dbg.getContext2d();
//		ctx.fillRect(x, 0, 2, gui.getScaledHeight());

	}

	@Override
	public boolean isValid() {
		return super.isValid() && getForeground().isValid() && ((getResource().hotSpotMask == null) || getHotSpots().hasMask())
				&& (getBackground() == null || getBackground().isLoaded());
	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		App.nextMode();

	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (!App.isDebug()) {
			return;
		}

		int key = event.getNativeKeyCode();
		switch (key) {
		case KeyCodes.KEY_S:
			testSpeech();
			break;
		case KeyCodes.KEY_F:
			Browser.requestFullscreen();
			break;
			case KeyCodes.KEY_D:
				toggleDebug();
				break;
			case KeyCodes.KEY_Q:
				getListener().changeScreen(getId());
				break;
		case KeyCodes.KEY_H:
			drawHotSpot = !drawHotSpot;
			toggleDebug();
			break;
		case KeyCodes.KEY_SPACE:
			if (getNorbi().isRunning()) {
				getNorbi().stop();
			} else {
				getNorbi().start();
			}
			break;

		}

	}

	private void toggleDebug() {
		if (isDebugEnabled()) {
			disableDebug();
		} else {
			enableDebug();
			drawDebug();
		}
	}

	public StageModel getResource() {
		return resource;
	}

	private void setResource(StageModel resource) {
		this.resource = resource;
	}

	protected WalkGraph getGraph() {
		return graph;
	}

	public void setGraph(WalkGraph graph) {
		this.graph = graph;
	}

	@Override
	public void onRegionEnter(String label) {
		App.debug("[REGION] RegionEnter to " + label);
	}

	@Override
	public void onRegionLeave(String label) {

		App.debug("[REGION] RegionLeave from " + label);

	}

	@Override
	public void onRegionMove(String label) {
		App.debug("[REGION] RegionMove on " + label);

	}

	public void goToStage(String vLabel, Id id) {
		WalkPoint wp = getGraph().getVertex(vLabel);
		addCommandList(Arrays.asList(new WalkCommand(getNorbi(), (int) (wp.getX()), (int) (wp.getY()), true),
				changeScreenCmd(id)));

	}

	protected /* abstract */ NQControl getHotSpotControl(HotSpot.Id hotSpotId) {
		return null;
	}// ;

	public void addHotSpot(HotSpot hotSpot) {
		NQControl ctrl = getHotSpotControl(hotSpot.getId());
		if (ctrl != null) {
			ctrl.setHotSpot(hotSpot);
			hotSpot.setControl(ctrl);
		}
		super.addHotSpot(hotSpot);
	}

	@Override
	protected void onBlock() {
		clearHotSpot();
	}

	@Override
	protected void onUnblock() {
		drawHotSpotName();
	}

	@Override
	public void onLongPress(int x, int y) {
		zoom(x, y);
	}

    @Override
    public final void destroyResources() {
	    App.infoDestroy("NQStage resources");
        graph = null;
        resource.destroy();
        resource = null;
        info.removeFromParent();
        info = null;
    }

	@Deprecated
	public Command changeItemZIndex(final StageItem.Id id, final int zIndex) {
		return new OneShotCommand() {
			@Override
			public void start() {
				getStageItem(id).getLayer().setZIndex(zIndex);
			}
		};
	}

	public static NQStage current() {
		return (NQStage) App.getCurrentScreen();

	}

	public List<Command> fadeTo(Id stageId) {
		return Cmd.fadeOutToScreen(stageId, getListener(), false);

	}

	public void reset() {
		setLeft(getResource().bgXDiff);
	}

	protected SpeechEnum[] getAllSpeech() {
		return null;
	}

	private void testSpeech() {
		SpeechEnum[] speech = getAllSpeech();
		if (speech == null) {
			return;
		}
		for (SpeechEnum s : speech) {
			addCommand(Hero.say(s));
		}
	}

	@Override
	public void autoPilot(Settings.AutoPilot state) {
		App.debug("AutoPilot: " + state);
	}

	protected void moveRightSide() {
		moveLeft(-getResource().bgXDiff);
	}

	@Override
	protected void onHandleTarget() {
		clearHotSpot();
	}
}
