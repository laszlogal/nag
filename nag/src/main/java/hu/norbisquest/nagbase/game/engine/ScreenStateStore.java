package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.ScreenId;

public interface ScreenStateStore {
    void commit(ScreenId referer, ScreenId current);
}
