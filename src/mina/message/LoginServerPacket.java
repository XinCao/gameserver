package mina.message;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class LoginServerPacket extends BaseServerPacket {

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
    }
}