package mina.client;

import java.nio.ByteBuffer;
import entity.Player;
import mina.core.BaseClientPacket;
import mina.core.PacketKind;
import mina.core.PacketManagement;

/**
 *
 * @author caoxin
 */
public class LoginClientPacket extends BaseClientPacket {

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
            player.setLoginOk(true);
            ioSession.setAttribute("currentPlayer", player);
        }
        Player currentPlayer = (Player) (ioSession.getAttribute("currentPlayer"));
        String flag = "failure";
        if (currentPlayer.isLoginOk()) {
            flag = "success";
        }
        logger.debug("player object bind to IoSession " + flag + " !");
        System.out.println(this.str);
        this.ioSession.write(PacketManagement.getPacketByOpcode(PacketKind.LOGIN_SERVER_PACKET.getOpcode()));
    }
}