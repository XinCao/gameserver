package gameserver.log;

import gameserver.dao.ItemLogMapper;
import gameserver.model.ItemLog;

/**
 *
 * @author caoxin
 */
public class LogServiceImplementWithDatabase implements LogService {

    private ItemLogMapper itemLogMapper;

    @Override
    public void save_item_log(int playerId, String action, int itemTplId, long amount, int channel, String extraData) {
        ItemLog itemLog = new ItemLog();
        itemLog.setPlayerId(playerId);
        itemLog.setAction(action);
        itemLog.setItemTplId(itemTplId);
        itemLog.setAmount(amount);
        itemLog.setChannel(channel);
        itemLog.setExtraData(extraData);
        itemLogMapper.insertItemLog(itemLog);
    }

    public void setItemLogMapper(ItemLogMapper itemLogMapper) {
        this.itemLogMapper = itemLogMapper;
    }
}