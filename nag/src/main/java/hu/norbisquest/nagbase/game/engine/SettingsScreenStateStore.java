package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.Stage;

public final class SettingsScreenStateStore implements ScreenStateStore {
    private final Settings settings;

    public SettingsScreenStateStore(Settings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings must not be null");
        }
        this.settings = settings;
    }

    @Override
    public void commit(Stage.Id referer, Stage.Id current) {
        settings.setReferer(referer);
        settings.setScreenId(current);
    }
}
