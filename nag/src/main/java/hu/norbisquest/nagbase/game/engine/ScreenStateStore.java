package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.game.Stage;

public interface ScreenStateStore {
    void commit(Stage.Id referer, Stage.Id current);
}
