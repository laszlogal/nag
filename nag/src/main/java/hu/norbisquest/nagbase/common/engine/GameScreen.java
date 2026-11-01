package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.core.Destroyable;

public interface GameScreen extends Destroyable {
    ScreenId getId();

    void attach(ScreenContext context);

    boolean prepare();

    boolean isReady();

    void enter();

    void update(double timestamp);

    void setInputEnabled(boolean enabled);
}
