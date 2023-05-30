package com.site.blog.my.core.util;

/**
 * @author 夏志勇
 * @since 2023年05月25日 14:47
 */
public class SignUtil {

    /**
     * @param day 连续签到的天数
     * @return 奖励
     */
    //计算奖励
    public static Integer calculatedReward(int day) {
        if (day <= 5) {
            return 50;
        } else if (day <= 10) {
            return 100;
        } else if (day <= 15) {
            return 150;
        } else if (day <= 20) {
            return 200;
        } else if (day <= 30) {
            return 250;
        } else {
            return 300;
        }

    }

}
