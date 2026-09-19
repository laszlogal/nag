package hu.norbisquest.nagbase.game;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.Widget;
import hu.norbisquest.nagbase.common.engine.GameEngine;
import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.game.gui.GameBuilder;
import hu.norbisquest.nagbase.game.gui.Toolbar;

public abstract class NAGGame implements EntryPoint {

	private GameGUI gui;
	private GameEngine engine;
	private final static int DEFAULT_FPS = 60;

	@Override
	public void onModuleLoad() {
        App.setGame(this);
        launchAsync();
	}

	private void launchAsync() {

        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onFailure(Throwable reason) {
                App.error("Failed to launch game");
            }

            @Override
            public void onSuccess() {
                launch();
            }
        });
	}

    private void launch() {
		GameBuilder builder = getBuilder();
 		builder.build(DEFAULT_FPS);
		gui = builder.getGui();
		engine = builder.getEngine();
		gui.resize();
		engine.run();

		Scheduler.get().scheduleDeferred(this::onLaunch);
	}

	protected abstract GameBuilder getBuilder();

	protected abstract void onLaunch();

	protected void addStyleName(String style) {
		gui.addStyleName(style);
	}

	void setStyleName(String style) {
		gui.setStyleName(style);
	}

	Screen getCurrentScreen() {
		return engine.getCurrentScreen();
	}

	Stage getCurrentStage() {
		return engine.getCurrentStage();
	}

	protected void add(Widget widget) {
		gui.add(widget);
	}

	public Toolbar getToolbar() {
		return gui.getToolbar();
	}

	public abstract void setUserZoomEnabled(boolean b);
}