package mina.message;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author caoxin
 */
public class MessageManagement implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        MessageManagement.ac = ac;
    }

    public static ClientMessage getClientMessage(IoSession ioSession, short messageId) {
        if (messageId == 0x01) {
            return new LoginClientMessage(ac);
        }
        return null;
    }

    public static ServerMessage getServerMessage(short messageId) {
        return new LoginServerMessage();
    }
}