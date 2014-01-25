package gameserver.language;

import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 * 语言本地化（可以给属性文件传递参数）
 *
 * @author caoxin
 */
public class MessageSourceBundle {

    private MessageSource messageSource;

    public String getMessage(String key, Object[] o) {
        return messageSource.getMessage(key, o, Locale.getDefault());
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String toString() {
        return "MessageSourceBundle{" + "messageSource=" + messageSource + '}';
    }
}