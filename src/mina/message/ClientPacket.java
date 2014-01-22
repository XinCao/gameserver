package mina.message;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public abstract class ClientPacket extends BasePacket {

    public void read(ByteBuffer byteBuffer) {
        readImp(byteBuffer);
    }

    abstract protected void readImp(ByteBuffer byteBuffer);
}