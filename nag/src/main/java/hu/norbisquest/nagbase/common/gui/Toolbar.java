package hu.norbisquest.nagbase.common.gui;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import hu.norbisquest.nagbase.game.gui.Toolbar.ToolbarButton;
import hu.norbisquest.nagbase.game.gui.Toolbar.ToolbarListener;
import hu.norbisquest.nagbase.game.gui.Toolbar.ToolbarPosition;
import hu.norbisquest.nagbase.game.gui.ZoomHandler;

public interface Toolbar extends ZoomHandler, IsWidget {
    ToolbarButton addButton(ImageResource res, String style);

    ToolbarPosition getPosition();

    void setVisible(boolean value);

    void setListener(ToolbarListener listener);

    boolean isEnabled();

    void setEnabled(boolean value);

    void update();

    void reset();
}
