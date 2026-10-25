package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.game.Stage;

public interface GameScreen extends Destroyable {
    Stage.Id getId();

    void setGui(GameGUI gui);

    void setListener(ScreenListener listener);

    boolean load();

    boolean isValid();

    void show();

    void execute(double timestamp);

    void setInputEnabled(boolean enabled);
}
