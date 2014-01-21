package mina.message;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class LoginClientMessage extends ClientMessage {

    @Override
    protected void readImp(ByteBuffer bytebuffer) {
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
    }
}