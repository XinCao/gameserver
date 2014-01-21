package mina;

import mina.message.ClientMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MessageHandler extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    /**
     * 对接受到的消息进行处理
     * 
     * @param session
     * @param object
     * @throws Exception 
     */
    @Override
    public void messageReceived(IoSession session, Object object) throws Exception {
        ClientMessage clientMessage = (ClientMessage)object;
        if (clientMessage.canPerform()) {
            clientMessage.perform();
        }
        super.messageReceived(session, clientMessage);
    }

    @Override
    public void messageSent(IoSession session, Object serverMessage) throws Exception {
        super.messageSent(session, serverMessage);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        session.write(new IdelMessage());
        super.sessionIdle(session, status);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }
}