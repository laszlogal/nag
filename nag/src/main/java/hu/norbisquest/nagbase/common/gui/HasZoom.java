package hu.norbisquest.nagbase.common.gui;

public interface HasZoom {
    void zoom(int x, int y);
    void zoomOut();
    int toScaledX(int x);
    int toScaledY(int y);
    boolean justZoomed();
    void setJustZoomed(boolean value);
    boolean isEnabled();
    void setEnabled(boolean enabled);
}
