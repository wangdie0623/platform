package com.wang.platform.utils;



import java.util.Random;

/**
 * Created by amosli on 16/3/31.
 */
public class RandomUtil {

    private RandomUtil(){
        throw new IllegalAccessError("Utility class");
    }

    public static synchronized int generate(int start, int end) {
        Random random = new Random();
        int result = random.nextInt(end);
        if (result < start) {
            result = start + (int) (Math.random() * (end - start + 1));
        }
        return result;
    }

    public static synchronized int getRandomInt(int a, int b) {
        if (a > b || a < 0)
            return -1;
        return a + (int) (Math.random() * (b - a + 1));
    }


}
