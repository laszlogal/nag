package hu.norbisquest.nagbase.core;

import hu.norbisquest.nagbase.game.App;

public class NAGObject implements Destroyable {
	private boolean destroyed;
	protected NAGObject() {destroyed=false;}
	protected String getPrefix(){return  "";}
	protected void log(final String msg) {
		App.debug("["  + getPrefix() + "] "+ msg);

	}
	public boolean isValid() {return !destroyed;}

	@Override
	public boolean isDestroyed() {return destroyed;}


	@Override
    public final void destroy() {
		if (isDestroyed()) {
			return;
		}
		destroyed = true;
		destroyCore();
		doDestroy();
	}

	protected void doDestroy() {
	}

	protected void destroyCore() {
	}

}
