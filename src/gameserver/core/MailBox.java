package gameserver.core;

import java.util.List;

/**
 *
 * @author caoxin
 */
public class MailBox {

    private int ownerId;
    private List<Mail> mailList;

    public MailBox(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Mail> getMailList() {
        return mailList;
    }

    public void setMailList(List<Mail> mailList) {
        this.mailList = mailList;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}