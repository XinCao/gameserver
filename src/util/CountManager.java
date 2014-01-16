package util;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CountManager implements Serializable {

    private static Logger log = LoggerFactory.getLogger(CountManager.class);
    private Map<CountId, CountInfo> countMap = new FastMap<CountId, CountInfo>();

    /**
     * 存放玩家最大次数和当前次数信息
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

    /**
     * 设置计数
     *
     * @param id
     * @param cur
     */
    public void setCount(CountId id, int cur) {
        CountInfo ci = countMap.get(id);
        ci.cur = cur;
//        PacketSendUtility.sendPacket(player, new SM_COUNT_SYNC(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max)));
    }

    /**
     * 消耗一个计数
     *
     * @param id
     */
    public void decCount(CountId id) {
        if (id.isUpperLimited()) {
            if (hasCount(id)) {
                countMap.get(id).cur -= 1;
//                if (id.isSync() && player != null) {
//                    PacketSendUtility.sendPacket(player, new SM_COUNT_SYNC(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max)));
//                }
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
//            if (id.isSync() && player != null) {
//                PacketSendUtility.sendPacket(player, new SM_COUNT_SYNC(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max)));
//            }
            return true;
        } else {
            countMap.get(id).max += count;
            countMap.get(id).cur += count;
//            if (id.isSync() && player != null) {
//                PacketSendUtility.sendPacket(player, new SM_COUNT_SYNC(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max)));
//            }
            return true;
        }
    }

    /**
     * 增加一个计数
     * 
     * @param id 
     */
    public void addCount(CountId id) {
        if (!id.isUpperLimited()) {
            if (notFull(id)) {
                countMap.get(id).cur += 1;
//                if (id.isSync() && player != null) {
//                    PacketSendUtility.sendPacket(player, new SM_COUNT_SYNC(new Int3(id.value(), countMap.get(id).cur, countMap.get(id).max)));
//                }
            } else {
                log.error("企图消耗一种错误的Count " + id.value());
            }
        }
    }

    public int getMaxCount(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                return countMap.get(id).max;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }

            return 0;
        }
        return 0;
    }

    public int getCount(CountId id) {
        if (id.isUpperLimited()) {
            if (countMap.containsKey(id)) {
                return countMap.get(id).cur;
            } else {
                log.error("企图测试一种不存在的Count " + id.value());
            }

            return 0;
        }
        return 0;
    }

    public CountInfo getCountInfo(CountId id) {
        return countMap.containsKey(id) ? countMap.get(id) : null;
    }

    /**
     * 是否还有计数可以消耗
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
            return false;
        } 
        return false;
    }

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

    static int VERSION = 0;

    public int save(ByteBuffer buf) {
        try {
            buf.clear();
            // 压入版本号
            buf.putInt(VERSION);
            buf.putInt(countMap.size());

            Iterator<CountId> it = countMap.keySet().iterator();
            while (it.hasNext()) {
                CountId countid = it.next();
                CountInfo info = countMap.get(countid);

                if (countid.isSave()) {
                    buf.putInt(countid.value());
                    buf.putInt(info.cur);
                    buf.putInt(info.max);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buf.position();
    }

    public void load(ByteBuffer buf) {
        try {
            int version = buf.getInt();

            switch (version) {
                case 0:
                    load_version0(buf);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_version0(ByteBuffer buf) {
        int count = buf.getInt();

        for (int i = 0; i < count; ++i) {
            CountId countid = CountId.fromInt(buf.getInt());
            int cur = buf.getInt();
            int max = buf.getInt();

            if (countid == CountId.ENTER_PVP_TIMES) {
                if (max > 3000 || cur > 3000) {
                    // 不可能，临时修
                    max = 10;
                    cur = 10;
                }
            } else if (countid == CountId.CONTIUNE_LOGIN_REWORD) {
                max = Short.MAX_VALUE;
            } else if (countid == CountId.GOLDMEONY2MONEY) {
                if (max > 200 || cur > 200) {
                    max = 100;
                    cur = 100;
                }
            }
            countMap.put(countid, new CountInfo(cur, max));
        }
    }
}