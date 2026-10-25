package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;

@SuppressWarnings("unusable-by-js")
public record ScreenContext(
        GameGUI gui,
        ScreenNavigation navigation) {

    public ScreenContext {
        if (gui == null || navigation == null) {
            throw new IllegalArgumentException(
                    "Screen context dependencies must not be null");
        }
    }
}
