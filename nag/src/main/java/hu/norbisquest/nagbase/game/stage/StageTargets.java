package hu.norbisquest.nagbase.game.stage;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.CommandProcessor;
import hu.norbisquest.nagbase.common.game.target.Target;
import hu.norbisquest.nagbase.common.gui.HasZoom;
import hu.norbisquest.nagbase.common.stage.TargetList;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Log;
import hu.norbisquest.nagbase.game.target.Hits;
import hu.norbisquest.nagbase.game.target.TalkControl;
import hu.norbisquest.nagbase.game.target.TargetControl;

import java.util.ArrayList;
import java.util.List;

public class StageTargets implements TargetList {

    private List<Target> list = new ArrayList<>();
    private HasZoom zoomer;


    private CommandProcessor processor;

    StageTargets(HasZoom zoomer) {
        this.zoomer = zoomer;
    }
    @Override
    public void add(Target target) {
        list.add(target);
    }

    @Override
    public void add(int index, Target target) {
        list.add(index, target);
    }

    @Override
    public Target get(int x, int y) {
        Hits hits = new Hits();
        for (Target t : list) {
            if (t.isHit(x, y)) {
                hits.add(t);
            }
        }
        return hits.isEmpty() ? null : hits.getTopHit();

    }

    @Override
    public boolean handle(int x, int y) {
        Target target = get(x, y);
        if (target == null) {
            return false;
        } else {
            Log.debug("TARGET IS: " + target);
        }

        TargetControl ctrl = target.getControl();

        if (ctrl == null) {

            Log.warn("[STAGE] target " + target + " has no control yet");
            return false;
        }

        List<Command> cmds = null;
        switch (App.getMode()) {
            case EXAMINE:
                cmds = ctrl.onExamine(x, y);
                break;
            case TALK:

            case USE:
                if (ctrl instanceof TalkControl) {
                    zoomer.zoomOut();
                    cmds = ((TalkControl) ctrl).onTalk(x, y);

                } else {
                    zoomer.zoomOut();
                    cmds = ctrl.onUse(x, y);
                }
                break;
            case NORMAL:
            case WALK:
                cmds = ctrl.onWalk(x, y);
                break;
            case INVENTORY:
                zoomer.zoomOut();
                cmds = ctrl.onInventory(App.getInventory().getSelectedItem().getId());
                break;
            default:
                break;

        }

        if (cmds != null) {
            Log.debug("append commands");
            processor.addCommandList(cmds);
            return true;
        }

        return false;
    }

    public void setProcessor(CommandProcessor processor) {
        this.processor = processor;
    }

    public void setZoomer(HasZoom zoomer) {
        this.zoomer = zoomer;
    }
}
