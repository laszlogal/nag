package hu.norbisquest.nagbase.game.target;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.target.HotSpot.Id;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

	private Map<Id, InventoryItem> items;
	private InventoryItem selectedItem;
	private ItemFactory factory;

	private InventoryView view;

	public Inventory() {
		items = new HashMap<>();
	}

	public void put(InventoryItem item) {
		items.put(item.getId(), item);
		view.add(item);
	}

	public boolean has(Id id) {
		return items.containsKey(id);
	}

	public boolean isSelected(Id id) {
		return selectedItem.getId() == id;
	}

	public void remove(Id id) {
		view.remove(items.get(id));
		items.remove(id);
		if (selectedItem != null && isSelected(id)) {
			selectedItem = null;
		}
	}

	public Collection<InventoryItem> getItems() {
		return items.values();
	}

	public InventoryItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(InventoryItem selectedItem) {
		this.selectedItem = selectedItem;
		App.getCursor().setInventory(selectedItem);
	}

	public void setSelectedItem(Id id) {
		if (has(id)) {
			setSelectedItem(items.get(id));
		} else {
			App.warn("You don't have item " + id + " to select!");
		}
	}

	public void unselect() {
		if (selectedItem ==null) {
			return;
		}
		put(selectedItem);
		selectedItem = null;
	}

	public String toString() {
		return "items: " +
				App.comma(items.keySet()) +
				"\n";
	}

	public String getDescription() {
		return toString();
	}

	public JSONValue toJSONValue() {
		JSONArray array = new JSONArray();
		for (Id id : items.keySet()) {
			array.set(array.size(), new JSONString(id.toString()));
		}
		return array;
	}

	public void parseItems(JSONArray array) {
		for (int i = 0; i < array.size(); i++) {
			JSONValue item = array.get(i);
			put(factory.createItem(item.isString().stringValue()));
		}
	}

	public void setFactory(ItemFactory factory) {
		this.factory = factory;
	}

	public ItemFactory getFactory() {
		return factory;
	}

	public InventoryView getView() {
		return view;
	}

	public void setView(InventoryView view) {
		this.view = view;
	}
}

