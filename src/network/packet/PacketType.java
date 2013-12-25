package network.packet;

/**
 * 协议包类型
 * 
 * @author caoxin
 */
public enum PacketType {

    SERVER("S"),
    CLIENT("C");
    private final String name;

    private PacketType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}