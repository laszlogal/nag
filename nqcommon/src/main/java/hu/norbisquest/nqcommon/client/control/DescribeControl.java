package hu.norbisquest.nqcommon.client.control;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.Speech;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.HotSpot.Id;
import hu.norbisquest.nagbase.game.walk.WalkPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DescribeControl extends SimpleControl {

	// private Speech examineSpeech;
	// private int examineDirection;
	// private Speech useSpeech;
	// private int useDirection;
	private WalkPoint walkPoint;
	private ArrayList<Command> examineList;
	private ArrayList<Command> useList;
	private Map<HotSpot.Id, ArrayList<Command>> inventoryUseList;
	private boolean walkToExamine;
	private boolean walkToUse;

	private DescribeControl(boolean walkToExamine, boolean walkToUse) {
		walkPoint = null;
		examineList = new ArrayList<>();
		useList = new ArrayList<>();
		inventoryUseList = new HashMap<>();
		this.setWalkToExamine(walkToExamine);
		this.setWalkToUse(walkToUse);

	}

	public DescribeControl(Speech examine, int examineDirection, Speech use,
			int useDirection) {
		this(true, true);
		addExamineSpeech(examine, examineDirection);
		addUseSpeech(use, useDirection);
	}

	public DescribeControl(int examineIdx, int examineDirection, int useIdx,
			int useDirection) {
		this(true, true);
	}

	@Override
	public List<Command> onExamine(int x, int y) {
		ArrayList<Command> list = new ArrayList<>();
		if (isWalkToExamine()) {
			list.add(walkPoint == null ? walk(x, y) : walk(walkPoint));
		}
		list.addAll(examineList);
		return list;
	}

	@Override
	public List<Command> onUse(int x, int y) {
		ArrayList<Command> list = new ArrayList<>();
		if (isWalkToUse()) {
			list.add(walkPoint == null ? walk(x, y) : walk(walkPoint));
		}

		list.addAll(useList);
		return list;
	}

	private void addExamineSpeech(Speech speech, int direction) {
		if (speech == null) {
			return;
		}

		examineList.add(say(speech, direction));

	}

	private void addUseSpeech(Speech speech, int direction) {
		if (speech == null) {
			return;
		}

		useList.add(say(speech, direction));
	}

	public void addInventoryCommands(HotSpot.Id itemId,
			List<Command> commands) {
		if (inventoryUseList.containsKey(itemId)) {
			inventoryUseList.get(itemId).addAll(commands);
		} else {
			inventoryUseList.put(itemId, new ArrayList<>(commands));
		}

	}

	public void addInventorySpeech(HotSpot.Id itemId, int speechIdx,
			int direction) {
		ArrayList<Command> list = null;
		if (inventoryUseList.containsKey(itemId)) {
			list = inventoryUseList.get(itemId);
		} else {
			list = new ArrayList<>();
			inventoryUseList.put(itemId, list);
		}

		// list.add(Norbi.sayEnum(speechIdx, direction));

	}

	public void setWalkPoint(int x, int y) {
		walkPoint = new WalkPoint(x, y, 0);
	}

	public void setWalkPoint(WalkPoint p) {
		walkPoint = p;
	}

	private boolean isWalkToExamine() {
		return walkToExamine;
	}

	public WalkPoint getWalkPoint() {
		return walkPoint;
	}

	private void setWalkToExamine(boolean walkToExamine) {
		this.walkToExamine = walkToExamine;
	}

	private boolean isWalkToUse() {
		return walkToUse;
	}

	private void setWalkToUse(boolean walkToUse) {
		this.walkToUse = walkToUse;
	}

	@Override
	public List<Command> onInventory(Id id) {
		return null;
	}
}
