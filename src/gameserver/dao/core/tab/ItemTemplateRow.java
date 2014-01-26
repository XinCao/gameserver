package gameserver.dao.core.tab;

/**
 *
 * @author caoxin
 */
public class ItemTemplateRow extends BaseTableRow {

    private int id;
    private String name;
    private boolean isEquip;

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

    public boolean isIsEquip() {
        return isEquip;
    }

    public void setIsEquip(boolean isEquip) {
        this.isEquip = isEquip;
    }
}