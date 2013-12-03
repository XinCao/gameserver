package event.listener;

import event.WorldEvents;
import event.core.Event;

/**
 * 游戏逻辑（控制器）
 *
 * @author CaoXin
 */
public class WorldEventListener extends AbstractGameEventListener {

    @Override
    public void handleEvent(Event e) throws Exception {
        String eventName = e.getName();
        Object context = e.getContext();
        if (eventName.equals(WorldEvents.login)) {
            this.login();
        }
    }

    private void login() {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

}