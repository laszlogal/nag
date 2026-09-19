package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.ItemResource;
import hu.norbisquest.nq1.client.NQ1Texts;
import hu.norbisquest.nq1.client.data.bundle.Inventory;

public class InventoryModel {
	public enum Ids implements HotSpot.Id {
		SLEEVE_KEY, SLEEVE, TOILET_BRUSH, TOILET_PAPER, TOILET_CHAIN, TICKET, USED_TICKET, CIGARETTE, SNIPS, MONEY, ALCOHOL, SANDWITCH, NEWSPAPER, FUSE;

		public static InventoryModel.Ids as(hu.norbisquest.nagbase.game.target.HotSpot.Id id) {
			return (InventoryModel.Ids) id;
		}
	}

	public static ItemResource sleeve_key() {
		ItemResource res = new ItemResource();
		res.id = Ids.SLEEVE_KEY;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.sleeve_key();
		res.titleId = NQ1Texts.items_sleeve_key;
		res.cursorHotSpotX = 25;
		res.cursorHotSpotY = 10;
		return res;
	}

	public static ItemResource sleeve() {
		ItemResource res = new ItemResource();
		res.id = Ids.SLEEVE;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.sleeve();
		res.titleId = NQ1Texts.items_sleeve;
		res.cursorHotSpotX = 14;
		res.cursorHotSpotY = 1;
		return res;
	}

	public static ItemResource toilet_brush() {
		ItemResource res = new ItemResource();
		res.id = Ids.TOILET_BRUSH;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.toilet_brush();
		res.titleId = NQ1Texts.items_toilet_brush;
		return res;
	}

	public static ItemResource toilet_paper() {
		ItemResource res = new ItemResource();
		res.id = Ids.TOILET_PAPER;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.toilet_paper();
		res.titleId = NQ1Texts.items_toilet_paper;
		return res;
	}

	public static ItemResource toilet_chain() {
		ItemResource res = new ItemResource();
		res.id = Ids.TOILET_CHAIN;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.toilet_chain();
		res.titleId = NQ1Texts.items_toilet_chain;
		return res;
	}

	public static ItemResource ticket() {
		ItemResource res = new ItemResource();
		res.id = Ids.TICKET;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.ticket();
		res.titleId = NQ1Texts.items_ticket;
		return res;
	}

	public static ItemResource used_ticket() {
		ItemResource res = new ItemResource();
		res.id = Ids.USED_TICKET;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.used_ticket();
		res.titleId = NQ1Texts.items_used_ticket;
		return res;
	}

	public static ItemResource cigarette() {
		ItemResource res = new ItemResource();
		res.id = Ids.CIGARETTE;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.cigarette();
		res.titleId = NQ1Texts.items_cigarette;
		return res;
	}

	public static ItemResource snips() {
		ItemResource res = new ItemResource();
		res.id = Ids.SNIPS;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.snips();
		res.titleId = NQ1Texts.items_snips;
		return res;
	}

	public static ItemResource money() {
		ItemResource res = new ItemResource();
		res.id = Ids.MONEY;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.money();
		res.titleId = NQ1Texts.items_money;
		return res;
	}

	public static ItemResource alcohol() {
		ItemResource res = new ItemResource();
		res.id = Ids.ALCOHOL;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.alcohol();
		res.titleId = NQ1Texts.items_alcohol;
		return res;
	}

	public static ItemResource newspaper() {
		ItemResource res = new ItemResource();
		res.id = Ids.NEWSPAPER;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.newspaper();
		res.titleId = NQ1Texts.items_newspaper;
		return res;
	}

	public static ItemResource sandwitch() {
		ItemResource res = new ItemResource();
		res.id = Ids.SANDWITCH;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.sandwitch();
		res.titleId = NQ1Texts.items_sandwitch;
		return res;
	}

	public static ItemResource fuse() {
		ItemResource res = new ItemResource();
		res.id = Ids.FUSE;
		res.visible = false;
		res.inventoryImage = Inventory.INSTANCE.fuse();
		res.titleId = NQ1Texts.items_fuse;
		return res;
	}
}
