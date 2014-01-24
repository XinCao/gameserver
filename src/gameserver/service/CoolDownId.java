package gameserver.service;

/**
 *
 * @author caoxin
 */
public enum CoolDownId {

    CAST_TEST(0, false, false), FORCEITEM_CONTROL(100, true, true), // 强化cd
    CHAT_INTERVAL_LIMIT(101, false, false), // 聊天
    ONLINE_REWARD(102, true, true), // 持续在线奖励
    GET_PLAYER_INFO_INTERVAL_LIMIT(103, false, false), // 查询玩家信息限制
    QUESTION_ANSWER_CD_TIME(104, true, true), // 答题cd时间
    PVE_CD_TIME(105, true, true), // pve挑战CD时间
    ITEM_AND_SKILL_PROTOCOL_INTERVAL_LIMIT(106, false, false), // 强化，吞噬，提品，学技能等等，最小CD
    MINE_CRAFT_OCCUPY_LIMIT(107, true, true), // 金矿占领 CD
    MINE_CRAFT_ROB_LIMIT(108, true, true), // 金矿掠夺 CD
    FACTION_BATTLE_STRONGHOLD_PILLAGE_LIMIT(109, true, true), // 工会战据点掠夺 CD
    ;
    private int value; // 冷却id
    private boolean saveToDB; // 是否需要存盘
    private boolean syncToClient; // 是否需要同步给客户端

    private CoolDownId(int value, boolean saveToDB, boolean syncToClient) {
        this.value = value;
        this.saveToDB = saveToDB;
        this.syncToClient = syncToClient;
    }

    public int value() {
        return value;
    }

    public boolean isSave() {
        return saveToDB;
    }

    public boolean isSync() {
        return syncToClient;
    }

    public static CoolDownId toEnum(int id) {
        for (CoolDownId CDId : values()) {
            if (CDId.value() == id) {
                return CDId;
            }
        }
        return CAST_TEST;
    }
}