package gameserver.model;

/**
 *
 * @author caoxin
 */
public class CoolDown {

    private int id;
    private int playerId;
    private int count; // 类型编号
    private int cur; // 冷却苏醒时间
    private int value; // 对应CoolDownId中value,这个值可以通过购买来替换默认值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    public int getCur() {
        return cur;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}