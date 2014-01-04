package network;

public class DisconnectionTask implements Runnable {

    private AConnection connection;
    private long delay = -1;
    private boolean locked = false;

    public DisconnectionTask(AConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.onDisconnect();
    }

    boolean tryLockConnection() {
        if (locked) {
            return false;
        }
        return locked = true;
    }

    void unlockConnection() {
        locked = false;
    }

    public long getDelay() {
        return delay + System.currentTimeMillis();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}