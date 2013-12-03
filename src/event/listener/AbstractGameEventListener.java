package event.listener;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CaoXin
 */
public abstract class AbstractGameEventListener implements IGameEventListener {
    final Logger logger = LoggerFactory.getLogger(getClass());

    protected List<String> events;

    @Override
    public List<String> getEvents() {
        return this.events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}