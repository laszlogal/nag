package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.dom.client.Style;

interface ILayer {
    void setSize(int width, int height);

    void setZIndex(int value);

    int getLeft();

    void setLeft(int value, Style.Unit unit);

    void setTop(int value, Style.Unit unit);

    int getTop();

    void setId(String id);

    void setPositionInPixels(int left, int top);

    String getId();

    void moveLeft(int diff);

    void moveTop(int diff);
}
