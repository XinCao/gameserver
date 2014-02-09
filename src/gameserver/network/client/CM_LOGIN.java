package gameserver.network.client;

import java.nio.ByteBuffer;
import gameserver.service.PlayerService;
import gameserver.model.player.Player;
import gameserver.model.World;
import gameserver.network.core.BaseClientPacket;

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
        if (key.equals("caoxin")) {
            this.player = new Player();
            this.player.setKey(this.key);
            this.player.setIoSession(ioSession);
            this.playerService.initPlayer(this.player);
            World.joinWorld(player);
            this.ioSession.setAttribute("currentPlayer", player);
        } else {
        }
    }

    @Override
    public void initContext() {
        playerService = ac.getBean(PlayerService.class);
    }
}