package mina.message;

/**
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