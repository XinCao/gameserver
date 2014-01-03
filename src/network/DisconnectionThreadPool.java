package network;

import java.util.List;

/**
 * DisconnectionThreadPool that will be used to execute DisconnectionTask
 *
 * @author -Nemesiss-
 */
public interface DisconnectionThreadPool {

    /**
     * 若干时间后关闭连接任务
     * @param dt
     * @param delay 
     */
    public void scheduleDisconnection(DisconnectionTask dt, long delay);

    /**
     * 关闭所有连接（shutdown）
     */
    public void waitForDisconnectionTasks(List<AConnection> list);
}
