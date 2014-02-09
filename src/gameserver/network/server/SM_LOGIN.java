package gameserver.network.server;

import gameserver.network.core.BaseServerPacket;
import gameserver.network.core.PacketKind;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_LOGIN extends BaseServerPacket {

    private int flag = 0;

    public SM_LOGIN() {
        super(PacketKind.SM_LOGIN);
    }

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(flag);
    }
}