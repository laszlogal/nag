package hu.norbisquest.nagbase.core;

import com.google.gwt.dom.client.Element;

public class Scaler {
    private final static String[] engines = new String[] { "", "webkit", "ms", "Moz", "O", "khtml" };

    public static void scale(Element elem, double ratio) {
        scale(elem, 0, 0, ratio);
    }

    public static void scale(Element elem, int x, int y, double ratio) {
        String name = "Transform";
        String origin = "TransformOrigin";
        for (String prefix : engines) {
            elem.getStyle().setProperty(prefix + name, "scale(" + ratio + ")");
            elem.getStyle().setProperty(prefix + origin, x + "px " + y + "px 0px");
        }
    }

}
