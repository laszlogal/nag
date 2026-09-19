package hu.norbisquest.nagbase.game.target;

import com.google.gwt.resources.client.ImageResource;

import hu.norbisquest.nagbase.game.Stage;

public class ItemResource {
	public class PositionInStage {
		Stage.Id stageId;
		double x;
		double y;
		int zIndex;
		PositionInStage(Stage.Id id, double x, double y, int zIndex) {
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

	void addPositionInStage(Stage.Id id, double x, double y, int zIndex) {
        PositionInStage posInStage = new PositionInStage(id, x, y, zIndex);
	}
}
