package gameserver.network.core;

/**
 *
 * @author caoxin
 */
public enum Status {

    LOGIN_GAME((short) -1),
    IN_GAME((short) 1),;
    private short value;

    private Status(short value) {
        this.value = value;
    }
    
    public short getValue() {
        return this.value;
    }
    
    public Status getStatus(int value) {
        for (Status s : values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}