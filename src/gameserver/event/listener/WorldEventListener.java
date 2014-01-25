package gameserver.event.listener;

import gameserver.event.WorldEvents;
import gameserver.event.core.Event;

/**
 * 游戏逻辑（控制器）
 *
 * @author CaoXin
 */
public class WorldEventListener extends AbstractGameEventListener {

    @Override
    public void handleEvent(Event e) throws Exception {
        String eventName = e.getName();
        String name = (String)e.getContext();
        if (eventName.equals(WorldEvents.login)) {
            this.login(name);
        }
    }

    private void login(String name) {
        System.out.println(name + "登录游戏世界");
    }

}