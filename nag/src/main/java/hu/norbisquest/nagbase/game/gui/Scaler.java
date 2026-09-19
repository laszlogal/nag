package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;

public class Scaler implements Scalable {
    private double ratio;

    private int scaledLeft;

    private int scaledTop;

    private double ratioX;

    private double ratioY;

    private Element element;

    Scaler(Element element) {
        this.element = element;
    }

    @Override
    public int getHeight() {
        return (int) Math.round(App.HEIGHT * ratio);
    }

    @Override
    public int getWidth() {
        return (int) Math.round(App.WIDTH * ratio);
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    @Override
    public double getRatioX() {
        return ratioX;
    }

    @Override
    public double getRatioY() {
        return ratioY;
    }

    private void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public int getLeft() {
        return scaledLeft;
    }

    private void setScaledLeft(int scaledLeft) {
        this.scaledLeft = scaledLeft;
    }

    @Override
    public int getTop() {
        return scaledTop;
    }

    private void setScaledTop(int scaledTop) {
        this.scaledTop = scaledTop;
    }

    @Override
    public void fitToWindow() {
        double w = Window.getClientWidth();
        double h = Window.getClientHeight();

        setScaledLeft(0);
        setScaledTop(0);

        double ratioW = w / App.WIDTH;
        double ratioH = h / App.HEIGHT;

        setRatio(ratioW > ratioH ? ratioH : ratioW);


        setScaledLeft((int) Math.abs((w - getWidth()) / 2));
        setScaledTop((int) Math.abs((h - getHeight()) / 2));

        ratioX = App.WIDTH / (w - 2 * getLeft());
        ratioY = App.HEIGHT / (h - 2 * getTop());
        scale(0, 0, ratio);

    }

    @Override
    public void scale(int ox, int oy, double ratio) {
        Browser.scale(element, ox, oy, ratio);
    }

    @Override
    public void reset() {
        Browser.scale(element, 0, 0, 1);
    }
}
