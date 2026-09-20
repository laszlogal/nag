package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.game.Stage;

public interface ScreenFactory {
    void create(Stage.Id id, ScreenCreationCallback callback);
}
