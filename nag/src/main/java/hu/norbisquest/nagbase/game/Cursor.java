package hu.norbisquest.nagbase.game;

import hu.norbisquest.nagbase.game.target.InventoryItem;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Cursor {
	private class Pointer{
		private String url;
		private int hotSpotX;
		private int hotSpotY;

		Pointer(String url, int hotSpotX, int hotSpotY) {
			this.url = url;
			this.hotSpotX = hotSpotX;
			this.hotSpotY = hotSpotY;
			
		}

		Pointer(ImageResource res, int hotSpotX, int hotSpotY) {
			this(res.getSafeUri().asString(), hotSpotX, hotSpotY);	
		}
		
		
	}

	private Pointer normal;
	private Pointer busy;
	private Pointer examine;
	private Pointer use;
	private Pointer talk;
	private Pointer walk;
	private Pointer inventory;
	private Image cursorImage;
	protected Cursor() {
	}
	
	protected void setNormal(ImageResource res, int x, int y) {
		normal = new Pointer(res, x, y);
	}
	
	protected void setBusy(ImageResource res, int x, int y) {
		busy = new Pointer(res, x, y);
	}
	
	protected void setWalk(ImageResource res, int x, int y) {
		walk = new Pointer(res, x, y);
	}
	
	
	protected void setExamine(ImageResource res, int x, int y) {
		examine = new Pointer(res, x, y);
	}
	
	protected void setUse(ImageResource res, int x, int y) {
		use = new Pointer(res, x, y);
	}
	
	protected void setTalk(ImageResource res, int x, int y) {
		talk = new Pointer(res, x, y);
	}
	
	public void setInventory(InventoryItem item) {
		inventory = new Pointer(item.getImage().getUrl(),
				item.getCusorHotSpotX(),
				item.getCusorHotSpotY());
	}
	
	public void normal() {
		setCursor(getNormal());
	}

	public void busy() {
		setCursor(getBusy());
	}

	public void walk() {
		setCursor(walk);
	}

	public void examine() {
		setCursor(getExamine());
	}

	public void use() {
		setCursor(getUse());
	}

	public void talk() {
		setCursor(talk);
	}

	public void inventory() {
		setCursor(inventory);
	}
	
	private static void setCursor(Element elem, Pointer p) {
		elem.setAttribute("style",
                "cursor: url(" + p.url  + ") " + p.hotSpotX + " " + p.hotSpotY + ", auto");

	}
	
	private void setCursor(Pointer p) {
		setCursor(RootPanel.getBodyElement(), p);
		cursorImage = new Image(p.url);
	}

	public Image getImage() {
		return cursorImage;
	}

	private Pointer getNormal() {
		return normal;
	}

	private Pointer getBusy() {
		return busy;
	}

	private Pointer getExamine() {
		return examine;
	}

	private Pointer getUse() {
		return use;
	}

}
