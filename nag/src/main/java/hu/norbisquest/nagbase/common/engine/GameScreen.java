package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.game.Stage;

public interface GameScreen extends Destroyable {
    Stage.Id getId();

    void attach(ScreenContext context);

    boolean prepare();

    boolean isReady();

    void enter();

    void update(double timestamp);

    void setInputEnabled(boolean enabled);
}
