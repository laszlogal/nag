package hu.norbisquest.nq1.client.factory;

import com.google.gwt.core.client.RunAsyncCallback;
import hu.norbisquest.nagbase.game.stage.StageCreator;

public abstract class StageAsyncCreator implements StageCreator, RunAsyncCallback {

    @Override
    public void onFailure(Throwable reason) {

    }

    @Override
    public void onSuccess() {
        createStage(createFactory());
    }
}
