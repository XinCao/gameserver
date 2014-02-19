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
    private int value;

    private CoolDownId(int count, boolean saveToDB, boolean syncToClient, int value) {
        this.count = count;
        this.saveToDB = saveToDB;
        this.syncToClient = syncToClient;
        this.value = value;
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
    
    public int value() {
        return this.value;
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