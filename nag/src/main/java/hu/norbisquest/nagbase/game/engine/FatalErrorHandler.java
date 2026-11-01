package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.ScreenId;

@FunctionalInterface
public interface FatalErrorHandler {
    void show(ScreenId failedScreen, Throwable cause);
}
