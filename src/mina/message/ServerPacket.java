package mina.message;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public abstract class ServerPacket extends BasePacket {

    public void write(IoBuffer ioBuffer) {
        writeImp(ioBuffer);
    }

    abstract protected void writeImp(IoBuffer ioBuffer);
}