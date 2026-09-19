package hu.norbisquest.nagbase.core;

import com.google.gwt.dom.client.CanvasElement;

import java.util.List;

interface IAnimation {
    List<NAGImage> getFrames();

    void setScale(double value);

    boolean isLoaded();

    boolean isValid();

    void start();

    void stop();

    void pause();

    void resume();

    void update();

    CanvasElement getCurrentFrame();

    boolean tick(double timestamp);

    boolean unload();

    void reset();

    boolean isRunning();

    boolean load();

    double getAnimationSpeed();

    void setAnimationSpeed(double speed);

    NAGAnimation.Point getTopMiddle();

    void setVisible(boolean visible);

    boolean isLoop();

    void setLoop(boolean loop);

    int  getFrameIdx();

    boolean isSkipFirst();

    double getWidth();

    double getHeight();

    void cleanup();
}
