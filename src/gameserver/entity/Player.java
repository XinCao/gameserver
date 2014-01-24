package gameserver.entity;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author caoxin
 */
public class Player {

    private boolean loginOk = false;
    private IoSession ioSession;

    public boolean isLoginOk() {
        return loginOk;
    }

    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }

    public IoSession getIoSession() {
        return ioSession;
    }

    public void setIoSession(IoSession ioSession) {
        this.ioSession = ioSession;
    }
}