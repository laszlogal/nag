package hu.norbisquest.nq1.client.factory;

import com.google.gwt.user.client.ui.FlowPanel;
import hu.norbisquest.nagbase.common.gui.HasContent;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.engine.ScreenCreationCallback;
import hu.norbisquest.nagbase.game.engine.ScreenCreator;
import hu.norbisquest.nagbase.game.engine.ScreenFactory;
import hu.norbisquest.nagbase.game.engine.StagedScreenParent;

import java.util.EnumMap;
import java.util.Map;

/** The single registry for every screen that can currently be created by NQ1. */
public final class NQ1StageFactory implements ScreenFactory {
    private final FlowPanel activeParent;
    private final Map<NQ1Ids, ScreenCreator> creators = new EnumMap<>(NQ1Ids.class);

    public NQ1StageFactory(HasContent content) {
        if (content == null || content.getContent() == null) {
            throw new IllegalArgumentException("content must not be null");
        }
        activeParent = content.getContent();
        registerScreens();
    }

    private void registerScreens() {
        creators.put(NQ1Ids.TestRoom, TestRoom::create);
        creators.put(NQ1Ids.TestScreen, TestScreen::create);
        creators.put(NQ1Ids.Room, Room::create);
        creators.put(NQ1Ids.Corridor, Corridor::create);
        creators.put(NQ1Ids.TOILET, Toilet::create);
        creators.put(NQ1Ids.HouseFront, HouseFront::create);
        creators.put(NQ1Ids.NewsStand, NewsStand::create);
        creators.put(NQ1Ids.BusStop, BusStop::create);
        creators.put(NQ1Ids.BusFront, BusFront::create);
        creators.put(NQ1Ids.BusBack, BusBack::create);
        creators.put(NQ1Ids.VRKFront, VRKFront::create);
        creators.put(NQ1Ids.RockKlub, RockKlub::create);
        creators.put(NQ1Ids.RehearsalRoom, RehearsalRoom::create);
        creators.put(NQ1Ids.PlayBass, PlayBass::create);
    }

    @Override
    public void create(Stage.Id id, ScreenCreationCallback callback) {
        if (!(id instanceof NQ1Ids)) {
            callback.onFailure(new IllegalArgumentException("Unsupported screen id: " + id));
            return;
        }

        ScreenCreator creator = creators.get((NQ1Ids) id);
        if (creator == null) {
            callback.onFailure(new IllegalArgumentException("No screen registered for " + id));
            return;
        }

        StagedScreenParent parent = new StagedScreenParent(activeParent, callback);
        try {
            creator.create(parent);
        } catch (Throwable cause) {
            parent.onScreenCreationFailed(cause);
        }
    }
}
