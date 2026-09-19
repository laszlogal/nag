package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel;

import java.util.ArrayList;
import java.util.List;

public class StageModel extends NAGObject {
	private static int LEFT = 0;
	private static int TOP = 0;
	private static int WIDTH = 640;
	private static int HEIGHT = 400;

	public Id id;
	protected Id referer;
	public Id next;

	public ImageResource backround = null;
	public ImageResource maskTop = null;
	public ImageResource maskBottom = null;
	public ImageResource hotSpotMask = null;
	public DataResource music = null;
	public DataResource ambientNoise = null;
	public double scaleMin = 0;
	public double scaleMax = 0;
	public ImageResource walkArea = null;

	public Integer left = LEFT;
	public Integer top = TOP;
	public Integer height = HEIGHT;
	public Integer width = WIDTH;
	public List<HotSpot> hotSpots;
	public List<StageItem> stageItems;
	public WalkGraphModel graphModel = null;
	public boolean autoPlayMusic = true;
	public Integer bgXDiff = 0;
	public List<DataResource> heroAudio = null;
	public TextResource heroLines = null;
	public String graphData = null;

	protected StageModel() {
		hotSpots = new ArrayList<>();
		stageItems = new ArrayList<>();
	}

	@Deprecated
	public void addHotSpot(HotSpot.Id id, int titleId, int red, int green, int blue) {
		addHotSpot(new HotSpot(id, titleId, new NAGColor(red, green, blue)));
	}

	@Deprecated
	public void addHotSpot(HotSpot.Id id, int titleId, NAGColor color, int lookX, int lookY) {
		addHotSpot(new HotSpot(id, titleId, color, lookX, lookY));
	}

	protected void addHotSpot(HotSpot.Id id, int titleId, int lookX, int lookY) {
		addHotSpot(new HotSpot(id, titleId, null, lookX, lookY));
	}

	public void addHotSpotDisabled(HotSpot.Id id, int titleId, int red, int green, int blue) {
		HotSpot hotSpot = new HotSpot(id, titleId, new NAGColor(red, green, blue));
		hotSpot.setEnabled(false);
		addHotSpot(hotSpot);
	}

	private void addHotSpot(HotSpot hotSpot) {
		hotSpots.add(hotSpot);
	}

	protected void setGraph(TextResource res) {
		setGraph(res.getText());
	}
	protected void setGraph(String data) { graphData = data;}

	@Override
	protected void doDestroy() {
		backround = null;
		maskTop = null;
		maskBottom = null;
		hotSpotMask = null;
		music = null;
		ambientNoise = null;
		walkArea = null;
		hotSpots.clear();
		hotSpots = null;
		stageItems.clear();
		stageItems = null;
		graphModel = null;
		heroAudio = null;
		heroLines = null;
		graphData = null;
	}
}