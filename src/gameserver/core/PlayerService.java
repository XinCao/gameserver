package gameserver.core;

/**
 *
 * @author caoxin
 */
public class PlayerService {
    
    private MailBoxService mailBoxService;
    public Player loadPlayer(int playerId) {
        Player player = new Player(playerId);
        mailBoxService.loadMailBox(player);
        return player;
    }
}
