package gameserver.core;

/**
 *
 * @author caoxin
 */
public class MailService {
    
    public void loadMail(MailBox mailBox) {
        int receiver = mailBox.getOwnerId();
        Mail mail = new Mail();
        mail.setReceiver(receiver);
        /**
         * go exec
         */
    }
}
