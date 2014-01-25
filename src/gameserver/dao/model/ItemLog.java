package gameserver.dao.model;

/**
 *
 * @author caoxin
 */
public class ItemLog {

    private int playerId;
    private String action;
    private int itemTplId;
    private long amount;
    private int channel;
    private String extraData;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getItemTplId() {
        return itemTplId;
    }

    public void setItemTplId(int itemTplId) {
        this.itemTplId = itemTplId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}