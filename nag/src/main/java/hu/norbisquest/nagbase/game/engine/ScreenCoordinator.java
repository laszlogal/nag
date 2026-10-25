package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenNavigation;
import hu.norbisquest.nagbase.game.Stage;

/** Coordinates the game's single in-flight screen transition. */
public final class ScreenCoordinator implements ScreenNavigation {
    public enum State {
        READY,
        LOADING,
        STOPPED
    }

    private final ScreenFactory factory;
    private final ScreenHost host;
    private final ScreenStateStore stateStore;
    private final FatalErrorHandler fatalErrorHandler;

    private State state = State.READY;

    public ScreenCoordinator(ScreenFactory factory, ScreenHost host,
                             ScreenStateStore stateStore,
                             FatalErrorHandler fatalErrorHandler) {
        if (factory == null || host == null || stateStore == null
                || fatalErrorHandler == null) {
            throw new IllegalArgumentException(
                    "Screen coordinator dependencies must not be null");
        }
        this.factory = factory;
        this.host = host;
        this.stateStore = stateStore;
        this.fatalErrorHandler = fatalErrorHandler;
    }

    @Override
    public void navigateTo(Stage.Id id) {
        navigateTo(id, true);
    }

    @Override
    public void navigateTo(final Stage.Id id, final boolean clear) {
        if (state != State.READY) {
            return;
        }
        if (id == null) {
            failFatally(null, new IllegalArgumentException(
                    "Screen id must not be null"));
            return;
        }
        state = State.LOADING;
        try {
            host.pauseScreen();
            factory.create(id, this, new ScreenCreationCallback() {
                private boolean completed;
                private StagedScreen accepted;

                @Override
                public void onSuccess(StagedScreen screen) {
                    if (completed) {
                        if (screen != accepted) {
                            destroy(screen, null);
                        }
                        return;
                    }
                    completed = true;
                    accepted = screen;
                    accept(id, clear, screen);
                }

                @Override
                public void onFailure(Throwable cause) {
                    if (completed) {
                        return;
                    }
                    completed = true;
                    if (state == State.LOADING) {
                        failFatally(id, cause != null ? cause
                                : new IllegalStateException(
                                        "Screen creation failed without a cause"));
                    }
                }
            });
        } catch (Throwable cause) {
            if (state == State.LOADING) {
                failFatally(id, cause);
            }
        }
    }

    public State getState() {
        return state;
    }

    public void stop() {
        state = State.STOPPED;
    }

    private void accept(Stage.Id id, boolean clear, StagedScreen next) {
        if (state != State.LOADING) {
            destroy(next, null);
            return;
        }
        if (next == null || next.getScreen() == null) {
            Throwable failure = new IllegalStateException(
                    "Screen factory returned an empty staged screen");
            destroy(next, failure);
            failFatally(id, failure);
            return;
        }

        GameScreen nextScreen = next.getScreen();
        try {
            if (!nextScreen.prepare()) {
                throw new IllegalStateException(
                        "Screen refused to load: " + id);
            }

            GameScreen previous = host.getScreen();
            Stage.Id referer = previous == null ? null : previous.getId();

            next.activate(clear);
            host.replaceScreen(next);
            stateStore.commit(referer, id);
            state = State.READY;
        } catch (Throwable cause) {
            if (host.getScreen() != nextScreen) {
                destroy(next, cause);
            }
            failFatally(id, cause);
        }
    }

    private void failFatally(Stage.Id id, Throwable cause) {
        if (state == State.STOPPED) {
            return;
        }
        state = State.STOPPED;
        fatalErrorHandler.show(id, cause);
    }

    private void destroy(StagedScreen screen, Throwable primaryFailure) {
        if (screen == null) {
            return;
        }
        try {
            screen.destroy();
        } catch (Throwable cleanupFailure) {
            if (primaryFailure != null) {
                try {
                    primaryFailure.addSuppressed(cleanupFailure);
                } catch (Throwable ignored) {
                    // Reporting the transition failure takes priority.
                }
            }
        }
    }
}
