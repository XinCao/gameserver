package gameserver.network.core;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class BaseServerPacket extends BasePacket {

    public BaseServerPacket(PacketKind packetKind) {
        this.status = packetKind.getStatus();
        this.packetType = packetKind.getPacketType();
        this.opcode = packetKind.getOpcode();
    }

    public void write(IoBuffer ioBuffer) {
        writeImp(ioBuffer);
    }

    protected void writeImp(IoBuffer ioBuffer) {
    }
}