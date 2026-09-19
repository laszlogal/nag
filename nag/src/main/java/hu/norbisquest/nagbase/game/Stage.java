package hu.norbisquest.nagbase.game;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.CommandProcessor;
import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.common.game.target.HotSpotContainer;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.layer.EventLayerListener;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.core.layer.MaskedLayer;
import hu.norbisquest.nagbase.game.Walker.RegionListener;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.CommandFeeder;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.ConversationView;
import hu.norbisquest.nagbase.game.gui.Toolbar.ToolbarListener;
import hu.norbisquest.nagbase.game.target.*;

import java.util.List;

public abstract class Stage extends Screen
		implements EventLayerListener, CommandFeeder, RegionListener, ToolbarListener {

	public interface Id {
		Id toId(String name);
	}

    private MaskedLayer foreground;
	private List<Target> targets;
	private HotSpotContainer hotSpots;
	private CommandProcessor processor;
	private Walker walker;
	private List<Actor> actors;
	private List<ActorTarget> actorTargets;
	protected Stage() {}

    protected Stage(StageFactory factory) {
		super(factory.getDimension(), factory.getParent());
		foreground = new MaskedLayer(Layer.Z_FOREGROUND1);
		setLayer("fg", foreground);
		processor = factory.createProcessor();
		hotSpots = factory.createHotSpotContainer(getId());
		targets = factory.createEmptyTargets();
		actors = factory.createEmptyActors();
		actorTargets = getActors();
	}
	public void setProcessor(CommandProcessor processor) {
		this.processor = processor;
	}

	protected List<ActorTarget> getActors() {
		return null;
	}

	protected HotSpotContainer getHotSpots() {return hotSpots;}

	protected void setMasks(ImageResource topRes, ImageResource bottomRes) {
		foreground.setTopMask(topRes);
		foreground.setBottomMask(bottomRes);
		foreground.load();

	}

	protected void setWalker(Walker walker) {
		this.walker = walker;

	}

	protected void detachWalker(Walker walker) {
		walker.setAlgorithm(null);
	}

	protected MaskedLayer getForeground() {
		return foreground;
	}

	public void setForeground(MaskedLayer foreground) {
		this.foreground = foreground;
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		// not used
	}

	public void addTarget(Target target) {
		targets.add(target);
	}

	public void addTarget(int idx, Target target) {
		targets.add(idx, target);
	}

	public void addHotSpot(HotSpot hotSpot) {
		hotSpots.add(hotSpot);
		addTarget(hotSpot);
	}

	public HotSpot getHotSpot(HotSpot.Id id) {
		return hotSpots.get(id);
	}

	public StageItem getStageItem(HotSpot.Id id) {
		HotSpot item = getHotSpot(id);
		if (item instanceof StageItem) {
			return (StageItem) item;
		}
		return null;
	}

	public void enableHotSpot(HotSpot.Id id) {
		getHotSpot(id).setEnabled(true);
	}

	public void disableHotSpot(HotSpot.Id id) {
		getHotSpot(id).setEnabled(false);
	}

	public void enableAllHotSpots() {
		hotSpots.enableAll();
	}

	public void disableAllHotSpots() {
		hotSpots.disableAll();
	}

	protected Target getTargetAt(int x, int y) {
		Hits hits = new Hits();
		for (Target t : targets) {
			if (t.isHit(x, y)) {
				hits.add(t);
			}
		}
		return hits.isEmpty() ? null : hits.getTopHit();
	}

	public void addCommand(Command cmd) {
		processor.addCommand(cmd);
	}

	protected void addCommands(Command... cmds) {
		processor.addCommands(cmds);
	}

	public void addCommandList(List<Command> cmdList) {
		if (cmdList == null) {
			App.printStacktrace("null command list");
			return;
		}
		processor.addCommandList(cmdList);
	}

	public void clearCommands() {
		processor.clearCommands();
	}

	protected boolean isProcessorIdle() {
		return processor.isIdle();
	}
	@Override
	public void execute(double timestamp) {
		if (!isValid()) {
			return;
		}

		drawActors(timestamp);

		drawActorTargets(timestamp);

		if (App.isSuspended()) {
			getEventLayer().setEnabled(false);
			return;
		}

		getEventLayer().setEnabled(true);

		processCommands();
	}

	private void drawActors(double timestamp) {
		if (actors == null) {
			return;
		}

		for (Actor actor : actors) {
			if (actor.tick(timestamp)) {
				actor.drawTo(foreground);
			}
		}
	}

	private void drawActorTargets(double timestamp) {
		if (actorTargets == null) {
			return;
		}

		for (ActorTarget at : actorTargets) {
			if (at.tick(timestamp)) {
				at.draw();
			}
		}
	}

	private void processCommands() {
		if (processor.process()) {
			App.block();
		} else {

			App.unblock();
		}
		if (Browser.isAndroid() && processor.isIdle()) {
			gui.getToolbar().setVisible(true);
		}

	}

	protected abstract void onBlock();

	protected abstract void onUnblock();

	@Override
	public void onClick(int x, int y) {
		App.debug("click");
		if (gui.hideMenu()) {
			return;
		}
		if (!handleTargetAt(x, y)) {
			addCommand(Cmd.walk(walker, x, y));
		}

	}

	@Override
	public void onDrop(int x, int y) {
		App.debug("drop to stage: (" + x + ", " + y + ")");
		handleTargetAt(x, y);

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {

	}

	protected abstract void onHandleTarget();

	private boolean handleTargetAt(int x, int y) {
		Target target = getTargetAt(x, y);
		if (target == null) {
			return false;
		} else {
			App.debug("TARGET IS: " + target);
		}

		onHandleTarget();

		TargetControl ctrl = target.getControl();

		if (ctrl == null) {

			App.warn("[STAGE] target " + target + " has no control yet");
			return false;
		}

		List<Command> cmds = null;
		switch (App.getMode()) {
		case EXAMINE:
			cmds = ctrl.onExamine(x, y);
			break;
		case TALK:

		case USE:
			if (ctrl instanceof TalkControl) {
				gui.zoomOut();
				cmds = ((TalkControl) ctrl).onTalk(x, y);

			} else {
				gui.zoomOut();
				cmds = ctrl.onUse(x, y);
			}
			break;
		case NORMAL:
		case WALK:
			cmds = ctrl.onWalk(x, y);
			break;
		case INVENTORY:
			gui.zoomOut();
			cmds = ctrl.onInventory(App.getInventory().getSelectedItem().getId());
			break;
		default:
			break;

		}

		if (cmds != null) {
			App.debug("append commands");
			processor.addCommandList(cmds);
			return true;
		}

		return false;
	}

	public void clean() {
		NAGImage.clearCache();
		removeActors();

	}

	private void onLeaveStage() {
		gui.closesAllDialogs();
		App.setRootStyle("fade-out");
		hotSpots.save();
	}

	public void save() {
		hotSpots.save();
	}

	protected void removeActor(Actor a) {
		if (actors.contains(a)) {
			App.debug("[ACTOR] removing " + a.getName());
			actors.remove(a);
		}
	}

	private void removeActors() {
		if (actorTargets == null || actorTargets.size() == 0) {
			return;
		}
		for (ActorTarget at : actorTargets) {
			at.destroy();
		}
		actors.clear();
	}

	@Override
	public boolean unload() {
		App.debug("STAGE unload");
		return true;
	}

	protected Command changeScreenCmd(final Id id) {
		return new OneShotCommand() {

			@Override
			public void start() {
				getListener().changeScreen(id);
			}
		};
	}

	protected Command enableMaskCmd(final boolean enable) {
		return new OneShotCommand() {

			@Override
			public void start() {
				getForeground().setMaskEnable(enable);
			}
		};
	}

	public void drawHotspots() {
		enableDebug();
		hotSpots.drawMask(getDebugLayer());
	}

	protected void addActor(Actor actor) {
		actors.add(actor);
	}

	@Deprecated
	public void addActor(ActorTarget actorTarget) {
		addActor(actorTarget.getActor());
		addTarget(actorTarget.getActor().getTarget());
	}

	@Override
    protected void onAvailable(boolean isFirst) {
		super.onAvailable(isFirst);
		if (actorTargets != null) {
			for (ActorTarget at : actorTargets) {
				at.onAvailable();
				at.getActor().addSubtitle();
			}
		}

		if (actors != null) {
			for (Actor actor : actors) {
				actor.addSubtitle();
			}
		}
	}

	public void onInventoryClose() {
	}

	public abstract void reset();

	@Override
	public boolean isValid() {
		if (!super.isValid()) {
			return false;
		}
		if (foreground == null || !foreground.isValid()) {
			return false;
		}
		if (actorTargets != null) {
			for (ActorTarget at : actorTargets) {
				if (!at.isValid()) {
					return false;
				}
			}
		}

		if (actors != null) {
			for (Actor actor : actors) {
				if (!actor.isValid()) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public final void doDestroy() {
		App.infoDestroy("stage " + getId());
		onLeaveStage();
		removeActors();
		ConversationView.destroyInstace();
		destroyHotSpots();
		destroyHeroes();
		destroyActors();
		destroyResources();
		destroyStage();
		setId(null);
	}


	protected abstract void destroyHeroes();
	protected abstract void destroyActors();
	protected abstract void destroyResources();
	protected abstract void destroyStage();

	private void destroyHotSpots() {
		hotSpots.destroy();
		hotSpots = null;
	}

	public abstract void autoPilot(Settings.AutoPilot state);

	protected void click(HotSpot.Id id) {
		HotSpot hotSpot = hotSpots.get(id);
		if (hotSpot == null) {
			App.warn("No such hotSpot " + id + " on this stage.");
			return;
		}
		onClick(hotSpot.getLookX(), hotSpot.getLookY());
	}

	protected void zoom(int x, int y) {
		gui.zoom(x, y);
	}
}