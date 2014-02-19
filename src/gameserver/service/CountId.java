package gameserver.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author caoxin
 */
public enum CountId {
    CAST_TEST(0, -1, false, false, true, 10),
    ;
    int count; // Count 编号
    int resetType; // 0 不重置 -1 每天重置
    boolean saveToDB; // 是否存盘
    boolean syncToClient; // 是否同步
    boolean isUpperLimited; // 模式，true是扣除，有初始上限。false是累加，无上限
    int value; // 计数的初始值
    public static final int NO_RESET = 0;
    public static final int EVERY_DAY_RESET = -1;

    private CountId(int count, int resetType, boolean save, boolean sync, boolean isUpperLimited, int value) {
        this.count = count;
        this.resetType = resetType;
        this.saveToDB = save;
        this.syncToClient = sync;
        this.isUpperLimited = isUpperLimited;
        this.value = value;
    }

    public boolean isUpperLimited() {
        return this.isUpperLimited;
    }

    public boolean isSave() {
        return this.saveToDB;
    }

    public boolean isSync() {
        return this.syncToClient;
    }

    public int getResetMode() {
        return this.resetType;
    }

    public int count() {
        return this.count;
    }
    
    public int value() {
        return this.value;
    }
    private static final Map<Integer, CountId> enumMap = new HashMap<Integer, CountId>();

    static {
        for (CountId en : values()) {
            enumMap.put(en.count(), en);
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