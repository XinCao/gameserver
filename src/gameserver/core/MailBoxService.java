package gameserver.core;

/**
 *
 * @author caoxin
 */
public class MailBoxService {
    
    private MailService mailService;

    public void loadMailBox(Player player) {
        mailService.loadMail(player.getMailBox());
    }
}
