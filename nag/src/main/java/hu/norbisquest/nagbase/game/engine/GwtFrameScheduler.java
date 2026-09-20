package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.animation.client.AnimationScheduler;

public class GwtFrameScheduler implements FrameScheduler  {
    @Override
    public void requestFrame(FrameCallback callback) {
        AnimationScheduler.get().requestAnimationFrame(callback::onFrame);
    }
}
