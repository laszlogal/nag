package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.Destroyable;

public interface ScreenParent {
    FlowPanel getPanel();
    <T extends Destroyable> T register(T resource);
    void onScreenCreated(GameScreen screen);
    void onScreenCreationFailed(Throwable cause);
}
