package gameserver.model;

import gameserver.util.math.Weight;

/**
 *
 * @author caoxin
 */
public class Item implements Weight {

    private int itemId;
    private int itemNum;
    private int weight;

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
}