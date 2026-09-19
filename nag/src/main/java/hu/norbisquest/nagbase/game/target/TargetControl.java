package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.Destroyable;

import java.util.List;

public interface TargetControl extends Destroyable {
	List<Command> onWalk(int x, int y);

	List<Command> onExamine(int x, int y);

	List<Command> onUse(int x, int y);

	List<Command> onInventory(HotSpot.Id id);

}
