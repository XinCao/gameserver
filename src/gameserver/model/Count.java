package gameserver.model;

/**
 *
 * @author caoxin
 */
public class Count {

    private int id;
    private int playerId;
    private int count; // 计数编号
    private int cur;
    private int max;

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
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public int getCur() {
        return this.cur;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}