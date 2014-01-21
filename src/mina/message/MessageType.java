package mina.message;

/**
 *
 * @author caoxin
 */
public enum MessageType {

    SERVER("S"),
    CLIENT("C");
    private final String name;

    private MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}