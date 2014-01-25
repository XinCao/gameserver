package resources.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 断开任务连接池
 *
 * @author caoxin
 */
public class DisconnectionThreadPoolImp implements DisconnectionThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(PacketProcessor.class);
    private final static int reduceThreshold = 3;
    private final static int increaseThreshold = 50;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final List<DisconnectionTask> disconnectionTasks = new LinkedList<DisconnectionTask>();
    private final List<Thread> threads = new ArrayList<Thread>();
    private final int minThreads;
    private final int maxThreads;

    public DisconnectionThreadPoolImp(int minThreads, int maxThreads) {
        if (minThreads <= 0) {
            minThreads = 1;
        }
        if (maxThreads < minThreads) {
            maxThreads = minThreads;
        }
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        if (minThreads != maxThreads) {
            this.startCheckerThread();
        }
        for (int i = 0; i < minThreads; i++) {
            this.newThread();
        }
    }

    private void startCheckerThread() {
        new Thread(new CheckerTask(), "PacketProcessor:Checker").start();
    }

    /**
     * 平衡容器中线程个数
     */
    private final class CheckerTask implements Runnable {

        private final int sleepTime = 60 * 1000;
        private int lastSize = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                int sizeNow = disconnectionTasks.size();
                if (sizeNow < lastSize) {
                    if (sizeNow < reduceThreshold) {
                        killThread();
                    }
                } else if (sizeNow > lastSize && sizeNow > increaseThreshold) {
                    if (!newThread() && sizeNow >= increaseThreshold * 3) {
                        logger.info("Lagg detected! [" + sizeNow + " DisconnectionTask are waiting for execution]. You should consider increasing DisconnectionThreadPool maxThreads or hardware upgrade.");
                    }
                }
                lastSize = sizeNow;
            }
        }
    }

    private void killThread() {
        if (threads.size() < minThreads) {
            Thread t = threads.remove((threads.size() - 1));
            logger.debug("Killing PacketProcessor Thread: " + t.getName());
            t.interrupt();
        }
    }

    private boolean newThread() {
        if (threads.size() >= maxThreads) {
            return false;
        }
        String name = "DisconnectionThreadPool:" + threads.size();
        logger.debug("Creating new DisconnectionTaskProcessor Thread: " + name);
        Thread t = new Thread(new DisconnectionTaskProcessor(), name);
        threads.add(t);
        t.start();
        return true;
    }

    private final class DisconnectionTaskProcessor implements Runnable {

        @Override
        public void run() {
            DisconnectionTask disconnectionTask = null;
            while (true) {
                lock.lock();
                try {
                    if (disconnectionTask != null) {
                        disconnectionTask.unlockConnection();
                    }
                    if (Thread.interrupted()) {
                        return;
                    }
                    disconnectionTask = getFirstAviable();
                } finally {
                    lock.unlock();
                }
                disconnectionTask.run();
            }
        }
    }

    private DisconnectionTask getFirstAviable() {
        while (true) {
            while (disconnectionTasks.isEmpty()) {
                notEmpty.awaitUninterruptibly();
            }
            Iterator<DisconnectionTask> it = disconnectionTasks.listIterator();
            while (it.hasNext()) {
                DisconnectionTask disconnectionTask = it.next();
                if (disconnectionTask.getDelay() > System.currentTimeMillis()) {
                    continue;
                }
                if (disconnectionTask.tryLockConnection()) {
                    it.remove();
                    return disconnectionTask;
                }
            }
            notEmpty.awaitUninterruptibly();
        }
    }

    @Override
    public final void scheduleDisconnection(DisconnectionTask dt, long delay) {
        lock.lock();
        try {
            dt.setDelay(delay);
            disconnectionTasks.add(dt);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void waitForDisconnectionTasks(List<AConnection> list) {
        for (AConnection a : list) {
            this.scheduleDisconnection(new DisconnectionTask(a), 0);
        }
    }
}