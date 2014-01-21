package mina.message;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class LoginServerMessage extends ServerMessage {

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