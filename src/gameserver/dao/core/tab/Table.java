package gameserver.dao.core.tab;

import gameserver.service.TableService.TableKind;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class Table<T extends BaseTableRow> {

    private Logger logger = LoggerFactory.getLogger(Table.class);
    private TableKind tableKind;
    private Map<Integer, T> table;

    public Table(TableKind tableKind) {
        this.tableKind = tableKind;
    }

    public T getBaseTableRow(int id) {
        if (table.containsKey(id)) {
            return table.get(id);
        }
        logger.error("There is no id = {} column in table = {}", id, tableKind.getName());
        return null;
    }

    public void removeAllTable() {
        this.table.clear();
    }

    public void setTable(List<? extends BaseTableRow> list) {
        this.table = this.listToMap(list);
    }

    private Map<Integer, T> listToMap(List<? extends BaseTableRow> list) {
        Map<Integer, T> t = new HashMap<Integer, T>();
        for (BaseTableRow b : list) {
            t.put(b.getId(), (T)b);
        }
        return t;
    }
}