package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.game.Stage;

@FunctionalInterface
public interface ScreenFactory {
    void create(Stage.Id id, ScreenNavigation navigation,
                ScreenCreationCallback callback);
}
