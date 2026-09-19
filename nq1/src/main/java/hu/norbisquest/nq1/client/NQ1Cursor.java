package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.game.Cursor;
import hu.norbisquest.nq1.client.data.bundle.Common;

class NQ1Cursor extends Cursor {
	public NQ1Cursor() {
		setNormal(Common.INSTANCE.cursor_normal(), 1, 1);
		setBusy(Common.INSTANCE.cursor_busy(), 1, 1);
		// Same as normal
		setWalk(Common.INSTANCE.cursor_normal(), 1, 1);
		setExamine(Common.INSTANCE.cursor_examine(), 12, 10);
		setUse(Common.INSTANCE.cursor_use(), 4, 0);
		setTalk(Common.INSTANCE.cursor_talk(), 2, 3);

	}

}
