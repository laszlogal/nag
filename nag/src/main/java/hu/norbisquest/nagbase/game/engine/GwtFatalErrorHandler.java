package hu.norbisquest.nagbase.game.engine;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Stage;

/** Displays a terminal error without loading another game screen. */
public final class GwtFatalErrorHandler implements FatalErrorHandler {
    private final FlowPanel parent;
    private final GameLoop gameLoop;
    private final GameDisplay display;

    public GwtFatalErrorHandler(FlowPanel parent, GameLoop gameLoop,
                                GameDisplay display) {
        if (parent == null || gameLoop == null || display == null) {
            throw new IllegalArgumentException(
                    "Fatal error handler dependencies must not be null");
        }
        this.parent = parent;
        this.gameLoop = gameLoop;
        this.display = display;
    }

    @Override
    public void show(Stage.Id failedScreen, Throwable cause) {
        gameLoop.stop();

        Throwable failure = cause != null ? cause
                : new IllegalStateException("Screen loading failed without a cause");
        try {
            display.destroy();
        } catch (Throwable cleanupFailure) {
            try {
                failure.addSuppressed(cleanupFailure);
            } catch (Throwable ignored) {
                // The fatal error UI must still be displayed.
            }
        }

        String details = failure.getMessage();
        App.error("Failed to load screen " + failedScreen + ": " + details);

        parent.clear();
        FlowPanel errorPanel = new FlowPanel();
        errorPanel.setStyleName("nag-fatal-error");
        errorPanel.setSize("100%", "100%");
        errorPanel.getElement().getStyle().setBackgroundColor("#111111");
        errorPanel.getElement().getStyle().setColor("#ffffff");
        errorPanel.getElement().getStyle().setPadding(24, Unit.PX);
        errorPanel.add(new Label("The next location could not be loaded."));

        Button reload = new Button("Reload game");
        reload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.Location.reload();
            }
        });
        errorPanel.add(reload);
        parent.add(errorPanel);
    }
}
