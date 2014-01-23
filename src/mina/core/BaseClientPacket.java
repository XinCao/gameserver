package mina.core;

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
}