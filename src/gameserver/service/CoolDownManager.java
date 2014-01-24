package gameserver.service;

import gameserver.config.PlayerConfig;
import gameserver.entity.IntPair;
import gameserver.entity.Player;
import javolution.util.FastMap;
import mina.core.PacketKind;
import mina.core.PacketManagement;
import mina.server.SM_COOLDOWN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CoolDownManager {

    private static Logger log = LoggerFactory.getLogger(CoolDownManager.class);
    private FastMap<CoolDownId, Integer> coolmap = new FastMap<CoolDownId, Integer>().shared();
    private final Player player;
    private long longCDOperator = 0L;
    private long shortCDOperator = 0L;

    public FastMap<CoolDownId, Integer> getCoolMap() {
        return coolmap;
    }

    public CoolDownManager(Player player) {
        this.player = player;
    }

    /**
     * 设置一个冷却，单位是秒
     *
     * @param player
     * @param coolid
     * @param cooltime
     */
    public void setCoolDown(Player player, CoolDownId coolid, int cooltime) {
        coolmap.put(coolid, GameTime.getInstance().currentTimeSecond() + cooltime);
        if (coolid.isSync() && player != null) {
            SM_COOLDOWN cooldown = PacketManagement.getPacketByOpcode(PacketKind.SM_COOLDOWN.getOpcode());
            cooldown.init(player, new IntPair(coolid.value(), player.getCoolManager().getCoolDown(coolid)));
            player.sendPacket(cooldown);
        }
    }

    public int getCoolDown(CoolDownId coolid) {
        if (!coolmap.containsKey(coolid)) {
            setCoolDown(player, coolid, 0);
        }
        return coolmap.get(coolid);
    }

    /**
     * 测试是否在冷却中
     *
     * @param coolid
     * @return
     */
    public boolean inCoolDown(CoolDownId coolid) {
        if (coolmap.containsKey(coolid)) {
            if (coolmap.get(coolid) < GameTime.getInstance().currentTimeSecond()) {
                return false;
            }
            return true;
        } else {
            setCoolDown(player, coolid, 0);
            log.debug("企图测试一种不存在的CoolDown: name={}, id={}", coolid, coolid.value());
            return false;
        }
    }

    /**
     * 清除一个冷却
     *
     * @param player
     * @param coolid
     */
    public void clearCoolDown(Player player, CoolDownId coolid) {
        setCoolDown(player, coolid, 0);
    }

    public boolean checkCommonProtocolCD() {
        long curTimeMillis = GameTime.getInstance().currentTimeMillis();
        if (curTimeMillis < shortCDOperator) {
            return false;
        } else {
            shortCDOperator = curTimeMillis + PlayerConfig.player_common_protocol_cd;
            return true;
        }
    }

    public boolean checkLongProtocolCD() {
        long curTimeMillis = GameTime.getInstance().currentTimeMillis();
        if (curTimeMillis < longCDOperator) {
            return false;
        } else {
            longCDOperator = curTimeMillis + 5000;
            return true;
        }
    }
}