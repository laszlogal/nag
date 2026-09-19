package hu.norbisquest.nagbase.common.engine;

public interface Command {
	void start();
	boolean isBlocker();
	void process();
	boolean hasFinished();
	void setCondition(boolean condition);
	boolean getCondition();
}