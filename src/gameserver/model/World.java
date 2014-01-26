package gameserver.model;

import gameserver.model.player.Player;
import java.util.Map;
import javolution.util.FastMap;

/**
 *
 * @author caoxin
 */
public class World {

    private static final Map<String, Player> players = new FastMap<String, Player>().shared();

    /**
     * 玩家进入世界
     *
     * @param player
     */
    public static void joinWorld(Player player) {
        if (World.players.containsKey(player.getKey())) {
            World.players.remove(player.getKey());
        }
        World.players.put(player.getKey(), player);
    }
}