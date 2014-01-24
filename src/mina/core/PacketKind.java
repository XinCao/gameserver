package mina.core;

import mina.server.SM_IDEL;
import mina.server.SM_LOGIN;
import mina.client.CM_LOGIN;
import java.util.HashMap;
import java.util.Map;
import mina.server.SM_COOLDOWN;
import mina.server.SM_COUNT_SYNC;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author caoxin
 */
public enum PacketKind {

    CM_LOGIN(Status.LOGIN_GAME, PacketType.CLIENT, (short) 0x01, CM_LOGIN.class),
    SM_LOGIN(Status.LOGIN_GAME, PacketType.SERVER, (short) 0x02, SM_LOGIN.class),
    SM_IDEL(Status.LOGIN_GAME, PacketType.SERVER, (short) 0x03, SM_IDEL.class),
    SM_COOLDOWN(Status.IN_GAME, PacketType.SERVER, (short) 0x4, SM_COOLDOWN.class),
    SM_COUNT_SYNC(Status.IN_GAME, PacketType.SERVER, (short) 0x5, SM_COUNT_SYNC.class),
    ;
    private static final Logger logger = LoggerFactory.getLogger(PacketKind.class);
    private Status status;
    private PacketType packetType;
    private short opcode;
    private Class<BasePacket> clazz;
    public static final Map<Short, BasePacket> packetKinds = new HashMap<Short, BasePacket>();

    public static BasePacket getPacketByOpcode(short messageId) {
        if (packetKinds.containsKey(messageId)) {
            BasePacket basePacket = packetKinds.get(messageId);
            try {
                return (BasePacket)(basePacket.clone());
            } catch (CloneNotSupportedException ex) {
                logger.error(ex.getMessage());
            }
        }
        return null;
    }

    static {
        for (PacketKind p : values()) {
            BasePacket baseMessage = null;
            try {
                baseMessage = p.clazz.newInstance();
                baseMessage.setPacketType(p.getPacketType());
                baseMessage.setStatus(p.getStatus());
                baseMessage.setOpcode(p.getOpcode());
            } catch (InstantiationException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }

            packetKinds.put(p.getOpcode(), baseMessage);
        }
    }

    private PacketKind(final Status status, final PacketType messageType, short messageId, Class clazz) {
        this.status = status;
        this.packetType = messageType;
        this.opcode = messageId;
        this.clazz = clazz;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public short getOpcode() {
        return opcode;
    }

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}