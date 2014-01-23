package mina.server;

import mina.core.BaseServerPacket;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class LoginServerPacket extends BaseServerPacket {

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        String str = "hello world!";
        for (int i = 0; i < str.length(); i++) {
            ioBuffer.putChar(str.charAt(i));
        }
        logger.debug(str);
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
    }
}