package util;

/**
 * 定义线程池
 *
 * @author caoxin
 */
public class ThreadPoolCenter {

    public static final ThreadPool<DiscountTask> discountTaskPool = new ThreadPool<DiscountTask>(3, 50, "discount task");

    public static void main(String... args) {
        int loop = 55;
        while (loop > 0) {
            discountTaskPool.executeTask(new DiscountTask(loop));
            loop --;
        }
    }
}