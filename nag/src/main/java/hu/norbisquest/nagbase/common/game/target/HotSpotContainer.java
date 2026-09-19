package hu.norbisquest.nagbase.common.game.target;

import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.target.HotSpot;

public interface HotSpotContainer extends Destroyable {
    void add(HotSpot hotSpot);

    HotSpot get(HotSpot.Id id);

    void setMask(ImageResource res);

    void imageToMask();

    boolean hasMask();

    void enable(HotSpot.Id id);

    void disable(HotSpot.Id id);

    void save();

    void drawMask(Layer layer);

    NAGImage getImage();

    void enableAll();
    void disableAll();
}
