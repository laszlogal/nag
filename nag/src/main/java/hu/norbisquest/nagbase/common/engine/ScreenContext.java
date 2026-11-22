package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.core.AudioManager;

@SuppressWarnings("unusable-by-js")
public record ScreenContext(
        GameGUI gui,
        ScreenNavigation navigation,
        AudioManager audioManager) {

    public ScreenContext {
        if (gui == null || navigation == null || audioManager == null) {
            throw new IllegalArgumentException(
                    "Screen context dependencies must not be null");
        }
    }
}
