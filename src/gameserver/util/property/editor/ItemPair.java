package gameserver.util.property.editor;

/**
 *
 * @author caoxin
 */
public class ItemPair {

    private int itemId;
    private int itemNum;

    public ItemPair() {}
    
    public ItemPair(int itemId, int itemNum) {
        this.itemId = itemId;
        this.itemNum = itemNum;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    @Override
    public String toString() {
        return "ItemPair{" + "itemId=" + itemId + ", itemNum=" + itemNum + '}';
    }
}