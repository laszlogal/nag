package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.TransitionEffect;

@FunctionalInterface
public interface ScreenTransitionPresenter {
    void present(TransitionEffect effect);
}
