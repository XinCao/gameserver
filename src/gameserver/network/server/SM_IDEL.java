package gameserver.network.server;

import gameserver.network.core.BaseServerPacket;
import gameserver.network.core.PacketKind;
import org.apache.mina.core.buffer.IoBuffer;

public class SM_IDEL extends BaseServerPacket {

    public SM_IDEL() {
        super(PacketKind.SM_IDEL);
    }

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(1);
        ioBuffer.putInt(2);
    }
}