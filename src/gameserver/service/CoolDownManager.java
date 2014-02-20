package gameserver.service;

import gameserver.config.PlayerConfig;
import gameserver.model.CoolDown;
import gameserver.model.Int3;
import gameserver.model.player.Player;
import gameserver.network.server.SM_COOLDOWN;
import java.util.EnumMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CoolDownManager {

    private static Logger logger = LoggerFactory.getLogger(CoolDownManager.class);
    private Map<CoolDownId, CoolDownInfo> coolmap = new EnumMap<CoolDownId, CoolDownInfo>(CoolDownId.class);
    private final Player onwer;
    private long longCDOperator = 0L;
    private long shortCDOperator = 0L;

    public static class CoolDownInfo {

        public int cur;
        public int interval;

        public CoolDownInfo(int cur, int interval) {
            this.cur = cur;
            this.interval = interval;
        }
    }

    public Map<CoolDownId, CoolDownInfo> getCoolMap() {
        return coolmap;
    }

    public CoolDownManager(Player onwer) {
        this.onwer = onwer;
    }

    /**
     * 设置冷却
     * 
     * @param coolid
     * @return 
     */
    public void setCoolDown(CoolDownId coolid) {
        int curTimeSecond = GameTime.getInstance().currentTimeSecond();
        CoolDownInfo cooldownInfo;
        if (!coolmap.containsKey(coolid)) {
            cooldownInfo = new CoolDownInfo(coolid.interval() + curTimeSecond, coolid.interval());
            coolmap.put(coolid, cooldownInfo);
            return;
        } else {
            cooldownInfo = coolmap.get(coolid);
            cooldownInfo.cur = curTimeSecond + cooldownInfo.interval;   
        }
        if (coolid.isSync() && onwer != null) {
            SM_COOLDOWN sm_cooldown = new SM_COOLDOWN(this.onwer, new Int3(coolid.count(), cooldownInfo.cur, cooldownInfo.interval));
            onwer.sendPacket(sm_cooldown);
        }
    }

    /**
     * 设置冷却（仅在初始化冷却和对冷却进行重置时调用）
     *
     * @param coolid
     */
    public void resetCoolDown(CoolDownId coolid) {
        coolmap.put(coolid, new CoolDownInfo(0, coolid.interval()));
    }
    
    /**
     * 购买冷却时，调用
     * 
     * @param coolid
     * @param decS 减少的秒数
     */
    public void resetCoolDown(CoolDownId coolid, int decS) {
        coolmap.put(coolid, new CoolDownInfo(0, coolid.interval() > decS ? coolid.interval() - decS : 0));
    }

    /**
     * 获得冷却信息
     * 
     * @param coolid
     * @return 
     */
    public CoolDownInfo getCoolDown(CoolDownId coolid) {
        if (!coolmap.containsKey(coolid)) {
            this.setCoolDown(coolid);
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
            if (coolmap.get(coolid).cur < GameTime.getInstance().currentTimeSecond()) {
                return false;
            }
            return true;
        } else {
            resetCoolDown(coolid);
            logger.debug("企图测试一种不存在的CoolDown: name={}, id={}", coolid, coolid.count());
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
        this.resetCoolDown(coolid);
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