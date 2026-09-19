package hu.norbisquest.nagbase.common.stage;

import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.Actor;

public interface ActorList extends Destroyable {
    void draw(double timestamp, Layer foreground);

    void remove(Actor a);

    void removeAll();

    void add(Actor actor);

    boolean isValid();

    boolean onAvailable();
}
