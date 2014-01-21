package mina.message;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public abstract class ClientMessage extends BaseMessage {

    public void read(ByteBuffer byteBuffer) {
        readImp(byteBuffer);
    }

    abstract protected void readImp(ByteBuffer byteBuffer);
}