package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.common.engine.Command;

import java.util.List;

public interface TalkControl extends TargetControl {
	List<Command> onTalk(int x, int y);
}
