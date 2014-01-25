package gameserver.util.property.editor;

/**
 *
 * @author caoxin
 */
public class UseItemPair {

    private ItemPair itemPair;

    public ItemPair getItemPair() {
        return itemPair;
    }

    public void setItemPair(ItemPair itemPair) {
        this.itemPair = itemPair;
    }

    @Override
    public String toString() {
        return "UseItemPair{" + "itemPair=" + itemPair + '}';
    }
}