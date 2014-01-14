package util;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class RandomUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(RandomUtil.class);

    public static int randomInt(int max) {
        Args.notNegative(max, "max < 0");
        return (int) (Math.random() * max) + 1;
    }

    public static int randomInt(int min, int max) {
        Args.notNegative("min, max", min, max, max - min);
        return (int) (Math.random() * (max - min) + min) + 1;
    }

    /**
     * 随机多个数据（等概率）
     *
     * @param min
     * @param max
     * @param number 个数
     * @param repeat 是否可以重复
     * @return
     */
    public static List<Integer> randomIntArray(int min, int max, int number, boolean repeat) {
        List<Integer> result = new ArrayList<Integer>();
        if (repeat) {
            int n;
            for (int i = 0; i < number; i++) {
                n = RandomUtil.randomInt(min, max);
                result.add(n);
            }
        } else {
            List<Integer> hasResult = new ArrayList<Integer>();
            int n;
            if (number > max - min + 1) {
                return null;
            }
            for (int i = 0; i < number; i++) {
                n = RandomUtil.randomInt(min, max);
                if (hasResult.contains(n)) {
                    i--;
                    continue;
                }
                hasResult.add(n);
                result.add(n);
            }
        }

        return result;
    }

    public static <T extends Weight> T randomIntAccordingToWeight(List<T> weights, Integer total) {
        int num = RandomUtil.randomInt(total);
        int sum = 0;
        boolean flag = true;
        T result = null;
        for (T w : weights) {
            sum += w.getWeight();
            if (flag && sum >= num) {
                result = w;
                flag = false;
            }
        }
        if (sum != total) {
            logger.info("you muse ensure total = {} is sum of all weight");
        }
        return  result;
    }

    public static void main(String... args) {
    }
}