package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.App;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MultiLayer extends NAGObject implements Loadable {
	private Map<String, Layer> layerMap;
	private int left;
	private int top;
	private int width;
	private int height;
	private Panel parentPanel;

	protected MultiLayer() {}
	protected MultiLayer(int left, int top, int width, int height, FlowPanel parent) {
		super();
		layerMap = new HashMap<>();
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		parentPanel = parent;
	}

	public void addLayer(final String name, int zIndex) {
		Layer layer = new Layer();
		layer.setZIndex(zIndex);
		setLayer(name, layer);
	}

	public void setLayer(final String id, Layer layer) {
		layer.setSize(getWidth(), getHeight());
		layer.setPositionInPixels(left, top);
		layer.setId(id);
		layerMap.put(id, layer);
		parentPanel.add(layer.getCanvas());
	}

	protected Layer getLayer(final String id) {
		return layerMap != null ? layerMap.get(id):null;
	}

	public boolean hasLayer(final String id) {
		return layerMap.get(id) != null;
	}

	private void removeLayer(Layer layer) {
		if (layer instanceof Loadable) {
			App.debug("[LAYER] Unloading layer " + layer);
			((Loadable) layer).unload();
		}
		layerMap.remove(layer.getId());
		layer.getCanvas().removeFromParent();
	}

	protected void removeLayerById(final String id) {
		Layer layer = layerMap.get(id);
		if (layer == null) {
			App.warn("[LAYER] No such layer: " + id);
			return;
		}
		removeLayer(layer);
	}

	public void removeAll() {
		for (Layer layer : layerMap.values()) {
			removeLayer(layer);
		}
	}

	public void moveLeft(int diff) {
		for (Layer layer : getLayers()) {
			layer.moveLeft(diff);
		}
	}

	public void moveTop(int diff) {
		for (Layer layer : getLayers()) {
			layer.moveTop(diff);
		}
	}

	public void moveBy(int dx, int dy) {
		for (Layer layer : getLayers()) {
			layer.getCanvas().addStyleName("layerZoom");
			layer.moveLeft(dx);
			layer.moveTop(dy);
		}
	}

	private Collection<Layer> getLayers() {
		return layerMap.values();
	}

	public int getLeft() {
		Layer bg = layerMap.get("nag_background0");
		return bg != null ? bg.getLeft() : 0;
	}

	protected void setLeft(int value) {
		for (Layer layer : layerMap.values()) {
			if (layer != null) {
				layer.setLeft(value, Unit.PX);
			}
		}
	}

	public int getTop() {
		Layer bg = layerMap.get("nag_background0");
		return bg != null ? bg.getTop() : 0;
	}

	public void setTop(int value) {
		for (Layer layer : layerMap.values()) {
			if (layer != null) {
				layer.setTop(value, Unit.PX);
			}
		}
	}

	@Override
	public boolean load() {

		for (Layer layer : layerMap.values()) {
			if (layer instanceof Loadable) {
				((Loadable) layer).load();
			}
		}
		return true;
	}

	@Override
	public boolean unload() {
		if (layerMap == null) {
			return false;
		}
		for (Layer layer : layerMap.values()) {
			if (layer instanceof Loadable) {
				((Loadable) layer).unload();
			}
			layer = null;
		}
		layerMap.clear();
		layerMap = null;
		return true;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		for (Layer layer : layerMap.values()) {
			layer.setWidth(width);
		}
	}

	protected int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String getPrefix() {
		return getClass().getCanonicalName();
	}

	@Override
	public final void destroyCore() {
		for (Layer layer : layerMap.values()) {
			if (!layer.isPermanent()) {
				layer.destroy();
				layer = null;
			}
		}
		layerMap.clear();
		layerMap = null;
	}

}
