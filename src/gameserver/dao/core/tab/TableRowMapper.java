package gameserver.dao.core.tab;

import gameserver.service.TableService;
import java.util.Map;

/**
 * 加载游戏配置表
 *
 * @author caoxin
 */
public interface TableRowMapper<T extends BaseTableRow> {

    public Map<Integer, T> loadTableRow();
    public TableService.TableKind getTableKind();
}