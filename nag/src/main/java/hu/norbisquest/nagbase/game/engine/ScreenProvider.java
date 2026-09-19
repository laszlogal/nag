package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.common.gui.HasContent;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.Stage;

import java.util.ArrayList;
import java.util.List;

public abstract class ScreenProvider extends NAGObject implements ScreenParent, ScreenListener, Settings.SettingsChanged {
    private List<ScreenRequest> requestHandlers = new ArrayList<>();
    private FlowPanel parent;
    public ScreenProvider(HasContent gui) {
        this.parent = gui.getContent();
    }

    public void addRequestHandler(ScreenRequest handler) {
        if (!requestHandlers.contains(handler)) {
            requestHandlers.add(handler);
        }
    }

    @Override
    public void changeScreen(Stage.Id id) {
        changeScreen(id, true);
    }

    @Override
    public void changeScreen(Stage.Id id, boolean clear) {
        notifyScreenRequest(id);
        requestScreen(id, clear);
    }

    @Override
    public void onScreenCreated(GameScreen screen) {
        if (screen == null) {

            return;
        }
        screen.setListener(this);
        screen.load();
        notifyScreenCreated(screen);
    }

    @Override
    public FlowPanel getPanel() {
        return parent;
    }

    private void requestScreen(final Stage.Id id, final boolean clear) {
        if (clear) {
            parent.clear();
        }
        createScreen(id);
    }

    protected abstract void createScreen(Stage.Id id);


    private void notifyScreenRequest(Stage.Id id) {
        for (ScreenRequest handler: requestHandlers) {
            handler.onScreenRequest(id);
        }
    }

    private void notifyScreenCreated(GameScreen screen) {
        for (ScreenRequest handler: requestHandlers) {
            handler.onScreenCreated(screen);
        }
    }


    @Override
    public void onSettingsChanged() {
        changeScreen(App.getSettings().getScreenId());
    }
}