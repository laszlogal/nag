package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;

public abstract class TimeoutCommand implements Command {
	private int counter;
	private int timeout;
	@Override
	public void start() {
		counter = 0;
	}

	@Override
	public void process() {
		counter++;
	}

	@Override
	public boolean hasFinished() {
		return timeout <= counter;
	}

	public int getTimeout() {
		return timeout;
	}

	protected void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
