package gameserver.dao;

import gameserver.dao.model.ItemLog;
import java.util.List;

/**
 *
 * @author caoxin
 */
public interface ItemLogMapper {

    public void insertItemLog(ItemLog itemLog);

    public void deleteItemLog(int playerId);

    public void updateItemLog(int playerId);

    public List<ItemLog> selectItemLog(ItemLog itemLog);
}