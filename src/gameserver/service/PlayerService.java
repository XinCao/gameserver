package gameserver.service;

import gameserver.dao.PlayerMapper;
import gameserver.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author caoxin
 */
public class PlayerService {
    
    @Autowired
    private PlayerMapper playerMapper;

    public void initPlayer(Player player) {
    }
}