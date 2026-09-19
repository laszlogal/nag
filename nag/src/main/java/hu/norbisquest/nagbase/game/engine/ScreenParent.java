package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;

public interface ScreenParent {
    FlowPanel getPanel();
    void onScreenCreated(GameScreen screen);
}
