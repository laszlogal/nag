package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.ResourceScope;

public final class ScreenHandle implements Destroyable {
    private final GameScreen screen;
    private final ResourceScope scope;
    private boolean destroyed = false;

    public ScreenHandle(GameScreen screen, ResourceScope scope) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen cannot be null");
        }

        if (scope == null) {
            throw new IllegalArgumentException("Scope cannot be null");
        }

        this.screen = screen;
        this.scope = scope;
    }

    @Override
    public void destroy() {
        if (destroyed) {
            return;
        }
        destroyed = true;
        scope.destroy();
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public GameScreen getScreen() {
        return screen;
    }
}
