package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 * @author caoxin
 */
public class AionConnection extends AConnection {

    
    
    public AionConnection(SocketChannel sc, Dispatcher d) throws IOException {
        super(sc, d);
    }
    
    @Override
    protected boolean processData(ByteBuffer data) {
        int packetId = data.getShort();
        
        
        
        
        
        return true;
    }

    @Override
    protected boolean writeData(ByteBuffer data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 玩家离线时，调用
     */
    @Override
    protected void onDisconnect() {
    }

    /**
     * 停服时，调用
     */
    @Override
    protected void onServerClose() {
    }
}
