package event.listener;

import java.util.List;
import event.core.EventListener;

/**
 * 
 * @author CaoXin
 */
public interface IGameEventListener extends EventListener {

    public List<String> getEvents();
}