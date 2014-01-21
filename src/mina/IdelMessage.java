package mina;

import mina.message.ServerMessage;
import org.apache.mina.core.buffer.IoBuffer;

public class IdelMessage extends ServerMessage {

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
    }

    @Override
    public void perform() {
    }
}