package gameserver.event.listener;

import java.util.List;
import gameserver.event.core.EventListener;

/**
 * 
 * @author CaoXin
 */
public interface IGameEventListener extends EventListener {

    public List<String> getEvents();
}