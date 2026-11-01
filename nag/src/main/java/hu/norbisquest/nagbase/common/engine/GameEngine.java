package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.game.Screen;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.engine.ScreenChangeListener;

public interface GameEngine {
    void addScreenChangeListener(ScreenChangeListener listener);

    Screen getCurrentScreen();

    Stage getCurrentStage();

    void run();

    void stop();
}
