package hu.norbisquest.nagbase.game.engine;

@FunctionalInterface
public interface ScreenCreator {
    void create(ScreenParent parent);
}
