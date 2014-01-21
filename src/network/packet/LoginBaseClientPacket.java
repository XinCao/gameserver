package network.packet;

import java.nio.ByteBuffer;
import network.AionConnection;

/**
 *
 * @author caoxin
 */
public class LoginBaseClientPacket extends BaseClientPacket<AionConnection> {

    public LoginBaseClientPacket(ByteBuffer buf, int opcode) {
        super(buf, opcode);
    }

    public LoginBaseClientPacket(int opcode) {
        super(opcode);
    }

    @Override
    protected void readImpl() {
    }

    @Override
    protected void canPerform() {
    }

    @Override
    public void run() {
        System.out.print("****************************************************");
    }
}
