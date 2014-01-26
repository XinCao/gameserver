package gameserver.dao.core.tab;

import gameserver.service.TableService;
import org.mybatis.spring.mapper.MapperFactoryBean;

/**
 *
 * @author caoxin
 */
public class MapperFactoryBeanOnlyUseTable extends MapperFactoryBean {

    private TableService.TableKind tableKind;

    public TableService.TableKind getTableKind() {
        return tableKind;
    }

    public void setTableKind(TableService.TableKind tableKind) {
        this.tableKind = tableKind;
    }
}