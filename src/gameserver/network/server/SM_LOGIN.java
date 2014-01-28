package gameserver.network.server;

import gameserver.network.core.BaseServerPacket;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_LOGIN extends BaseServerPacket {

    private int flag = 0;

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(flag);
    }

    public void init(int flag) {
        this.flag = flag;
    }
}