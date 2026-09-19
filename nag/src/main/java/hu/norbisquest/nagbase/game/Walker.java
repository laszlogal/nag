package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.walk.RouteInterface;
import hu.norbisquest.nagbase.game.walk.WalkAlgorithm;
import hu.norbisquest.nagbase.game.walk.WalkGraph;
import hu.norbisquest.nagbase.game.walk.WalkPoint;

import java.util.List;
import java.util.Map;

public abstract class Walker extends Actor {
	private static final int MAX_SCALE = 5;
	public static final int WALK_FRONT = 0;
	protected static final int WALK_BACK = 1;
	public static final int WALK_LEFT = 2;
	public static final int WALK_RIGHT = 3;

	public static final int TALK_FRONT = 1000;
	public static final int TALK_BACK = 1001;
	public static final int TALK_LEFT = 1002;
	public static final int TALK_RIGHT = 1003;
	private Integer walkLeftId;
	private Integer walkRightId;
	private Integer walkFrontId;
	private Integer walkBackId;
	private Integer talkLeftId;
	private Integer talkRightId;
	private Integer talkFrontId;
	private Integer talkBackId;

	private int talkDirection;
	private double destX;
	private double destY;
	private RouteInterface route;
	private WalkAlgorithm algorithm;
	private WalkPoint lastPosition;
	private WalkPoint position;
	private double x = -1;
	private double y = -1;
	private WalkerListener walkListener;
	private LabelListener labelListener;
	private RegionListener regionListener;
	private WalkerState state = null;
	private int tickCount = 1;
	private double moveSpeed = 1;
	private int walkAnimSpeed;
	private int slopeStep;

	private boolean isReadyForWalk() {
		return route != null && route.isValid() && !isRunning();
	}

    public void cleanup() {
	    super.cleanup();
    	algorithm.destroy();
	}

	protected int getWalkAnimSpeed() {
		return walkAnimSpeed;
	}

	private class WalkerState {
		private Integer walkLeftId;
		private Integer walkRightId;
		private Integer walkFrontId;
		private Integer walkBackId;
		private Integer talkLeftId;
		private Integer talkRightId;
		private Integer talkFrontId;
		private Integer talkBackId;

		private int talkDirection;
		WalkPoint position;
		Integer talkBase;
	}

	private class WalkerTarget extends ActTarget {

		@Override
		public int getPosX() {
			return (int) x;
		}

		@Override
		public int getPosY() {
			return (int) y;
		}
	}

	@Override
	public Target newTarget() {
		return new WalkerTarget();
	}

	public interface WalkerListener {
		void onArrive();
	}

	public interface LabelListener {
		void onLabelChanged(String old, String label);
	}

	public interface RegionListener {
		void onRegionEnter(String label);

		void onRegionLeave(String label);

		void onRegionMove(String label);

	}


	public Walker(String name) {
		super(name);
		lastPosition = null;
		position = null;
		route = null;
		labelListener = null;
		regionListener = null;
	}

	Walker(ActorData res) {
		super(res);
		lastPosition = null;
		position = null;
		route = null;
		labelListener = null;
		regionListener = null;
	}

	Walker(String name, Map<Integer, String> lines, List<DataResource> audioData) {
		super(name, lines, audioData);
		lastPosition = null;
		position = null;
		route = null;
		labelListener = null;
		regionListener = null;
	}

	public void setFreePosition(int x, int y) {
		// no walkpoints considered
		this.x = x;
		this.y = y;
		update();
	}

	public void setPosition(int x, int y) {
		if (!getAlgorithm().isValid()) {
			App.debug("WalkAlgorithm is not valid!");
			return;
		}
		position = getAlgorithm().walkPointAt(x, y);
		calcPosition();

		update();

	}

	private void calcPosition() {
		if (!isLoaded() || position == null) {
			return;
		}
		if (route != null && !route.isEmpty()) {
			processRoute();
		}

		setScale(position.getScale());

		x = position.getX() - (getWidth() / 2);
		y = position.getY() - getHeight();

	}


	protected void updatePosition() {
		if (!isLoaded() || position == null) {
			return;
		}

		setScale(position.getScale());

		x = position.getX() - (getWidth() / 2);
		y = position.getY() - getHeight();

	}

	private void arrive() {
		stop();
		reset();
		update();

		setPosition((int) destX, (int) destY);
		algorithm.reset();
		if (walkListener != null) {
			walkListener.onArrive();
		}

		route = null;
	}

	void processRoute() {
		App.debug("processRoute");
		lastPosition = position;

		WalkPoint p = route.next();

		if (p != null) {
			double sc = getAlgorithm().getScale(p.getY());
			p.setScale(sc);
			position = p;
			if (regionListener == null) {
				return;
			}

			String lastRegion = lastPosition.getLabel();
			String region = position.getLabel();

			if (region.equals(lastRegion)) {
				regionListener.onRegionMove(region);
			} else {
				regionListener.onRegionLeave(lastRegion);
				regionListener.onRegionEnter(region);
			}

		}
	}

