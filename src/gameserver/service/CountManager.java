package gameserver.service;

import gameserver.model.Int3;
import gameserver.model.player.Player;
import java.util.Map;
import javolution.util.FastMap;
import gameserver.network.core.PacketKind;
import gameserver.network.core.PacketManager;
import gameserver.network.server.SM_COUNT_SYNC;
import java.util.EnumMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CountManager {

    private static Logger log = LoggerFactory.getLogger(CountManager.class);
    private Map<CountId, CountInfo> countMap = new EnumMap<CountId, CountInfo>(CountId.class);
    private final Player player;

    /**
     * 内部类，存放玩家最大次数和当前次数信息
     */
    public class CountInfo {

        public int max;
        public int cur;

        public CountInfo(int c, int m) {
            max = m;
            cur = c;
        }
    }

    public Map<CountId, CountInfo> getCountMap() {
        return countMap;
    }

    public CountManager(Player owner) {
        this.player = owner;
    }

    /**
     * 第几次了
     *
     * @param id
     * @return
     */
    public int getOrder(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                CountInfo ci = countMap.get(id);
                return ci.max - ci.cur;
            }
            return -1;
        } else {
            if (countMap.containsKey(id)) {
                CountInfo ci = countMap.get(id);
                return ci.cur;
            }
            return 0;
        }
    }

    /**
     * 重置一种计数
     *
     * @param id
     * @param max
     */
    public void resetCount(CountId id, int max) {
        int current = id.isUpperLimited() ? max : 0;
        countMap.put(id, new CountInfo(current, max));
    }

    public void setCount(CountId id, int cur) {
        CountInfo ci = countMap.get(id);
        ci.cur = cur;
        SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
        sm_count_sync.init(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max));
        player.sendPacket(sm_count_sync);
    }

    /**
     * 消耗一个计数（扣除模式）
     *
     * @param id
     */
    public void decCount(CountId id) {
        if (id.isUpperLimited()) {
            if (hasCount(id)) {
                countMap.get(id).cur -= 1;
                if (id.isSync() && player != null) {
                    SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
                    sm_count_sync.init(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max));
                    player.sendPacket(sm_count_sync);
                }
            } else {
                log.error("企图消耗一种错误的Count " + id.value());
            }
        }
    }

    /**
     * 添加多个购买数 当前个 最大数都累加
     *
     * @param id
     * @param count
     * @return
     */
    public boolean addMultiBuyCount(CountId id, int count) {
        if (!countMap.containsKey(id)) {
            log.error("购买了一种错误的Count " + id.value());
            return false;
        }
        if (!id.isUpperLimited()) {
            countMap.get(id).max += count;
            if (id.isSync() && player != null) {
                SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
                sm_count_sync.init(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max));
                player.sendPacket(sm_count_sync);
            }
            return true;
        } else {
            countMap.get(id).max += count;
            countMap.get(id).cur += count;
            if (id.isSync() && player != null) {
                SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
                sm_count_sync.init(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max));
                player.sendPacket(sm_count_sync);
            }
            return true;
        }
    }

    /**
     * 增加一个计数（累加模式）
     *
     * @param id
     */
    public void addCount(CountId id) {
        if (!id.isUpperLimited()) {
            if (notFull(id)) {
                countMap.get(id).cur += 1;
                if (id.isSync() && player != null) {
                    SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
                    sm_count_sync.init(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max));
                    player.sendPacket(sm_count_sync);
                }
            }
        }
    }

    /**
     * 获得最大次数（扣除模式）
     *
     * @param id
     * @return
     */
    public int getMaxCount(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                return countMap.get(id).max;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }
        }
        return 0;
    }

    /**
     * 获得剩余的次数（扣除模式）
     *
     * @param id
     * @return
     */
    public int getCount(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                return countMap.get(id).cur;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }
        }
        return 0;
    }

    /**
     * 获得计数信息
     *
     * @param id
     * @return
     */
    public CountInfo getCountInfo(CountId id) {
        return countMap.containsKey(id) ? countMap.get(id) : null;
    }

    /**
     * 是否还有计数可以消耗（扣除模式）
     *
     * @param id
     * @return
     */
    public boolean hasCount(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                if (countMap.get(id).cur > 0) {
                    return true;
                }
                return false;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }
        }

        return false;
    }

    /**
     * 测试的计数，是否充满（累加模式）
     *
     * @param id
     * @return
     */
    public boolean notFull(CountId id) {
        if (!id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                if (countMap.get(id).cur < countMap.get(id).max) {
                    return true;
                }
                return false;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }
        }
        return false;
    }
}