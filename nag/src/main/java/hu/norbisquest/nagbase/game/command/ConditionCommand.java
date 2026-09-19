package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;

public abstract class ConditionCommand implements Command {
	private boolean condition=true;
	@Override
	public void setCondition(boolean condition) {
		this.condition = condition;
	}

	@Override
	public boolean getCondition() {
		return condition;
	}

}
