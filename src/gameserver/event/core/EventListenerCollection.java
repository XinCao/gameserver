package gameserver.event.core;

import java.util.ArrayList;
import java.util.Iterator;

public class EventListenerCollection {

    private ArrayList listeners;

    public EventListenerCollection() {
        listeners = new ArrayList();
    }

    public int addListener(EventListener listener, boolean autoRemove) {
        EventListenerContainer container = new EventListenerContainer(listener);
        container.enableAutoRemove(autoRemove);
        listeners.add(container);
        return listeners.size();
    }

    /**
     * 事件触发（范围：事件对应的监听集合）
     * 
     * @param e
     * @return
     * @throws Exception 
     */
    public Event propagate(Event e) throws Exception {
        ArrayList remove = new ArrayList();
        int i = 0;
        do {
            if (i >= listeners.size()) {
                break;
            }
            EventListenerContainer container = (EventListenerContainer) listeners.get(i);
            container.getListener().handleEvent(e);
            if (container.isAutoRemoveEnabled()) {
                remove.add(container.getListener());
            }
            if (e.isCancelled()) {
                break;
            }
            i++;
        } while (true);
        EventListener listener;
        for (Iterator iter = remove.iterator(); iter.hasNext(); removeListener(listener)) {
            listener = (EventListener) iter.next();
        }

        return e;
    }

    public EventListenerContainer removeListener(int index) {
        return (EventListenerContainer) listeners.remove(index);
    }

    public EventListenerContainer removeListener(String className) {
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            EventListenerContainer container = (EventListenerContainer) iter.next();
            if (container.getListener().getClass().getName().equals(className)) {
                listeners.remove(container);
                return container;
            }
        }

        return null;
    }

    public EventListenerContainer removeListener(EventListener listener) {
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            EventListenerContainer container = (EventListenerContainer) iter.next();
            if (container.getListener().equals(listener)) {
                listeners.remove(container);
                return container;
            }
        }

        return null;
    }

    public void removeAllListeners() {
        listeners.clear();
    }

    public Iterator iterator() {
        return listeners.iterator();
    }
}