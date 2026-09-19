package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.dom.client.Element;
import hu.norbisquest.nagbase.common.gui.GuiFactory;
import hu.norbisquest.nagbase.common.gui.HasZoom;
import hu.norbisquest.nagbase.core.layer.EventLayer;
import hu.norbisquest.nagbase.core.layer.Layer;

public abstract class GuiFactorySimple implements GuiFactory {
    @Override
    public Layer createEventLayer(HasZoom zoomer) {
        EventLayer layer = new EventLayer(Layer.Z_EVENT);
        layer.setZoomer(zoomer);

        return layer;
    }

    @Override
    public HasZoom createZoomer(Scalable scaler) {
        return new Zoomer(scaler);
    }

    @Override
    public Scalable createScaler(Element element) {
        return new Scaler(element);
    }
}
