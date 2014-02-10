package gameserver.util.thread;

/**
 * 断开任务（事例：线程池的基本使用）
 *
 * @author caoxin
 */
public class DiscountTask extends ObjectLock implements Runnable {

    private int id;

    public DiscountTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("this is the " + this.id + " discount task!");
    }
}