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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @author caoxin
 */
public class TableService implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TableService.class);
    private static final Map<TableKind, Table<? extends BaseTableRow>> kindAndTable = new EnumMap<TableKind, Table<? extends BaseTableRow>>(TableKind.class);
    private static final Map<TableKind, TableRowMapper<? extends BaseTableRow>> kindAndDAO = new EnumMap<TableKind, TableRowMapper<? extends BaseTableRow>>(TableKind.class);

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
    }

    /**
     * 加载配置表，且，也可以更新配置表
     *
     * @param tableKind
     */
    public void loadTableByKind(TableKind tableKind) {
        if (kindAndTable.containsKey(tableKind) && kindAndDAO.containsKey(tableKind)) {
            kindAndTable.get(tableKind).setTable(kindAndDAO.get(tableKind).loadTableRow());
        } else {
            logger.error("this kind = {} table is not found!, Maybe, you don't have it", tableKind.getName());
        }
    }

    /**
     * 加载所有配置表，且，也可以更新所有配置表
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

    @Override
    public Object postProcessBeforeInitialization(Object o, String string) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String string) throws BeansException {
        TableRowMapper tableRowMapper = null;
        if (TableRowMapper.class.isAssignableFrom(o.getClass())) {
            tableRowMapper = (TableRowMapper) o;
            kindAndDAO.put(tableRowMapper.getTableKind(), tableRowMapper);
        }

        return tableRowMapper;
    }
}