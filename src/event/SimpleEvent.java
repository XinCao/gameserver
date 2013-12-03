package event;

import event.dispatcher.GameEventDispatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 事件分发
 * @author caoxin
 */
public class SimpleEvent {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        GameEventDispatcher dispatcher = ac.getBean(GameEventDispatcher.class);
        dispatcher.triggerEvent(WorldEvents.login);
    }
}
