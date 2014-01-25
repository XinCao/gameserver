package gameserver.service;

import gameserver.config.PlayerConfig;
import gameserver.entity.IntPair;
import gameserver.entity.Player;
import javolution.util.FastMap;
import gameserver.network.core.PacketKind;
import gameserver.network.core.PacketManager;
import gameserver.network.server.SM_COOLDOWN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CoolDownManager {

    private static Logger logger = LoggerFactory.getLogger(CoolDownManager.class);
    private FastMap<CoolDownId, Integer> coolmap = new FastMap<CoolDownId, Integer>();
    private final Player onwer;
    private long longCDOperator = 0L;
    private long shortCDOperator = 0L;

    public FastMap<CoolDownId, Integer> getCoolMap() {
        return coolmap;
    }

    public CoolDownManager(Player onwer) {
        this.onwer = onwer;
    }

    /**
     * 设置一个冷却，单位是秒
     *
     * @param player
     * @param coolid
     * @param cooltime
     */
    public void setCoolDown(CoolDownId coolid, int cooltime) {
        coolmap.put(coolid, GameTime.getInstance().currentTimeSecond() + cooltime);
        if (coolid.isSync() && onwer != null) {
            SM_COOLDOWN sm_cooldown = PacketManager.getPacketByOpcode(PacketKind.SM_COOLDOWN.getOpcode());
            sm_cooldown.init(this.onwer, new IntPair(coolid.value(), onwer.getCoolManager().getCoolDown(coolid)));
            onwer.sendPacket(sm_cooldown);
        }
    }

    public int getCoolDown(CoolDownId coolid) {
        if (!coolmap.containsKey(coolid)) {
            setCoolDown(coolid, 0);
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
            setCoolDown(coolid, 0);
            logger.debug("企图测试一种不存在的CoolDown: name={}, id={}", coolid, coolid.value());
            return false;
        }
    }

    /**
     * 清除一个冷却
     *
     * @param player
     * @param coolid
     */
    public void clearCoolDown(CoolDownId coolid) {
        setCoolDown(coolid, 0);
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