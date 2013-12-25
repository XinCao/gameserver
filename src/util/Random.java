package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author caoxin
 */
public class Random {

    public static int randomInt(int max) {
        return (int) (Math.random() * max);
    }

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
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
                n = Random.randomInt(min, max);
                result.add(n);
            }
        } else {
            List<Integer> hasResult = new ArrayList<Integer>();
            int n;
            if (number > max - min + 1) {
                return null;
            }
            for (int i = 0; i < number; i++) {
                n = Random.randomInt(min, max);
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

    public static Object randomIntAccordingToWeight(Map<Integer, Object> weightMap, Integer total) {
        Iterator<Integer> iterator = weightMap.keySet().iterator();
        Integer num = Random.randomInt(total);
        Integer key = -1;
        for (Integer sum = 0; iterator.hasNext();) {
            key = iterator.next();
            sum += key;
            if (sum >= num) {
                break;
            }
        }

        return weightMap.get(key);
    }

    public static void main(String[] args) {
        Map<Integer, Object> weightMap = new HashMap<Integer, Object>();
        int total = 0;
        int loop = 100;
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        while(loop > 0) {
            for (int i = 0; i < 10; i++) {
                weightMap.put(i * 10 * i * 10, new Integer(i));
                total += i * 10 * i * 10;
            }
            Integer random = (Integer) randomIntAccordingToWeight(weightMap, total);
            loop--;
            if (!result.containsKey(random)) {
                result.put(random, 1);
            } else {
                int temp = result.get(random);
                result.put(random, temp + 1);
            }
        }
        System.out.println(result);
    }
}