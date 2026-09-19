package hu.norbisquest.nagbase.game.stage;

import hu.norbisquest.nagbase.common.stage.ActorList;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.Log;
import hu.norbisquest.nagbase.game.target.ActorTarget;

import java.util.ArrayList;
import java.util.List;

class StageActors extends NAGObject implements ActorList {
    private List<Actor> actors;

    private List<ActorTarget> actorTargets;

    StageActors(List<ActorTarget> actorTargets) {
        actors = new ArrayList<>();
        this.actorTargets = actorTargets;
    }

    @Override
    public void draw(double timestamp, Layer foreground) {
        drawActors(timestamp, foreground);
        drawActorTargets(timestamp);
    }

    private void drawActors(double timestamp, Layer foreground) {
        if (actors == null) {
            return;
        }

        for (Actor actor : actors) {
            if (actor.tick(timestamp)) {
                actor.drawTo(foreground);
            }
        }
    }

    private void drawActorTargets(double timestamp) {
        if (actorTargets == null) {
            return;
        }

        for (ActorTarget at : actorTargets) {
            if (at.tick(timestamp)) {
                at.draw();
            }
        }
    }

    @Override
    public void remove(Actor a) {
        if (actors.contains(a)) {
            Log.debug("[ACTOR] removing " + a.getName());
            actors.remove(a);
        }
    }

    @Override
    public void removeAll() {
        if (actorTargets == null || actorTargets.size() == 0) {
            return;
        }
        for (ActorTarget at : actorTargets) {
            at.destroy();
        }
        actors.clear();
    }


    @Override
    public void add(Actor actor) {
        actors.add(actor);
    }

    @Override
    public boolean isValid() {
        if (actorTargets != null) {
            for (ActorTarget at : actorTargets) {
                if (!at.isValid()) {
                    return false;
                }
            }
        }

        if (actors != null) {
            for (Actor actor : actors) {
                if (!actor.isValid()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean onAvailable() {
        if (actorTargets != null) {
            for (ActorTarget at : actorTargets) {
                at.onAvailable();
                at.getActor().addSubtitle();
            }
        }

        if (actors != null) {
            for (Actor actor : actors) {
                actor.addSubtitle();
            }
        }

        return true;
    }

    @Override
    public void doDestroy() {
        removeAll();
        actors = null;
    }
}
