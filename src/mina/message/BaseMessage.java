package mina.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public abstract class BaseMessage implements Cloneable {

    protected Logger logger = LoggerFactory.getLogger(ClientMessage.class);
    private short messageId;
    protected ApplicationContext ac;
    private MessageType messageType;
    private Status status;

    public void setAc(ApplicationContext ac) {
        this.ac = ac;
    }

    public boolean canPerform() {
        return true;
    }

    abstract public void perform();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public short getMessageId() {
        return messageId;
    }

    public void setMessageId(short messageId) {
        this.messageId = messageId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}