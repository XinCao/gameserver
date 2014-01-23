package mina.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public abstract class BasePacket implements Cloneable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private short opcode;
    protected ApplicationContext ac;
    private PacketType packetType;
    private Status status;

    public void setAc(ApplicationContext ac) {
        this.ac = ac;
    }

    public boolean canPerform() {
        return true;
    }

    abstract public void perform();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public short getOpcode() {
        return opcode;
    }

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}