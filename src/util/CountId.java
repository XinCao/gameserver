package util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author caoxin
 */
public enum CountId {

    CAST_TEST(0, -1, false, false, true),
    FORCEITEM_CONTROL(100, -1, true, false, true), // 强化次数控制
    GOLDMEONY2MONEY(101, -1, true, true, true), // 每天钻石兑换金币的次数
    ONLINE_REWORD(102, -1, true, true, true), // 持续在线奖励
    GOT_LOGIN_REWORD(103, -1, true, true, true), // 是否领取了登陆奖励
    CONTIUNE_LOGIN_REWORD(104, 0, true, true, false), // 连续登陆天数
    LOTTERY_COUNT(105, -1, true, true, true), // 转盘限制
    FREE_FORCE_ITEM(106, -1, true, true, true), // 免费强化次数
    FREE_REFRESH_ITEM(107, -1, true, true, true), // 免费刷新转盘物品次数
    SIGNIN_DAY_COUNT(108, 0, true, true, false), // 累计登陆天数
    INVITE_FRIEND_TIMES(109, -1, true, true, false), // 召唤好友次数
    ENTER_PVP_TIMES(110, -1, true, true, true), // 进入竞技场次数
    GOLDMEONY2FP(111, -1, true, true, true), // 每天钻石换体力次数
    QUESTION_ANSWER_COUNT(112, -1, true, true, false), // 每天可答题数量
    BUY_ENTER_NB_INTANCE_TIMES(113, -1, true, true, false), // 精英副本 购买次数
    BUY_REFRESH_LEADER_TIMES(114, -1, true, true, true), // 天关次数购买
    BUY_ENTER_PVP_TIMES(115, -1, true, true, false), // PVP次数购买
    BUY_LOTTERY_TIMES(116, -1, true, true, false), // 转盘次数购买
    BUY_LOTTERY_REFRESH_ITEM_TIMES(117, -1, true, true, false), // 转盘刷新物品购买
    SOME_FLOWER_TIMES(118, -1, true, true, true), // 送花次数
    FREE_REFRESH_LEADER_TIMES(119, -1, true, true, true), // 免费天关次数
    SHAKE_CHRISTMAS_TREE_TIMES(120, -1, true, true, true), // 圣诞树每天可摇的次数
    ITEM_FLUSH_QUALITY_TIMES(121, -1, true, true, true), // 装备提升品质次数

    FREE_OPEN_BOX_COUNT(122, -1, true, true, true), // 免费开宝箱次数
    CREAM_FREE_OPEN_BOX_COUNT(123, -1, true, true, true), // 免费开精英副本宝箱
    SIGNIN_REWARD_GET_COUNT(124, 0, true, true, false), // 累计奖励当前领取天数
    VIP_EVERYDAY_LOGIN_REWARD(125, -1, true, false, true), // vip每天登入奖励
    LOGIN_REWARD_ACTIVE(126, -1, true, false, true), // 玩家登入奖励活动
    LOTTERY2_FREE_COUNT(127, -1, true, true, true), // 新转盘免费刷新次数

    TONGTIANTA_ENTER_TIMES(128, -1, true, true, true), // 通天塔进入次数
    TONGTIANTA_BUY_ENTER_TIMES(129, -1, true, true, true), // 购买通天塔次数
    MINECRAFT_ENTER_TIMES(130, -1, true, true, true), // 金矿进入次数
    MINECRAFT_BUY_ENTER_TIMES(131, -1, true, true, true), // 购买金矿进入次数
    MINECRAFT_CHALLENGE_TIMES(132, -1, true, true, true), // 挑战金矿次数
    MINECRAFT_BUY_CHALLENGE_TIMES(133, -1, true, true, true), // 购买金矿次数
    MINECRAFT_ROB_TIMES(134, -1, true, true, true), // 掠夺金矿次数
    MINECRAFT_BUY_ROB_TIMES(135, -1, true, true, true), // 购买掠夺金矿次数

    LOTTERY2_DAY_LIMIT(136, -1, true, true, true), // 新转盘每日玩次数限制
    SIWANGDILAO_ENTER_TIMES(137, -1, true, true, true), // 死亡地牢进入次数
    SIWANGDILAO_BUY_ENTER_TIMES(138, -1, true, true, true), // 购买死亡地牢次数
    DAY_ACTIVITY_REWARD_COUNT(139, -1, true, true, false), // 日活跃领奖次数
    COUNT_ACTIVITY_REWARD_COUNT(140, 0, true, true, false), // 日活跃累计领奖次数

    ITEM_SLOT_FORCE(141, 0, true, true, false), // 强化成功累计次数
    ITEM_SLOT_QUALITY(142, 0, true, true, false), // 精炼成功累计次数
    ITEM_SLOT_EMBLEM(143, 0, true, true, false), // 宝石成功累计次数

    CONTIUNE_LOGIN_DAY_COUNT(144, 0, true, true, false), // 连续登陆天数
    LOGIN_DAY_COUNT(145, 0, true, true, false), // 累计登陆天数
    ROLE_CREATE_DAY(146, 0, true, true, false), // 角色创建时间
    // 以后往后加记得排好格式整齐一点。（梁振警）
    ;
    int value; // Count 编号
    int resetType; // 0 不重置 -1 每天重置
    boolean saveToDB; // 是否存盘
    boolean syncToClient; // 是否同步
    boolean isUpperLimited; // 模式，true是扣除，有初始上限。false是累加，无上限
    public static final int NO_RESET = 0;
    public static final int EVERY_DAY_RESET = -1;

    private CountId(int value, int resetType, boolean save, boolean sync, boolean isUpperLimited) {
        this.value = value;
        this.resetType = resetType;
        this.saveToDB = save;
        this.syncToClient = sync;
        this.isUpperLimited = isUpperLimited;
    }

    public boolean isUpperLimited() {
        return isUpperLimited;
    }

    public boolean isSave() {
        return saveToDB;
    }

    public boolean isSync() {
        return syncToClient;
    }

    public int getResetMode() {
        return resetType;
    }

    public int value() {
        return value;
    }

    private static final Map<Integer, CountId> enumMap = new HashMap<Integer, CountId>();

    static {
        for (CountId en : values()) {
            enumMap.put(en.value(), en);
        }
    }

    public static CountId fromInt(Integer intValue) throws IndexOutOfBoundsException {
        if (enumMap.containsKey(intValue)) {
            return enumMap.get(intValue);
        } else {
            throw new IndexOutOfBoundsException("Invalid enum value: " + intValue);
        }
    }
}