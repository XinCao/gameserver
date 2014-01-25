package gameserver.log;

/**
 *
 * @author caoxin
 */
public interface LogService {

    public void save_item_log(int playerId, String action, int itemTplId, long amount, int channel, String extraData);
}