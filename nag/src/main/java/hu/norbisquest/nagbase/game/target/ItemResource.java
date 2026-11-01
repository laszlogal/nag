package hu.norbisquest.nagbase.game.target;

import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.common.engine.ScreenId;

public class ItemResource {
	public class PositionInStage {
		ScreenId stageId;
		double x;
		double y;
		int zIndex;
		PositionInStage(ScreenId id, double x, double y, int zIndex) {
			this.stageId = id;
			this.x = x;
			this.y = y;
			this.zIndex = zIndex;

		}
	}

	public int titleId;
	public HotSpot.Id id;
	public boolean visible = true;
    public ImageResource image = null;
	public ImageResource inventoryImage;
	public int cursorHotSpotX = 0;
	public int cursorHotSpotY = 0;

	void addPositionInStage(ScreenId id, double x, double y, int zIndex) {
        PositionInStage posInStage = new PositionInStage(id, x, y, zIndex);
	}
}
