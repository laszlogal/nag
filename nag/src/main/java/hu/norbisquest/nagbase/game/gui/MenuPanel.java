package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.user.client.ui.IsWidget;

public interface MenuPanel extends IsWidget {
    void show();
    void hide();
    void toggle();
    boolean isShowing();
}
