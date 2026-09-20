package hu.norbisquest.nq1.client.factory;

import com.google.gwt.core.client.RunAsyncCallback;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nagbase.game.stage.StageCreator;

public abstract class StageAsyncCreator implements StageCreator, RunAsyncCallback {
    private final ScreenParent parent;

    protected StageAsyncCreator(ScreenParent parent) {
        this.parent = parent;
    }

    @Override
    public void onFailure(Throwable reason) {
        parent.onScreenCreationFailed(reason);
    }

    @Override
    public void onSuccess() {
        try {
            createStage(createFactory());
        } catch (Throwable cause) {
            parent.onScreenCreationFailed(cause);
        }
    }
}
