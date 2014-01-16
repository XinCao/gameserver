package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池实现
 *
 * @author caoxin
 */
public class ThreadPool<T extends ObjectLock & Runnable> {

    private Logger logger = LoggerFactory.getLogger(ThreadPool.class);
    private final static int reduceThreshold = 3; // 最小阈值
    private final static int increaseThreshold = 50; // 最大阈值
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final List<T> taskSet = new LinkedList<T>();
    private final List<Thread> threads = new ArrayList<Thread>();
    private final int minThreads;
    private final int maxThreads;

    public ThreadPool(int minThreads, int maxThreads, String name) {
        logger.info("this " + name + " thread pool " + " is starting!");
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
        new Thread(new CheckerTask(), "Processor:Checker").start();
    }

    private boolean newThread() {
        if (threads.size() >= maxThreads) {
            return false;
        }
        String name = "Processor:" + threads.size();
        logger.debug("Creating new Processor Thread: " + name);
        Thread t = new Thread(new ProcessorTask(), name);
        threads.add(t);
        t.start();
        return true;
    }

    private void killThread() {
        if (threads.size() < minThreads) {
            Thread t = threads.remove((threads.size() - 1));
            logger.debug("Killing Processor Thread: " + t.getName());
            t.interrupt();
        }
    }

    public final void executeTask(T t) {
        lock.lock();
        try {
            taskSet.add(t);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private T getFirstAviable() {
        while (true) {
            while (taskSet.isEmpty()) {
                notEmpty.awaitUninterruptibly();
            }
            ListIterator<T> it = taskSet.listIterator();
            while (it.hasNext()) {
                T t = it.next();
                if (t.tryLockObject()) {
                    it.remove();
                    return t;
                }
            }
            notEmpty.awaitUninterruptibly();
        }
    }

    private final class ProcessorTask implements Runnable {

        @Override
        public void run() {
            T t = null;
            while (true) {
                lock.lock();
                try {
                    if (t != null) {
                        t.unlockObject();
                    }
                    if (Thread.interrupted()) {
                        return;
                    }
                    t = getFirstAviable();
                } finally {
                    lock.unlock();
                }
                t.run();
            }
        }
    }

    private final class CheckerTask implements Runnable {

        private final int sleepTime = 60 * 1000; // 一分钟检测一次，这个值， 直接关系到，线程池中工作线程的个数，和对请求处理的敏捷程度(如果，对于高并发的需求则需要降低，以提高敏捷度)
        private int lastSize = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.info("this is working checker task runinng stop" + e.getMessage());
                }
                int sizeNow = taskSet.size();
                if (sizeNow < lastSize) {
                    if (sizeNow < reduceThreshold) {
                        killThread();
                    }
                } else if (sizeNow > lastSize && sizeNow > increaseThreshold) {
                    if (!newThread() && sizeNow >= increaseThreshold * 3) {
                        logger.info("Lagg detected! [" + sizeNow + " T are waiting for execution]. You should consider increasing Processor maxThreads or hardware upgrade.");
                    }
                }
                lastSize = sizeNow;
            }
        }
    }
}