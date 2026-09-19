package hu.norbisquest.nagbase.game.walk;

public interface RouteInterface {
    boolean isValid();

    WalkPoint next();

    WalkPoint getPointAt(int index);

    void add(WalkPoint p);

    void add(WalkPoint p, WalkPoint q, String label);

    void clear();

    boolean isEmpty();

    boolean isSingle();

    boolean isReady();
    @Override
    String toString();

    WalkPoint getEnd();
}
