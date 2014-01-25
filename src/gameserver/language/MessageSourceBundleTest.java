package gameserver.language;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class MessageSourceBundleTest {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/message_source_bundle.xml");
        MessageSourceBundle messageSourceBundle = ac.getBean(MessageSourceBundle.class);
        System.out.println(messageSourceBundle);
        String str = messageSourceBundle.getMessage("1", new String[]{"caoxin"});
        System.out.println(str);
    }
}