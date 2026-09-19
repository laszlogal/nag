package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.game.Stage;

public interface GameScreen extends Destroyable, Loadable {
    void setGui(GameGUI gui);

    void show();

    boolean isValid();

    Stage.Id getId();

    ScreenListener getListener();

    void setListener(ScreenListener listener);

    void appear();

    void execute(double timestamp);

}
