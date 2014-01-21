package mina.message;

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

    public static <T extends BaseMessage> T getMessageByOpcode(short messageId) {
        BaseMessage baseMessage = MessageConf.getMessageByOpcode(messageId);
        baseMessage.setAc(ac);
        return (T) baseMessage;
    }
}