	RouteInterface getRoute() {
		return route;
	}

	private double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAbsoluteX() {
		return (int) Math.round(x + App.getCurrentScreen().getLeft());
	}

	private int getAbsoluteY() {
		return (int) Math.round(y);
	}

	public boolean tick(double timestamp) {
		if (isRunning()) {
			if (tickCount > moveSpeed) {
				tickCount = 1;
				calcPosition();
				scrollStage();
			}
			tickCount++;
			return super.tick(timestamp);

		}
		return false;


	}

	private void buildRoute() {
		if (algorithm == null || isWalking()) {
			return;
		}

		getAlgorithm().buildRoute();

		if (algorithm.isRouteReady() && route == null) {
			route = algorithm.getRoute();

//			return true;
		}

	}

	private void startWalking() {
		if (route.isValid()) {
			App.debug("[ROUTE] Start walking on " + route.toString());
			start();
		}
	}
	public void walkTo(double x1, double y1) {
		destX = x1;
		destY = y1;
		algorithm.reset();
		getAlgorithm().initRoute(position.getX(), position.getY(), x1, y1, slopeStep);

	}

	public void lookAt(int x1, int y1) {
		int px = (int) position.getX();
		int py = (int) position.getY() - (getCurrentFrame().getHeight() / 2);
		// App.debug("[LOOK] Look to x: " + x1 + " y: " + y1);
		// App.debug("[LOOK] NORBI px: " + px + " py: " + py);
		int dx = x1 - px;
		int dy = y1 - py;
		App.debug("[LOOK] Diff    dx: " + dx + " dy: " + dy);
	 		if (Math.abs(dx) < Math.abs(dy)) {
			if (dy > 0) {
				App.debug("[LOOK] front");
				turnFront();
			} else {
				App.debug("[LOOK] back");
				turnBack();
			}
		} else {
			if (dx > 0) {
				App.debug("[LOOK] right");
				turnRight();
			} else {
				App.debug("[LOOK] left");
				turnLeft();
			}
		}
	}

	public int getWalkSpeed() {
		return getAlgorithm().getStep();
	}

	public final WalkPoint getPosition() {
		return position;
	}

	public final WalkPoint getLastPosition() {
		return lastPosition;
	}

	private boolean hasValidWalkArea() {
		return (getAlgorithm() != null && getAlgorithm().isValid());
	}

	@Override
	public boolean isValid() {
		return super.isValid() && position != null && hasValidWalkArea();
	}

	public void setPosition(WalkPoint p) {
		setPosition((int) p.getX(), (int) p.getY());
		setScale(p.getScale());
	}

	public void refresh() {
		setPosition(lastPosition);
	}

	public void drawTo(Layer layer) {
		if (position == null) {
			return;
		}
		layer.draw(getAnimation(), x, y);
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += "\tPosition: (" + x + ", " + y + ") speed: " + getWalkSpeed() + (isRunning() ? "running" : "stopped");
		return result;
	}
//
//	@Override
//	public boolean unload() {
//		super.unload();
//	}

	public WalkerListener getListener() {
		return walkListener;
	}

	public void setListener(WalkerListener listener) {
		this.walkListener = listener;
	}

