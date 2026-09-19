package hu.norbisquest.nagbase.common.gui;

import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.game.engine.ChangeScreen;
import hu.norbisquest.nagbase.game.gui.Toolbar;

public interface GameGUI extends ChangeScreen, HasMenu, HasContent, HasZoom {

    void add(Widget widget);

    void setStyleName(String styleName);

    void addStyleName(String styleName);

    void resize();

    void reset();

    void scale(int ox, int oy, double ratio);

    void setPosition(int top, int left);

    Toolbar getToolbar();

    HasZoom getZoomer();

    void closesAllDialogs();

    DialogManager getDialogManager();
}
