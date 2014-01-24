package mina.server;

import mina.core.BaseServerPacket;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_LOGIN extends BaseServerPacket {

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        String str = "hello world!";
        int len = str.length();
        ioBuffer.putInt(len);
        for (int i = 0; i < len; i++) {
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