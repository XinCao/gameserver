package gameserver.util;

/**
 * 定义线程池
 *
 * @author caoxin
 */
public class ThreadPoolCenter {

    public static final ThreadPool<DiscountTask> discountTaskPool = new ThreadPool<DiscountTask>(3, 50, "discount task");

}