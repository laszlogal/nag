package hu.norbisquest.nagbase.game.command;

public interface Command {
	void start();
	boolean isBlocker();
	void process();
	boolean hasFinished();
	void setCondition(boolean condition);
	boolean getCondition();
}