package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.common.gui.HasContent;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Cursor;
import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.engine.*;
import hu.norbisquest.nagbase.game.target.ItemFactory;
import hu.norbisquest.nagbase.resources.StyleInjector;

import java.util.List;

public abstract class GameBuilder {

    private GameGUI gui;


    private Engine engine;

    public void build(int fps) {
        Browser.addFullscreenChangeListener();
        App.setDefaultMode(getDefaultMode());
        App.setCursor(createCursor());
        App.setSettings(createSettings());
        parseParameters();
        injectStyles();
        App.getInventory().setFactory(getItemFactory());

        gui = createGui(createDialogManager());
        GameDisplay display = new GameDisplay();
        ScreenFactory screenFactory = createScreenFactory(gui);
        ScreenCoordinator coordinator = new ScreenCoordinator(
                screenFactory,
                display,
                new SettingsScreenStateStore(App.getSettings()));
        GameLoop gameLoop = new GameLoop(display, new GwtFrameScheduler(), fps);
        engine = new Engine(gameLoop, display, coordinator);
        engine.addChangeScreenHandler(gui);
        coordinator.changeScreen(App.getSettings().getStartId());
    }

    protected abstract Settings createSettings();
    protected abstract DialogManager createDialogManager();
    protected abstract void parseParameters();
    protected abstract Cursor createCursor();
    protected abstract ItemFactory getItemFactory();
    protected abstract App.GameModes getDefaultMode();

    private void injectStyles() {
        for (TextResource css: getCssResources()) {
            StyleInjector.inject(css);
        }
    }

    protected abstract List<TextResource> getCssResources();


    public abstract ScreenFactory createScreenFactory(HasContent parent);

    public abstract GameGUI createGui(DialogManager dialogManager);


    public Engine getEngine() {
        return engine;
    }

    public GameGUI getGui() {
        return gui;
    }
}
