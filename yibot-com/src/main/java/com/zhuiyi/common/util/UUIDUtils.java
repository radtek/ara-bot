package com.zhuiyi.common.util;

import java.util.UUID;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/18 19:14
 * description: 生成随机字符串的工具类 uuid
 * own: zhuiyi
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }
}
