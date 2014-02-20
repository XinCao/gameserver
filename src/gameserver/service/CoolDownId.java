package gameserver.service;

/**
 *
 * @author caoxin
 */
public enum CoolDownId {

    CAST_TEST(0, false, false, 1),
    ;
    private int count; // 冷却编号
    private boolean saveToDB; // 是否需要存盘
    private boolean syncToClient; // 是否需要同步给客户端
    private int interval; // 默认冷却间隔时间，单位（秒）

    private CoolDownId(int count, boolean saveToDB, boolean syncToClient, int interval) {
        this.count = count;
        this.saveToDB = saveToDB;
        this.syncToClient = syncToClient;
        this.interval = interval;
    }

    public int count() {
        return this.count;
    }

    public boolean isSave() {
        return this.saveToDB;
    }

    public boolean isSync() {
        return this.syncToClient;
    }
    
    public int interval() {
        return this.interval;
    }

    public static CoolDownId fromInt(int count) {
        for (CoolDownId CDId : values()) {
            if (CDId.count() == count) {
                return CDId;
            }
        }
        return CAST_TEST;
    }
}