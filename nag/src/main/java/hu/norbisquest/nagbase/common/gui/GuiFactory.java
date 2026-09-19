package hu.norbisquest.nagbase.common.gui;

import com.google.gwt.dom.client.Element;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.gui.MenuPanel;
import hu.norbisquest.nagbase.game.gui.Scalable;

public interface GuiFactory {
    Layer createEventLayer(HasZoom zoomer);
    MenuPanel createMenu();
    Toolbar createToolBar(MenuPanel menu);
    HasZoom createZoomer(Scalable scaler);
    Scalable createScaler(Element element);
    DialogManager createDialogManager();
}
