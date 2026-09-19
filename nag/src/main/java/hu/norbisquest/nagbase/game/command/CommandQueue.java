package hu.norbisquest.nagbase.game.command;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.core.NAGObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommandQueue extends NAGObject {
	private Queue<Command> commands;

	// Current command running.
	private Command exec;

	public CommandQueue() {
		commands = new LinkedList<>();
		exec = null;
	}

	public void add(Command cmd) {
		commands.add(cmd);
	}

	public void clear() {
		commands.clear();
		exec = null;
	}

	private Command next() {
		Command cmd = commands.peek();
		if (cmd != null) {
			if (cmd.getCondition()) {
				cmd.start();
			} else {
				return null;
			}
			commands.remove();
		}

		return cmd;
	}

	/**
	 * executes the next command in the queue, if any.
	 * 
	 * @return Whether the command blocks the events or not.
	 */
	public boolean process() {
		if (!hasCommand()) {
			return false;
		}

		if (exec == null || exec.hasFinished()) {
			exec = next();
		}

		if (exec != null) {
			exec.process();
		}

		return exec != null && exec.isBlocker();
	}

	public boolean hasCommand() {
		return (!commands.isEmpty() || exec != null);
	}

	@Override
	public String getPrefix() {
		return "COMMAND QUEUE";
	}

	public void append(List<Command> cmds) {
		for (Command command : cmds) {
			add(command);
		}
	}

	public void clearOther(Command cmd) {
		commands.removeIf(command -> (cmd != command));
	}
}
