package gameserver.util.thread;

/**
 *
 * @author caoxin
 */
public class ObjectLock {

    private boolean locked = false;

    public boolean tryLockObject() {
        if (locked) {
            return false;
        }
        return locked = true;
    }

    public void unlockObject() {
        locked = false;
    }
}