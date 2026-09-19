package hu.norbisquest.nq1.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import hu.norbisquest.nagbase.common.gui.DialogManager;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.gui.MenuPanel;

public class BurgerMenu extends FlowPanel implements MenuPanel {
    private MenuBar mainMenu;
    private boolean showing = false;
    private static final ItemTemlates TEMPLATES = GWT.create(ItemTemlates.class);
    private MenuItem mnuOn;
    private MenuItem mnuOff;
    private DialogManager dialogManager;

    interface ItemTemlates extends SafeHtmlTemplates {
        @Template("<div class=\"menuItem\"><img src=\"{0}\" draggable=\"false\"/>{1}</div>")
        SafeHtml item(SafeUri url, String text);
    }

    public BurgerMenu(DialogManager dialogManager) {
        this.dialogManager = dialogManager;
        addStyleName("burgerMenu");
        addStyleName("hidden");
        getElement().setAttribute("aria-hidden", "true");
        createMenu();
    }

    private SafeHtml itemHTML(ImageResource res, String text) {
        return TEMPLATES.item(res.getSafeUri(), text);
    }

    private void createMenu() {
        mainMenu = new MenuBar(true);
        createBurgerMenu();
        createLoadMenu();
        createSaveMenu();
        createAudioMenu();
        createExitMenu();
        add(mainMenu);
    }

    private void createBurgerMenu() {
        addMenuItem(Icons.get().burger_white(), "Close menu", this::onMenu);
    }

    private void createLoadMenu() {
        addMenuItem(Icons.get().burger_load(), "Load game", this::onLoadGame);
    }

    private void createSaveMenu() {
        addMenuItem(Icons.get().burger_save(), "Save game", this::onSaveGame);
    }

    private void createAudioMenu() {
        createToggleMenu(Icons.get().toolbar_music_on(), Icons.get().toolbar_music_off(),
                this::musicOn, this::musicOn);
    }

    private void createExitMenu() {
        addMenuItem(Icons.get().burger_exit(), "Exit game", this::onExitGame);
    }

    private MenuItem addMenuItem(ImageResource icon, String label, Scheduler.ScheduledCommand command) {
        MenuItem item = new MenuItem(itemHTML(icon, ""), command);
        item.getElement().setAttribute("aria-label", label);
        item.getElement().setAttribute("title", label);
        mainMenu.addItem(item);
        return item;
    }

    private void createToggleMenu(ImageResource on, ImageResource off, Scheduler.ScheduledCommand cmdOn, Scheduler.ScheduledCommand cmdOff) {
        mnuOn = new MenuItem(itemHTML(on, ""));
        mnuOff = new MenuItem(itemHTML(off, ""));
        mnuOn.getElement().setAttribute("aria-label", "Turn music off");
        mnuOn.getElement().setAttribute("title", "Music on");
        mnuOff.getElement().setAttribute("aria-label", "Turn music on");
        mnuOff.getElement().setAttribute("title", "Music off");
        mainMenu.addItem(mnuOn);
        mainMenu.addItem(mnuOff);
        boolean musicOn = App.getSettings().isMusicOn();
        mnuOn.setVisible(musicOn);
        mnuOff.setVisible(!musicOn);
        mnuOn.setScheduledCommand(() -> {
            if (cmdOn != null) {
                cmdOn.execute();
            }
            mnuOn.setVisible(false);
            mnuOff.setVisible(true);
        });


        mnuOff.setScheduledCommand(() -> {
            if (cmdOff != null) {
                cmdOff.execute();
            }
            mnuOn.setVisible(true);
            mnuOff.setVisible(false);
        });
    }

    @Override
    public void show() {
        App.block();
        showing = true;
        removeStyleName("hidden");
        getElement().setAttribute("aria-hidden", "false");
    }

    @Override
    public void hide() {
        if (!isShowing()) {
            return;
        }
        App.unblock();
        showing = false;
        addStyleName("hidden");
        getElement().setAttribute("aria-hidden", "true");
    }

    @Override
    public void toggle() {
        if (showing) {
            hide();
        } else {
            update();
            show();
        }
    }

    private void update() {
        boolean musicOn = App.getAudioManager().isMusicPlaying();
        mnuOn.setVisible(musicOn);
        mnuOff.setVisible(!musicOn);
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    private void musicOn() {
        App.getAudioManager().toggleMusic();
    }


    private void musicOff() {
        App.getAudioManager().setMusicOn(false);
    }

    private void onMenu() {
        hide();
    }

    private void onLoadGame() {
        hide();
        dialogManager.showLoadDialog();
    }

    private void onSaveGame() {
        hide();
        dialogManager.showSaveDialog();
    }

    private void onExitGame() {
        hide();
        dialogManager.showExitDialog();
    }
}
