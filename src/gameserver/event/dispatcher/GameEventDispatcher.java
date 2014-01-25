package gameserver.event.dispatcher;

import gameserver.event.core.Event;
import gameserver.event.core.EventDispatcher;
import gameserver.event.core.EventListener;

/**
 *
 * @author caoxin
 */
public class GameEventDispatcher extends BaseService {

    protected EventDispatcher dispatcher;

    public GameEventDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     *
     * 添加监听事件
     *
     * @param eventName
     * @param listener
     */
    public void addListener(String eventName, EventListener listener) {
        try {
            dispatcher.addListener(eventName, listener);
        } catch (Exception e) {
            logger.error("Failed on add EventListener, eventName={}, listener={}" + eventName + listener, e);
        }
    }

    /**
     * 分发事件
     *
     * @param e 事件对象
     * @return
     */
    public Event triggerEvent(Event e) {
        try {
            return this.dispatcher.triggerEvent(e, false);
        } catch (Exception ex) {
            String error = "Failed on trigger event, eventName=" + e.getName() + ", error=" + ex.getMessage();
            logger.error(error, ex);
            return null;
        }
    }

    /**
     * 分发事件
     *
     * @param name 事件名
     * @return
     */
    public Event triggerEvent(String name) {
        return this.triggerEvent(new Event(name));
    }

    /**
     * 分发事件
     *
     * @param name 事件名
     * @param context 传入的对象
     * @return
     */
    public Event triggerEvent(String name, Object context) {
        return this.triggerEvent(new Event(name, context));
    }

    /**
     * 分发事件
     *
     * @param name 事件名
     * @param context 传入对象1
     * @param userInfo 传入对象2
     * @return
     */
    public Event triggerEvent(String name, Object context, Object userInfo) {
        return this.triggerEvent(new Event(name, context, userInfo));
    }
}