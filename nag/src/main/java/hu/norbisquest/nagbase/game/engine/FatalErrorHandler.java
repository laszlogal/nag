package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.game.Stage;

@FunctionalInterface
public interface FatalErrorHandler {
    void show(Stage.Id failedScreen, Throwable cause);
}
