package gameserver.core;

/**
 *
 * @author caoxin
 */
public class Player {

    private int playerId;
    private MailBox mailBox;

    public Player(int playerId) {
        this.playerId = playerId;
        mailBox = new MailBox(playerId);
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }
}