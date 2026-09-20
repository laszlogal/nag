package hu.norbisquest.nagbase.game.engine;

import hu.norbisquest.nagbase.common.engine.GameScreen;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.game.Stage;

/** Owns asynchronous screen creation and commits only the latest successful request. */
public final class ScreenCoordinator implements ScreenListener {
    public enum State {
        EMPTY,
        LOADING,
        ACTIVE,
        FAILED,
        STOPPED
    }

    private final ScreenFactory factory;
    private final ScreenHost host;
    private final ScreenStateStore stateStore;

    private long generation;
    private State state = State.EMPTY;

    public ScreenCoordinator(ScreenFactory factory, ScreenHost host, ScreenStateStore stateStore) {
        if (factory == null || host == null || stateStore == null) {
            throw new IllegalArgumentException("Screen coordinator dependencies must not be null");
        }
        this.factory = factory;
        this.host = host;
        this.stateStore = stateStore;
    }

    @Override
    public void changeScreen(Stage.Id id) {
        changeScreen(id, true);
    }

    @Override
    public void changeScreen(final Stage.Id id, final boolean clear) {
        if (state == State.STOPPED) {
            return;
        }

        // Every request invalidates earlier asynchronous work, including a rejected request.
        final long requestGeneration = ++generation;
        if (id == null) {
            failCurrentRequest(id, new IllegalArgumentException("Screen id must not be null"));
            return;
        }

        state = State.LOADING;

        try {
            factory.create(id, new ScreenCreationCallback() {
                private boolean completed;

                @Override
                public void onSuccess(ScreenCandidate candidate) {
                    if (completed) {
                        discard(candidate);
                        return;
                    }
                    completed = true;
                    accept(requestGeneration, id, clear, candidate);
                }

                @Override
                public void onFailure(Throwable cause) {
                    if (completed) {
                        return;
                    }
                    completed = true;
                    fail(requestGeneration, id, cause);
                }
            });
        } catch (Throwable cause) {
            fail(requestGeneration, id, cause);
        }
    }

    public State getState() {
        return state;
    }

    public void stop() {
        generation++;
        state = State.STOPPED;
    }

    private void accept(long requestGeneration, Stage.Id id, boolean clear,
                        ScreenCandidate candidate) {
        if (!isCurrent(requestGeneration)) {
            discard(candidate);
            return;
        }
        if (candidate == null || candidate.getScreen() == null) {
            fail(requestGeneration, id,
                    new IllegalStateException("Screen factory returned an empty candidate"));
            if (candidate != null) {
                candidate.discard();
            }
            return;
        }

        GameScreen next = candidate.getScreen();
        try {
            next.setListener(this);
            if (!next.load()) {
                throw new IllegalStateException("Screen refused to load: " + id);
            }

            GameScreen previous = host.getScreen();
            Stage.Id referer = previous == null ? null : previous.getId();

            candidate.activate(clear);
            host.replaceScreen(next);
            stateStore.commit(referer, id);
            state = State.ACTIVE;
        } catch (Throwable cause) {
            if (host.getScreen() != next) {
                destroy(next);
                candidate.discard();
            }
            fail(requestGeneration, id, cause);
        }
    }

    private void fail(long requestGeneration, Stage.Id id, Throwable cause) {
        if (!isCurrent(requestGeneration)) {
            return;
        }
        failCurrentRequest(id, cause);
    }

    private void failCurrentRequest(Stage.Id id, Throwable cause) {
        state = host.getScreen() == null ? State.FAILED : State.ACTIVE;
        host.onScreenCreationFailed(id, cause != null ? cause
                : new IllegalStateException("Screen creation failed without a cause"));
    }

    private boolean isCurrent(long requestGeneration) {
        return state != State.STOPPED && requestGeneration == generation;
    }

    private void discard(ScreenCandidate candidate) {
        if (candidate == null) {
            return;
        }
        destroy(candidate.getScreen());
        candidate.discard();
    }

    private void destroy(GameScreen screen) {
        if (screen != null && !screen.isDestroyed()) {
            screen.destroy();
        }
    }
}
