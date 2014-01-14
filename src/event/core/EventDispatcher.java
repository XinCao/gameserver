package event.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EventDispatcher {

    private HashMap listeners;
    private EventListenerCollection globalListeners;
    private static HashMap instances = new HashMap();
    private EventQueue queue;

    private EventDispatcher() {
        listeners = new HashMap();
        globalListeners = new EventListenerCollection();
        queue = new EventQueue();
    }

    public static synchronized EventDispatcher getInstance() {
        return getInstance("__default");
    }

    public static synchronized EventDispatcher getInstance(String name) {
        if (!instances.containsKey(name)) {
            instances.put(name, new EventDispatcher());
        }
        return (EventDispatcher) instances.get(name);
    }

    public static synchronized EventDispatcher getDetachedInstance() {
        return new EventDispatcher();
    }

    public static synchronized boolean detachDispatcher(String name) {
        if (!instances.containsKey(name)) {
            return false;
        } else {
            instances.remove(name);
            return true;
        }
    }

    public static synchronized boolean dispatcherExists(String name) {
        return instances.containsKey(name);
    }

    public void addListener(String eventName, EventListener listener) throws Exception {
        addListener(eventName, listener, false);
    }

    public void addListener(String eventName, EventListener listener, boolean autoRemove) throws Exception {
        if (!listeners.containsKey(eventName)) {
            listeners.put(eventName, new EventListenerCollection());
        }
        EventListenerCollection col = (EventListenerCollection) listeners.get(eventName);
        col.addListener(listener, autoRemove);
        ArrayList events = queue.getQueuedEvents(eventName);
        Event e;
        /**
         * 挺好用的遍历list
         */
        for (Iterator iter = events.iterator(); iter.hasNext(); propagate(e, false)) {
            e = (Event) iter.next();
        }
    }

    public EventListener removeEventListener(String eventName, String className) {
        if (!listeners.containsKey(eventName)) {
            return null;
        }
        EventListenerCollection collection = (EventListenerCollection) listeners.get(eventName);
        EventListenerContainer container = collection.removeListener(className);
        if (container != null) {
            return container.getListener();
        } else {
            return null;
        }
    }

    public EventListener removeEventListener(String eventName, EventListener listener) {
        if (!listeners.containsKey(eventName)) {
            return null;
        }
        EventListenerCollection collection = (EventListenerCollection) listeners.get(eventName);
        EventListenerContainer container = collection.removeListener(listener);
        if (container != null) {
            return container.getListener();
        } else {
            return null;
        }
    }

    public void addGlobalListener(EventListener listener) throws Exception {
        addGlobalListener(listener, false);
    }

    public void addGlobalListener(EventListener listener, boolean autoRemove) throws Exception {
        globalListeners.addListener(listener, autoRemove);
        ArrayList events = queue.getQueuedEvents();
        Event e;
        for (Iterator iter = events.iterator(); iter.hasNext(); propagate(e, false)) {
            e = (Event) iter.next();
        }
    }

    public EventListener removeGlobalEventListener(EventListener listener) {
        EventListenerContainer container = globalListeners.removeListener(listener);
        if (container != null) {
            return container.getListener();
        } else {
            return null;
        }
    }

    public EventListener removeGlobalEventListener(String className) {
        EventListenerContainer container = globalListeners.removeListener(className);
        if (container != null) {
            return container.getListener();
        } else {
            return null;
        }
    }

    public Event triggerEvent(Event e)
            throws Exception {
        return propagate(e, false);
    }

    public Event triggerEvent(Event e, boolean queue)
            throws Exception {
        return propagate(e, queue);
    }

    public Event triggerEvent(String name)
            throws Exception {
        Event e = new Event(name);
        return propagate(e, false);
    }

    public Event triggerEvent(String name, boolean queue)
            throws Exception {
        Event e = new Event(name);
        return propagate(e, queue);
    }

    public Event triggerEvent(String name, boolean queue, Object context)
            throws Exception {
        Event e = new Event(name, context);
        return propagate(e, queue);
    }

    public Event triggerEvent(String name, boolean queue, Object context, Object userInfo)
            throws Exception {
        Event e = new Event(name, context, userInfo);
        return propagate(e, queue);
    }

    private Event propagate(Event e, boolean queue)
            throws Exception {
        if (listeners.containsKey(e.getName())) {
            EventListenerCollection col = (EventListenerCollection) listeners.get(e.getName());
            col.propagate(e);
        }
        if (e.isCancelled()) {
            return e;
        }
        globalListeners.propagate(e);
        if (e.isCancelled() || !queue) {
            return e;
        } else {
            this.queue.addEvent(e);
            return e;
        }
    }

    public String[] getRegisteredEventNames() {
        String a[] = new String[0];
        String names[] = (String[]) listeners.keySet().toArray(a);
        return names;
    }

    public EventListenerCollection getEventListeners(String eventName) {
        if (listeners.containsKey(eventName)) {
            return (EventListenerCollection) listeners.get(eventName);
        } else {
            return new EventListenerCollection();
        }
    }

    public EventListenerCollection getGlobalEventListeners() {
        return globalListeners;
    }

    public void reset() {
        EventListenerCollection listenerCol;
        for (Iterator iter = this.listeners.values().iterator(); iter.hasNext(); listenerCol.removeAllListeners()) {
            listenerCol = (EventListenerCollection) iter.next();
        }
        globalListeners.removeAllListeners();
        queue.clearQueue();
    }
}