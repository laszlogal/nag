package hu.norbisquest.nq1.client.factory;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.engine.ScreenContext;
import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.game.engine.ScreenCreationCallback;
import hu.norbisquest.nagbase.game.engine.ScreenCreator;
import hu.norbisquest.nagbase.game.engine.ScreenFactory;
import hu.norbisquest.nagbase.game.engine.StagedScreenParent;

/** Creates the screen identified by the NQ1 screen registry. */
public final class NQ1ScreenFactory implements ScreenFactory {
    private final GameGUI gui;
    private final FlowPanel activeParent;

    public NQ1ScreenFactory(GameGUI gui) {
        if (gui == null || gui.getContent() == null) {
            throw new IllegalArgumentException("gui must not be null");
        }
        this.gui = gui;
        activeParent = gui.getContent();
    }

    @Override
    public void create(ScreenId id, ScreenNavigation navigation,
                       ScreenCreationCallback callback) {
        if (!(id instanceof NQ1Ids)) {
            callback.onFailure(new IllegalArgumentException("Unsupported screen id: " + id));
            return;
        }

        ScreenCreator creator = ((NQ1Ids) id).screenCreator();
        if (creator == null) {
            callback.onFailure(new IllegalArgumentException("Screen is not creatable: " + id));
            return;
        }

        StagedScreenParent parent = new StagedScreenParent(
                activeParent, new ScreenContext(gui, navigation), callback);
        try {
            creator.create(parent);
        } catch (Throwable cause) {
            parent.onScreenCreationFailed(cause);
        }
    }
}
