package gameserver.dao.core.tab;

/**
 *
 * @author caoxin
 */
public class ItemTemplateRow extends BaseTableRow {

    private int id;
    private String name;
    private int isEquip;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquip() {
        return isEquip == 1;
    }

    public void setIsEquip(int isEquip) {
        this.isEquip = isEquip;
    }
}