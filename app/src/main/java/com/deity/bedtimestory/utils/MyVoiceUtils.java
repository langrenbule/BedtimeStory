package com.deity.bedtimestory.utils;

/**
 * Function:此项目的一些小工具方法
 * Reason:
 * Author:zyl
 * Date: 15-7-9 下午2:29.
 */
public class MyVoiceUtils {

    private MyVoiceUtils() {
        throw new AssertionError();
    }

    static String[] numberSet = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    static String[] chineseSet = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "六", "柒", "捌", "玖"};

    public static String numbers2chinese(String carNum) {
        String chineseCarNum = null;
        if (null == carNum) {
            throw new NullPointerException("车牌号码为NULL");
        } else if ("".equals(carNum)) {
            chineseCarNum = "空车牌";
        } else {
            carNum = carNum.replaceAll(numberSet[0], chineseSet[0]);
            carNum = carNum.replaceAll(numberSet[1], chineseSet[1]);
            carNum = carNum.replaceAll(numberSet[2], chineseSet[2]);
            carNum = carNum.replaceAll(numberSet[3], chineseSet[3]);
            carNum = carNum.replaceAll(numberSet[4], chineseSet[4]);
            carNum = carNum.replaceAll(numberSet[5], chineseSet[5]);
            carNum = carNum.replaceAll(numberSet[6], chineseSet[6]);
            carNum = carNum.replaceAll(numberSet[7], chineseSet[7]);
            carNum = carNum.replaceAll(numberSet[8], chineseSet[8]);
            chineseCarNum = carNum.replaceAll(numberSet[9], chineseSet[9]);
        }

        return chineseCarNum;
    }

}
