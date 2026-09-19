package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.common.gui.HasContent;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.engine.ScreenProvider;
import hu.norbisquest.nq1.client.factory.NQ1StageFactory;

public class NQ1ScreenProvider extends ScreenProvider {
    NQ1ScreenProvider(HasContent parent) {
        super(parent);
    }

    @Override
    protected void createScreen(Stage.Id id) {
   //     resetGUI();
        getPanel().addStyleName("gui");
        NQ1StageFactory.createStage(id, this);
    }
}
