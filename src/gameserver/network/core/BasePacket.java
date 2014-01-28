package gameserver.network.core;

import gameserver.model.player.Player;
import org.apache.mina.core.session.IoSession;
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
    protected IoSession ioSession;
    protected Player player;

    public void setAc(ApplicationContext ac) {
        this.ac = ac;
    }

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

    public IoSession getIoSession() {
        return ioSession;
    }

    public void setIoSession(IoSession ioSession) {
        this.ioSession = ioSession;
        if (ioSession != null) {
            Object o = ioSession.getAttribute("currentPlayer");
            if (o != null) {
                this.player = (Player) o;
            }
        }
    }
}