package event.core;

/**
 * 事件对象
 *
 * @author caoxin
 */
public class Event {

    private String name;
    private boolean cancelled;
    private boolean inQueue;
    private Object context;
    private Object userInfo;

    public Event(String name) {
        this.name = null;
        cancelled = false;
        inQueue = false;
        context = null;
        userInfo = null;
        this.name = name;
    }

    public Event(String name, Object context) {
        this.name = null;
        cancelled = false;
        inQueue = false;
        this.context = null;
        userInfo = null;
        this.name = name;
        this.context = context;
    }

    public Event(String name, Object context, Object userInfo) {
        this.name = null;
        cancelled = false;
        inQueue = false;
        this.context = null;
        this.userInfo = null;
        this.name = name;
        this.context = context;
        this.userInfo = userInfo;
    }

    public void cancel() {
        cancelled = true;
    }

    public void queueEvent() {
        inQueue = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isQueued() {
        return inQueue;
    }

    public String getName() {
        return name;
    }

    public Object getContext() {
        return context;
    }

    public Object getUserInfo() {
        return userInfo;
    }
}