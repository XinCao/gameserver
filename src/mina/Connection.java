package mina;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author caoxin
 */
public class Connection {

    private IoSession ioSession;
    private Player player;

    public Connection(IoSession ioSession, Player player) {
        this.ioSession = ioSession;
        this.player = player;
    }

    public IoSession getIoSession() {
        return ioSession;
    }

    public void setIoSession(IoSession ioSession) {
        this.ioSession = ioSession;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}