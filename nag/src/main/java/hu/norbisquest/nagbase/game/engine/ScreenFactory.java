package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;

@FunctionalInterface
public interface ScreenFactory {
    void create(ScreenId id, ScreenNavigation navigation,
                ScreenCreationCallback callback);
}
