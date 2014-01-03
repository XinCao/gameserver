package gameserver.core;

/**
 *
 * @author caoxin
 */
public enum MailStatus {

    notRead(1),
    haveRead(2),
    haveReadButAll(3);
    private int status;

    private MailStatus(int status) {
        this.status = status;
    }

    public int getStatus(MailStatus mailStatus) {
        for (MailStatus temp : MailStatus.values()) {
            if (temp.getStatus() == mailStatus.getStatus()) {
                return temp.getStatus();
            }
        }
        return -1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}