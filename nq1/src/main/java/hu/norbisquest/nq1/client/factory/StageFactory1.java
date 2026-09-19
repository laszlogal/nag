package hu.norbisquest.nq1.client.factory;

import hu.norbisquest.nagbase.common.game.StageFactory;
import hu.norbisquest.nagbase.common.gui.ScreenDimension;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nagbase.game.stage.StageFactorySimple;

public class StageFactory1 extends StageFactorySimple {

    private ScreenParent parent;
    private StageModel model;
    private ScreenDimension dimension;

    static StageFactory create(ScreenParent parent, StageModel model) {
        return new StageFactory1(parent, model);
    }
    private StageFactory1(ScreenParent parent, StageModel model) {
        this.parent = parent;
        this.model = model;
        this.dimension = new ScreenDimension(model.left, model.top,
                model.backround != null ? model.backround.getWidth() : model.width,
                model.height);
    }

    @Override
    public StageModel getModel() {
        return model;
    }

    @Override
    public ScreenParent getParent() {
        return parent;
    }

    @Override
    public ScreenDimension getDimension() {
        return dimension;
    }
}
