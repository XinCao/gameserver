package network.packet.client;

import java.nio.ByteBuffer;
import network.AConnection;
import network.packet.BaseClientPacket;

/**
 *
 * @author caoxin
 */
public class ChatRoomClientPacket extends BaseClientPacket<AConnection> {

    private ByteBuffer buf;
    
    public ChatRoomClientPacket(ByteBuffer buf, int opcode) {
        super(buf, opcode);
        this.buf = buf;
    }
    @Override
    protected void readImpl() {
        this.buf = getConnection().readBuffer;
        
    }

    @Override
    protected void canPerform() {
    }

    @Override
    public void run() {
    }
}
