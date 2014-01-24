package mina.client;

import java.nio.ByteBuffer;
import gameserver.entity.Player;
import mina.core.BaseClientPacket;
import mina.core.PacketKind;
import mina.core.PacketManagement;

/**
 *
 * @author caoxin
 */
public class CM_LOGIN extends BaseClientPacket {

    private String str;

    @Override
    protected void readImp(ByteBuffer byteBuffer) {
        StringBuilder sb = new StringBuilder();
        int lenght = byteBuffer.getInt();
        for (int i = 0; i < lenght; i++) {
            sb.append((char) (byteBuffer.get()));
        }
        this.str = sb.toString();
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
        if (true) {
            Player player = new Player();
            ioSession.setAttribute("currentPlayer", player);
            player.setIoSession(ioSession);
        }
        System.out.println(this.str);
        this.ioSession.write(PacketManagement.getPacketByOpcode(PacketKind.SM_LOGIN.getOpcode()));
    }
}