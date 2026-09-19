package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.core.NAGColor;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.StageItem;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel;

import java.util.ArrayList;
import java.util.List;

public abstract class StageData {
	private List<HotSpot> hotSpots;

    public StageData() {
		setHotSpots(new ArrayList<>());
        List<StageItem> stageItems = new ArrayList<>();
	}

	public abstract ImageResource background();

	public abstract Id getId();

	public ImageResource maskTop() {
		return null;
	}

    public ImageResource maskBottom() {
		return null;
	}

	public ImageResource hotspots() {
		return null;
	}

	public DataResource music() {
		return null;
	}

	public DataResource ambientNoise() {
		return null;
	}

	public WalkGraphModel graphModel() {
		return null;
	}

	public int getBgXDiff() {
		return 0;
	}

	public abstract TextResource heroLines();

	public abstract List<DataResource> heroAudio();

	public void addHotSpot(HotSpot.Id id, int titleId, int red, int green, int blue) {
		addHotSpot(new HotSpot(id, titleId, new NAGColor(red, green, blue)));
	}

	public void addHotSpot(HotSpot.Id id, int titleId, NAGColor color, int lookX, int lookY) {
		addHotSpot(new HotSpot(id, titleId, color, lookX, lookY));
	}

	public void addHotSpot(HotSpot.Id id, int titleId, int lookX, int lookY) {
		addHotSpot(new HotSpot(id, titleId, null, lookX, lookY));
	}

	public void addHotSpotDisabled(HotSpot.Id id, int titleId, int red, int green, int blue) {
		HotSpot hotSpot = new HotSpot(id, titleId, new NAGColor(red, green, blue));
		hotSpot.setEnabled(false);
		addHotSpot(hotSpot);
	}

	private void addHotSpot(HotSpot hotSpot) {
		getHotSpots().add(hotSpot);
	}

	private List<HotSpot> getHotSpots() {
		 return hotSpots;
	}

	private void setHotSpots(List<HotSpot> hotSpots) {
		this.hotSpots = hotSpots;
	}

	public boolean autoPlayMusic() {
		// TODO Auto-generated method stub
		return false;
	}

}
