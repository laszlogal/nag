package hu.norbisquest.nagbase.game.target;

public interface ItemFactory {
    InventoryItem createItem(String name);
    InventoryItem createItem(HotSpot.Id id);
    void fill();
}
