package mina.server;

import mina.core.BaseServerPacket;
import mina.core.PacketKind;
import mina.core.PacketManager;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_LOGIN extends BaseServerPacket {

    private int flag = 0;

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(flag);
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
        SM_COOLDOWN sm_cooldown = PacketManager.getPacketByOpcode(PacketKind.SM_COOLDOWN.getOpcode());
        sm_cooldown.init(this.player);
        SM_COUNT_SYNC sm_count_sync = PacketManager.getPacketByOpcode(PacketKind.SM_COUNT_SYNC.getOpcode());
        sm_count_sync.init(this.player);
        ioSession.write(sm_cooldown);
        ioSession.write(sm_count_sync);
    }
    
    public void init(int flag) {
        this.flag = flag;
    }
    
    public void initContext() {
        
    }
}