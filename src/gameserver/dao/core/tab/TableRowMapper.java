package gameserver.dao.core.tab;

import java.util.List;

/**
 * 加载游戏配置表
 *
 * @author caoxin
 */
public interface TableRowMapper<T extends BaseTableRow> {

    public List<T> loadTableRow();
}