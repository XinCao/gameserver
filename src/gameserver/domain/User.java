package gameserver.domain;

/**
 *
 * @author caoxin
 */
public class User {
    private String account;
    private String showName;
    private String roleName;
    private String createDate;
    private boolean valied;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isValied() {
        return valied;
    }

    public void setValied(boolean valied) {
        this.valied = valied;
    }
}
