package hu.norbisquest.nagbase.core;

import java.util.ArrayList;
import java.util.List;

public final class ResourceScope implements Destroyable {
    private final List<Destroyable> resources = new ArrayList<>();
    private boolean destroyed = false;
    public void register(Destroyable destroyable) {
        if (destroyed) {
            throw new IllegalStateException("This resource is destroyed;" +
                    " register is not possible");
        }

        if (destroyable == null) {
            throw new IllegalArgumentException("Resource cannot be null");
        }

        if (destroyable.isDestroyed()) {
            throw new IllegalArgumentException("You cannot register a destroyed resource");
        }

        if (destroyable == this) {
            throw new IllegalArgumentException("You cannot register the scope itself");
        }
        if (isRegistered(destroyable)) {
            throw new IllegalArgumentException("Resource is already registered");
        }

        resources.add(destroyable);
    }

    private boolean isRegistered(Destroyable candidate) {
        for (Destroyable resource : resources) {
            if (resource == candidate) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        if (destroyed) {
            return;
        }

        destroyed = true;

        Throwable firstFailure = null;

        for (int i = resources.size() - 1; i >= 0; i--) {
            Destroyable resource = resources.remove(i);
            try {
                resource.destroy();
            } catch (Throwable failure) {
                if (firstFailure == null) {
                    firstFailure = failure;
                } else {
                    try {
                        firstFailure.addSuppressed(failure);
                    } catch (Throwable ignored) {
                        // Cleanup must continue even if recording fails.
                    }
                }
            }
        }

        if (firstFailure != null) {
            rethrow(firstFailure);
        }
    }

    private static void rethrow(Throwable failure) {
        if (failure instanceof RuntimeException) {
            throw (RuntimeException) failure;
        }
        if (failure instanceof Error) {
            throw (Error) failure;
        }
        throw new RuntimeException(failure);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public int resourceCount() {
        return resources.size();
    }
}
