package hu.norbisquest.nagbase.game;

import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.common.game.target.HotSpotContainer;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.target.HotSpot;

import java.util.HashMap;
import java.util.Map;

public class HotSpotContainerSimple extends NAGObject implements HotSpotContainer {
    private Map<HotSpot.Id, HotSpot> items;
    private ImageData maskData =null;
    private NAGImage image;
    private Stage.Id stageId;

    public HotSpotContainerSimple(Stage.Id stageId) {
        this.stageId = stageId;
        maskData = null;
        items = new HashMap<>();

    }

    @Override
    public void add(HotSpot hotSpot) {
        App.debug("adding " + hotSpot);
        hotSpot.setData(maskData);
        items.put(hotSpot.getId(), hotSpot);
    }

    @Override
    public HotSpot get(HotSpot.Id id) {
        return items.get(id);
    }

    @Override
    public void setMask(ImageResource res) {
        if (res == null) {
            App.warn("No HotSpot Mask!");
            return;
        }

        image = new NAGImage(res);
        image.addLoadHandler(event -> imageToMask());
        image.load();
    }

    @Override
    public void imageToMask() {
        maskData = image.getImageData();
    }

    @Override
    public boolean hasMask() {
        return (maskData != null);
    }


    @Override
    public void enable(HotSpot.Id id) {
        get(id).setEnabled(true);
    }

    @Override
    public void disable(HotSpot.Id id) {
        get(id).setEnabled(false);
    }

    @Override
    public void enableAll() {
        for (HotSpot hotSpot : items.values()) {
            hotSpot.setEnabled(true);
        }
    }

    @Override
    public void disableAll() {
        for (HotSpot hotSpot : items.values()) {
            hotSpot.setEnabled(false);
        }
    }

    @Override
    public void save() {
        for (HotSpot hotSpot : items.values()) {
            App.getSettings().saveHotSpot(stageId, hotSpot);
        }
    }

    @Override
    public void drawMask(Layer layer) {
        layer.getContext2d().putImageData(maskData, 0, 0);
    }

    @Override
    protected void doDestroy() {
        for (HotSpot h: items.values()) {
            App.infoDestroy("hotspot " + h.getId());
            h.destroy();
            h = null;
        }

        items.clear();
        items = null;
        image.destroy();
        image = null;
        maskData = null;
    }

    @Override
    public NAGImage getImage() {
        return image;
    }
}
