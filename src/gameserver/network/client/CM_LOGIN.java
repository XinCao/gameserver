package gameserver.network.client;

import java.nio.ByteBuffer;
import gameserver.service.PlayerService;
import gameserver.model.player.Player;
import gameserver.model.World;
import gameserver.network.core.BaseClientPacket;
import gameserver.network.core.PacketKind;
import gameserver.network.core.PacketManager;
import gameserver.network.server.SM_LOGIN;

/**
 *
 * @author caoxin
 */
public class CM_LOGIN extends BaseClientPacket {

    private String key;
    private PlayerService playerService;

    @Override
    protected void readImp(ByteBuffer byteBuffer) {
        StringBuilder sb = new StringBuilder();
        int lenght = byteBuffer.getInt();
        for (int i = 0; i < lenght; i++) {
            sb.append((char) (byteBuffer.get()));
        }
        this.key = sb.toString();
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
        this.initContext();
        SM_LOGIN sm_login = PacketManager.getPacketByOpcode(PacketKind.SM_LOGIN.getOpcode());
        if (key.equals("caoxin")) {
            this.player = new Player();
            this.player.setKey(this.key);
            this.player.setIoSession(ioSession);
            this.playerService.initPlayer(this.player);
            World.joinWorld(player);
            this.ioSession.setAttribute("currentPlayer", player);
            sm_login.init(1);
        } else {
            sm_login.init(-1);
        }
        this.ioSession.write(sm_login);
    }

    public void initContext() {
        playerService = ac.getBean(PlayerService.class);
    }
}