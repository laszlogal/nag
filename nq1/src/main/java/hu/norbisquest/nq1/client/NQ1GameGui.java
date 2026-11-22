package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.core.AudioManager;
import hu.norbisquest.nagbase.game.gui.GameGuiSimple;
import hu.norbisquest.nagbase.game.gui.MenuPanel;
import hu.norbisquest.nagbase.game.gui.Toolbar;
import hu.norbisquest.nq1.client.gui.BurgerMenu;
import hu.norbisquest.nq1.client.gui.NQ1Toolbar;


public class NQ1GameGui extends GameGuiSimple {
    NQ1GameGui(DialogManager dialogManager, AudioManager audioManager) {
        super(dialogManager, audioManager);
    }

    @Override
    public MenuPanel createMenu() {
        return new BurgerMenu(getDialogManager(), getAudioManager());
    }

    @Override
    protected Toolbar createToolBar() {
        return new NQ1Toolbar(getDialogManager(), this, getAudioManager());
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }
}
