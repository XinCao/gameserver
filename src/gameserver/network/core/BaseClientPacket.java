package gameserver.network.core;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public abstract class BaseClientPacket extends BasePacket {

    public void read(ByteBuffer byteBuffer) {
        readImp(byteBuffer);
    }

    abstract protected void readImp(ByteBuffer byteBuffer);

    public boolean canPerform() {
        return true;
    }

    abstract public void perform();
}