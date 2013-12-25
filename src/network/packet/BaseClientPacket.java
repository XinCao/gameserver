package network.packet;

import network.AConnection;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/**
 * 网络协议客户端基础包
 *
 * @author caoxin
 * @param <T>
 */
public abstract class BaseClientPacket<T extends AConnection> extends BasePacket implements Runnable {

    private static final Logger log = Logger.getLogger(BaseClientPacket.class);
    private T client;
    private ByteBuffer buf;

    public BaseClientPacket(ByteBuffer buf, int opcode) {
        this(opcode);
        this.buf = buf;
    }

    public BaseClientPacket(int opcode) {
        super(PacketType.CLIENT, opcode);
    }

    public void setBuffer(ByteBuffer buf) {
        this.buf = buf;
    }

    public void setConnection(T client) {
        this.client = client;
    }

    public final boolean read() {
        log.debug("Before try read");
        try {
            log.debug("Before readImpl()");
            readImpl();
            log.debug("After readImpl");
            if (getRemainingBytes() > 0) {
                log.debug("Packet " + this + " not fully readed!");
            }
            return true;
        } catch (Exception re) {
            log.error("Reading failed for packet " + this, re);
            return false;
        }
    }

    protected abstract void readImpl();

    public final int getRemainingBytes() {
        return buf.remaining();
    }

    protected final int readD() {
        try {
            return buf.getInt();
        } catch (Exception e) {
            log.error("Missing D for: " + this);
        }
        return 0;
    }

    protected final int readC() {
        try {
            return buf.get() & 0xFF;
        } catch (Exception e) {
            log.error("Missing C for: " + this);
        }
        return 0;
    }

    protected final int readH() {
        try {
            return buf.getShort() & 0xFFFF;
        } catch (Exception e) {
            log.error("Missing H for: " + this);
        }
        return 0;
    }

    protected final double readDF() {
        try {
            return buf.getDouble();
        } catch (Exception e) {
            log.error("Missing DF for: " + this);
        }
        return 0;
    }

    protected final float readF() {
        try {
            return buf.getFloat();
        } catch (Exception e) {
            log.error("Missing F for: " + this);
        }
        return 0;
    }

    protected final long readQ() {
        try {
            return buf.getLong();
        } catch (Exception e) {
            log.error("Missing Q for: " + this);
        }
        return 0;
    }

    protected final String readS() {
        StringBuilder sb = new StringBuilder();
        char ch;
        try {
            while ((ch = buf.getChar()) != 0) {
                sb.append(ch);
            }
        } catch (Exception e) {
            log.error("Missing S for: " + this);
        }
        return sb.toString();
    }

    protected final byte[] readB(int length) {
        byte[] result = new byte[length];
        try {
            buf.get(result);
        } catch (Exception e) {
            log.error("Missing byte[] for: " + this);
        }
        return result;
    }

    public final T getConnection() {
        return client;
    }

    abstract protected void canPerform();
}