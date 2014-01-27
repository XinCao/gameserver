package gameserver.dao.core.tab;

import gameserver.service.TableService;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author caoxin
 */
public class TableKindEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        TableService.TableKind tableKind = (TableService.TableKind) getValue();
        return tableKind.getName();
    }

    @Override
    public void setAsText(String name) {
        TableService.TableKind tableKind = TableService.TableKind.getTableKindByName(name);
        setValue(tableKind);
    }
}