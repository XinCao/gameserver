package gameserver.event.core;

import java.util.ArrayList;
import java.util.Iterator;

class EventQueue {

    ArrayList events;

    EventQueue() {
        events = new ArrayList();
    }

    public void addEvent(Event e) {
        e.queueEvent();
        events.add(e);
    }

    public ArrayList getQueuedEvents() {
        return events;
    }

    public ArrayList getQueuedEvents(String eventName) {
        ArrayList qEvents = new ArrayList();
        Iterator iter = events.iterator();
        do {
            if (!iter.hasNext()) {
                break;
            }
            Event e = (Event) iter.next();
            if (e.getName().equals(eventName)) {
                qEvents.add(e);
            }
        } while (true);
        return qEvents;
    }

    public void clearQueue() {
        events.clear();
    }
}