	public WalkAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(WalkAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public LabelListener getLabelListener() {
		return labelListener;
	}

	public void setLabelListener(LabelListener labelListener) {
		this.labelListener = labelListener;
	}

	public RegionListener getRegionListener() {
		return regionListener;
	}

	public void setRegionListener(RegionListener regionListener) {
		this.regionListener = regionListener;
	}

	public boolean isWalking() {
		return route != null && route.isValid();
	}

	@Override
	public final void destroyCore() {
		App.infoDestroy("Walker");
		algorithm.destroy();
		algorithm = null;
		position = null;
		lastPosition = null;
		walkListener = null;
		labelListener = null;
		regionListener = null;
		state = null;
	}

	/**
	 * 
	 * @param walkId
	 *            walk animation id.
	 * @param talkId
	 *            talk animation id.
	 * @return if turn was successful.
	 */
    private boolean turn(Integer walkId, Integer talkId) {
		if (walkId == null) {
			return false;
		}
		changeAnimation(walkId);
		if (talkId != null) {
			setTalkBase(walkId);
			setTalkDirection(talkId);
		}

		return true;
	}

	protected boolean turnFront() {
		return turn(getWalkFrontId(), talkFrontId);
	}

	protected boolean turnBack() {
		return turn(getWalkBackId(), talkBackId);
	}

	protected boolean turnLeft() {
		return turn(getWalkLeftId(), talkLeftId);
	}

	protected boolean turnRight() {
		return turn(getWalkRightId(), talkRightId);
	}

	protected void setWalkAnimation(Integer front, Integer back, Integer left, Integer right) {
		setWalkFrontId(front);
		setWalkBackId(back);
		setWalkLeftId(left);
		setWalkRightId(right);
		// changeAnimation(getWalkFrontId());
	}

	protected void setTalkAnimation(Integer animId) {
		setTalkAnimation(animId, animId, animId, animId);
	}

	protected void setTalkAnimation(Integer front, Integer back, Integer left, Integer right) {
		talkFrontId = front;
		talkBackId = back;
		talkLeftId = left;
		talkRightId = right;
		// turnFront();
	}

	public boolean canWalkFront() {
		return getWalkFrontId() != null;
	}

	public boolean canWalkBack() {
		return getWalkBackId() != null;
	}

	public boolean canWalkLeft() {
		return getWalkLeftId() != null;
	}

	public boolean canWalkRight() {
		return getWalkRightId() != null;
	}

	public int getTalkDirection() {
		return talkDirection;
	}

	private void setTalkDirection(int talkDirection) {
		this.talkDirection = talkDirection;
	}

	public boolean isFacingLeft() {
		return getCurrentAnimationId().equals(getWalkLeftId());
	}

	public boolean isFacingRight() {
		return getCurrentAnimationId().equals(getWalkRightId());
	}

	public boolean isFacingFront() {
		return getCurrentAnimationId().equals(getWalkFrontId());
	}

	public boolean isFacingBack() {
		return getCurrentAnimationId().equals(getWalkBackId());
	}

	public void putTo(String label) {
		WalkGraph graph = (WalkGraph) getAlgorithm();
		WalkPoint wp = graph.getVertex(label);
		if (wp.getScale() > MAX_SCALE) {
			wp.setScale(1.0);
		}

		setPosition(wp);

		App.debug("[PUT] " + label + ": " + wp);

	}

	private Integer getWalkLeftId() {
		return walkLeftId;
	}

	private void setWalkLeftId(Integer walkLeftId) {
		this.walkLeftId = walkLeftId;
	}

	private Integer getWalkRightId() {
		return walkRightId;
	}

	private void setWalkRightId(Integer walkRightId) {
		this.walkRightId = walkRightId;
	}

	private Integer getWalkFrontId() {
		return walkFrontId;
	}

	private void setWalkFrontId(Integer walkFrontId) {
		this.walkFrontId = walkFrontId;
	}

	private Integer getWalkBackId() {
		return walkBackId;
	}

	private void setWalkBackId(Integer walkBackId) {
		this.walkBackId = walkBackId;
	}

	@Override
	public int getTextX() {
		return (int) (getX());
		// getAbsoluteX() - (getSubLabel().getOffsetWidth() / 2);
	}

	@Override
	public int getTextY() {
		return getAbsoluteY() - getSubLabel().getOffsetHeight() - 50;
	}

	void saveAnimState() {
		if (state == null) {
			state = new WalkerState();
		}
		state.walkFrontId = walkFrontId;
		state.walkBackId = walkBackId;
		state.walkLeftId = walkLeftId;
		state.walkRightId = walkRightId;
		state.talkFrontId = talkFrontId;
		state.talkBackId = talkBackId;
		state.talkLeftId = talkLeftId;
		state.talkRightId = talkRightId;
		state.talkDirection = talkDirection;
		state.talkBase = getTalkBase();
		state.position = getPosition();
	}

	void restoreAnimState() {

		walkFrontId = state.walkFrontId;
		walkBackId = state.walkBackId;
		walkLeftId = state.walkLeftId;
		walkRightId = state.walkRightId;
		talkFrontId = state.talkFrontId;
		talkBackId = state.talkBackId;
		talkLeftId = state.talkLeftId;
		talkRightId = state.talkRightId;
		talkDirection = state.talkDirection;
		setTalkBase(state.talkBase);
		setPosition(state.position);
	}

	public void process() {
    	if (!algorithm.isRouteReady()) {
    		buildRoute();
		} else if (isReadyForWalk()) {
    		startWalking();
		} else if (route == null || !route.isValid()) {
			arrive();
		}
	}

	protected void setWalkAnimatiomSpeed(int animSpeed, int moveSpeed, int slopeStep) {
		this.walkAnimSpeed = animSpeed;
    	this.moveSpeed = moveSpeed;
  		this.slopeStep = slopeStep;
    }

    protected void setAnimation(int animId) {
    	setWalkAnimation(animId, animId, animId, animId);
	}

    private void scrollStage() {
		if (isWalking()) {
			int left = App.getCurrentStage().getLeft();
			int width = App.getCurrentStage().getWidth();
			int middleX = App.getWidth() / 2;
			double x = position.getX() + left;
			int diff = (int)moveSpeed;
			if (isFacingLeft() && left < 0 && x < middleX) {
				App.getCurrentStage().moveLeft(diff);
			} else if (isFacingRight() && left > App.WIDTH - width && x > middleX) {
				App.getCurrentStage().moveLeft(-diff);
			}
		}
	}
}
