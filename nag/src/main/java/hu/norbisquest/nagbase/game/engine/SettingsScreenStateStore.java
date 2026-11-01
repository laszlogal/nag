package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.game.Settings;

public final class SettingsScreenStateStore implements ScreenStateStore {
    private final Settings settings;

    public SettingsScreenStateStore(Settings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings must not be null");
        }
        this.settings = settings;
    }

    @Override
    public void commit(ScreenId referer, ScreenId current) {
        settings.setReferer(referer);
        settings.setScreenId(current);
    }
}
