package util;

/**
 *
 * @author caoxin
 */
public class ObjectLock {

    private boolean locked = false;

    protected boolean tryLockObject() {
        if (locked) {
            return false;
        }
        return locked = true;
    }

    protected void unlockObject() {
        locked = false;
    }
}