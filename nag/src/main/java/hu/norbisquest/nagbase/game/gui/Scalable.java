package hu.norbisquest.nagbase.game.gui;

public interface Scalable {
    int getLeft();

    int getTop();

    int getHeight();

    int getWidth();

    double getRatio();

    double getRatioX();

    double getRatioY();

    void fitToWindow();

    void scale(int ox, int oy, double ratio);

    void reset();
}
