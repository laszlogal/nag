package hu.norbisquest.nq1.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import hu.norbisquest.nagbase.core.Browser;
import hu.norbisquest.nagbase.game.App;

public class ButtonPanel extends FlowPanel implements ClickHandler {
    private ToggleButton fullScreenButton;
    private boolean toggled;
    public ButtonPanel() {
        createGUI();
        addStyleName("buttonPanel");
     }

    static ToggleButton createFloatToggleButton(ImageResource upImage, ImageResource downImage, ClickHandler handler) {
        ToggleButton button = new ToggleButton(new Image(upImage), new Image(downImage), handler);
        button.setStyleName("floatButton");
        return button;
    }

    static ToggleButton createFloatToggleButton(ClickHandler handler) {
        ToggleButton button = new ToggleButton();
        button.addClickHandler(handler);
        button.setStyleName("floatButton");
        return button;
    }

    private void createGUI() {
        fullScreenButton = createFloatToggleButton(Icons.get().toolbar_fullscreen(),
                Icons.get().toolbar_fullscreen_exit(), this);
        fullScreenButton.getElement().setAttribute("aria-label", "Toggle fullscreen");
        fullScreenButton.getElement().setAttribute("title", "Fullscreen");
        add(fullScreenButton);
    }


    @Override
    public void onClick(ClickEvent event) {
        Object source = event.getSource();
        if (source == fullScreenButton) {
            toggleFullscreen();
        }
    }

    private void toggleFullscreen() {
        Browser.toggleFullscreen();
        toggled = true;
    }

    private void toggleMusic() {
        boolean musicOn = App.getAudioManager().toggleMusic();
        App.getSettings().setMusicOn(musicOn);
        toggled = true;
    }

    public void update() {
        if (toggled) {
            toggled = false;
            return;
        }
        fullScreenButton.setDown(Browser.isFullscreen());
    }
}
