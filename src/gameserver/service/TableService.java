package gameserver.service;

import gameserver.dao.core.tab.BaseTableRow;
import gameserver.dao.core.tab.Table;
import gameserver.dao.core.tab.TableRowMapper;
import gameserver.service.TableService.TableKind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载配置表类（核心）
 * 
 * @author caoxin
 */
public class TableService {

    private static final Logger logger = LoggerFactory.getLogger(TableService.class);
    private static final Map<TableKind, Table<? extends BaseTableRow>> kindAndTable = new EnumMap<TableKind, Table<? extends BaseTableRow>>(TableKind.class);
    private Map<TableKind, TableRowMapper<? extends BaseTableRow>> kindAndDAO;

    public static enum TableKind {

        ITEM_TEMPLATE("item_template"),;
        private String name;
        public static final List<TableKind> tableKinds = new ArrayList<TableKind>();

        static {
            tableKinds.addAll(Arrays.asList(values()));
        }

        private TableKind(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static TableKind getTableKindByName(String name) {
            for (TableKind k : values()) {
                if (k.getName().equals(name)) {
                    return k;
                }
            }
            logger.debug("this name = {} table is not found!", name);
            return null;
        }
    }

    /**
     * 加载配置表，且，也可以更新配置表
     *
     * @param tableKind
     */
    public void loadTableByKind(TableKind tableKind) {
        if (!kindAndTable.containsKey(tableKind) && kindAndDAO.containsKey(tableKind)) {
            Table<BaseTableRow> table = new Table(tableKind);
            kindAndTable.put(tableKind, table);
            table.setTable(kindAndDAO.get(tableKind).loadTableRow());
        } else {
            logger.error("this kind = {} table is not found!, Maybe, you don't have it", tableKind.getName());
        }
    }

    /**
     * 加载所有配置表
     */
    public void loadAllTable() {
        for (TableKind k : TableKind.tableKinds) {
            this.loadTableByKind(k);
        }
    }

    /**
     * 获得配置表
     *
     * @param tableKind
     * @return
     */
    public Table<? extends BaseTableRow> getTable(TableKind tableKind) {
        if (kindAndTable.containsKey(tableKind)) {
            return kindAndTable.get(tableKind);
        } else {
            logger.error("this kind = {} table is not found in map!", tableKind.getName());
        }
        return null;
    }

    public void setKindAndDAO(Map<TableKind, TableRowMapper<? extends BaseTableRow>> kindAndDAO) {
        this.kindAndDAO = kindAndDAO;
    }
}