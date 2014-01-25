package gameserver.event.core;

public class EventListenerContainer {

    private EventListener listener;
    private boolean autoRemove;
    
    public EventListenerContainer(EventListener listener) {
        autoRemove = false;
        this.listener = listener;
    }

    public void enableAutoRemove(boolean enable) {
        autoRemove = enable;
    }

    public boolean isAutoRemoveEnabled() {
        return autoRemove;
    }

    public EventListener getListener() {
        return listener;
    }
}