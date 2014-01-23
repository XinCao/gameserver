package mina.message;

import org.apache.mina.core.buffer.IoBuffer;

public class IdelPacket extends BaseServerPacket {

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(1);
        ioBuffer.putInt(2);
    }

    @Override
    public void perform() {
         logger.debug("a heart beat!");
    }
}