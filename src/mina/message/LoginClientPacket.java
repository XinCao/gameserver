package mina.message;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class LoginClientPacket extends ClientPacket {

    @Override
    protected void readImp(ByteBuffer bytebuffer) {
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
        System.out.println("i have login game!");
    }
}