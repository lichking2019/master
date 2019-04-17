package com.wt.master.core.common.utils;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtils {
    /**
     * 获取随机整数
     * @param bound 范围，从0~bound
     * @return
     */
    public static int getInt(int bound){
        Random random = new Random();
        return random.nextInt(bound);
    }

}
