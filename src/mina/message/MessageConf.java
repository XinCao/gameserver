package mina.message;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author caoxin
 */
public enum MessageConf {

    LOGIN_CLIENT_MESSAGE(Status.LOGIN_GAME, MessageType.CLIENT, (short) 0x01, LoginClientMessage.class),
    LOGIN_SERVER_MESSAGE(Status.LOGIN_GAME, MessageType.SERVER, (short) 0x02, LoginServerMessage.class),;
    private static final Logger logger = LoggerFactory.getLogger(MessageConf.class);
    private Status status;
    private MessageType messageType;
    private short messageId;
    private Class<BaseMessage> clazz;
    public static final Map<Short, BaseMessage> messageSet = new HashMap<Short, BaseMessage>();

    public static BaseMessage getMessageByOpcode(short messageId) {
        if (messageSet.containsKey(messageId)) {
            return messageSet.get(messageId);
        }
        return null;
    }

    static {
        for (MessageConf m : values()) {
            BaseMessage baseMessage = null;
            try {
                baseMessage = m.clazz.newInstance();
                baseMessage.setMessageType(m.getMessageType());
                baseMessage.setStatus(m.getStatus());
                baseMessage.setMessageId(m.getMessageId());
            } catch (InstantiationException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }

            messageSet.put(m.getMessageId(), baseMessage);
        }
    }

    private MessageConf(final Status status, final MessageType messageType, short messageId, Class clazz) {
        this.status = status;
        this.messageType = messageType;
        this.messageId = messageId;
        this.clazz = clazz;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public short getMessageId() {
        return messageId;
    }

    public void setMessageId(short messageId) {
        this.messageId = messageId;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}