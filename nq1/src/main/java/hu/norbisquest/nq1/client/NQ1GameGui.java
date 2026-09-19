package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.game.gui.GameGuiSimple;
import hu.norbisquest.nagbase.game.gui.MenuPanel;
import hu.norbisquest.nagbase.game.gui.Toolbar;
import hu.norbisquest.nq1.client.gui.BurgerMenu;
import hu.norbisquest.nq1.client.gui.NQ1Toolbar;


public class NQ1GameGui extends GameGuiSimple {
    NQ1GameGui(DialogManager dialogManager) {
        super(dialogManager);
    }

    @Override
    public MenuPanel createMenu() {
        return new BurgerMenu(this.getDialogManager());
    }

    @Override
    protected Toolbar createToolBar() {
        return new NQ1Toolbar(getDialogManager(), this);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }
}
