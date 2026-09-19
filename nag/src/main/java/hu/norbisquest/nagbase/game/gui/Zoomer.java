package hu.norbisquest.nagbase.game.gui;

import hu.norbisquest.nagbase.common.gui.HasZoom;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

public class Zoomer implements HasZoom {
    private static final int ZOOM_IN = 2;
    private boolean userZoom = false;
    private boolean enabled = true;

    private int userZoomDX;

    private int userZoomDY;
    private boolean justZoomed = false;
    private Scalable scaler;
    private List<ZoomHandler> zoomHandlers = new ArrayList<>();
    Zoomer(Scalable scaler) {
        this.scaler = scaler;
    }

    @Override
    public void zoom(int x, int y) {
        if (userZoom) {
            zoomOut();
        } else {
            zoomIn(x, y);
        }
        justZoomed = true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    void addZoomHandler(ZoomHandler handler) {
        if (!zoomHandlers.contains(handler)) {
            zoomHandlers.add(handler);
        }
    }

    @Override
    public int toScaledX(int x) {
        if (userZoom) {
            return getZoomedX(x);
        }

        return (int) (scaler.getRatioX() * x);

    }

    @Override
    public int toScaledY(int y) {
        if (userZoom) {
            return getZoomedY(y);
        }

        return (int) (scaler.getRatioY() * y);
    }

    private void zoomIn(int x, int y) {
        if (userZoom || !enabled) {
            return;
        }

        double zoomRatio = ZOOM_IN;

        int ox = toScaledX(x);
        int oy = toScaledY(y);

        scaler.scale(ox, oy, zoomRatio);
        App.debug("[ZOOM] (" + ox + ", " + oy + ") ratio: " + zoomRatio);
        userZoom = true;

        notifyZoomIn();
    }

    private int getZoomedX(int x) {
        double x0 = x * scaler.getRatioX();
        return (int) (x0 / ZOOM_IN);

    }

    private int getZoomedY(int y) {
        double y0 = y * scaler.getRatioX();
        return (int) (y0 / ZOOM_IN);

    }

    @Override
    public void zoomOut() {
        if (!userZoom || !enabled) {
            return;
        }
        scaler.scale(0, 0, 1);
        userZoom = false;

        notifyZoomOut();
    }

    @Override
    public boolean justZoomed() {
        if (!(Browser.isAndroid() && justZoomed)) {
            return false;
        }

        justZoomed = false;
        return true;
    }

    @Override
    public void setJustZoomed(boolean b) {
        justZoomed = false;
    }


    private void notifyZoomIn() {
        for (ZoomHandler handler: zoomHandlers) {
            handler.onZoomIn();
        }
    }


    private void notifyZoomOut() {
        for (ZoomHandler handler: zoomHandlers) {
            handler.onZoomOut();
        }
    }
}
