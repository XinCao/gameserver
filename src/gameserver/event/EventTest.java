package gameserver.event;

import gameserver.event.dispatcher.GameEventDispatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 事件分发
 * @author caoxin
 */
public class EventTest {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/event_listener.xml");
        GameEventDispatcher dispatcher = ac.getBean(GameEventDispatcher.class);
        String name = "caoxin";
        dispatcher.triggerEvent(WorldEvents.login, name);
    }
}
