package com.zhuiyi.common.constant;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/2 16:30
 * description:
 * own: zhuiyi
 */
@SuppressWarnings("unused")
public class GlobaSystemConstant {
    /**
     * 返回项目名称
     */
    public static final String KEY_PROJECT_NAME_STR = "yibot";
    /**
     * 返回业务名称的标识字符串
     */
    public static final String KEY_APP_NAME_STRING = "appidPool";
    /**
     * 返回USER名称的标识字符串
     */
    public static final String KEY_USER_NAME_STRING = "userPool";
    /**
     * 返回chanel名称的标识字符串
     */
    public static final String KEY_CHANEL_NAME_STRING = "chanelPool";
    /**
     * 返回session名称的标识字符串
     */
    public static final String KEY_SESSION_NAME_STRING = "sessionPool";
    /**
     * 返回数据源为测试的客服名称的标识字符串
     */
    public static final String KEY_KF_NAME_N_STRING = "kf";
    /**
     * 返回数据源为测试的业务标识字符串
     */
    public static final String KEY_BUS_NAME_N_STRING = "guess";
    /**
     * 返回数据源为测试的FAQ标识字符串
     */
    public static final String KEY_FAQ_NAME_N_STRING = "999999999";
    /**
     * 返回字段非空时的默认值
     */
    public static final String KEY_FIELD_DEFAULT_VALUE = "-";
    /**
     * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁 10分钟
     */
    public static final int LOCK_EXPIRE_MSECS = 600 * 1000;
    /**
     * 线程获取锁的等待时间 10秒
     */
    public static final int LOCK_TIMEOUT_MSECS = 10 * 1000;
    /**
     * 设置静态缓存变量用于提供系统性能
     */
    public static final HashMap<String, Object> INIT_HASHMAP = new HashMap<>();
    /**
     * 返回错误码字段
     */
    public static final String CUSTOM_SYSTEM_USER = "admin";
    /**
     * 返回错误码字段
     */
    public static final String RES_ERROR_CODE_STRING = "errorCode";
    /**
     * 返回错误信息字段
     */
    public static final String RES_ERROR_MSG_STRING = "errorMsg";
    /**
     * 默认针对百分数计算保留两位小数
     */
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    /**
     * 默认针对百分数计算保留四位小数
     */
    public static final DecimalFormat DECIMAL_FORMAT_FOUR = new DecimalFormat("0.0000");
    /**
     * 返回类型为是否的判断值
     */
    public enum BOOLEAN_IS_STATU {
        TRUE(1), FALSE(0);
        private final int elementName;
        BOOLEAN_IS_STATU(int elementName){
            this.elementName = elementName;
        }
        public int getElementName() {
            return elementName;
        }
    }
    /**
     * 返回类型为ZRG类型的枚举串
     */
    public enum ZRG_TYPE {
        NO_IN("0_on"),NO_OUT("0_off"),ACTIVE_IN("11_on"),ACTIVE_OUT("11_off"), PASSIVE_IN("12_on"),PASSIVE_OUT("12_off");
        private final String elementName;
        ZRG_TYPE(String elementName){
            this.elementName = elementName;
        }
        public String getElementName() {
            return elementName;
        }
    }
    /**
     * 返回类型为ZRG类型标识的枚举串
     */
    public enum ZRG_TYPE_FLAG {
        NO("0"),ACTIVE("11"), PASSIVE("12");
        private final String elementName;
        ZRG_TYPE_FLAG(String elementName){
            this.elementName = elementName;
        }
        public String getElementName() {
            return elementName;
        }
    }
    /**
     * 返回类型为FAQ类型的枚举串
     */
    public enum FAQ_TYPE {
        FAQ(0), CHAT(1), KB(2), TASK(3);
        private final int elementName;
        FAQ_TYPE(int elementName){
            this.elementName = elementName;
        }
        public int getElementName() {
            return elementName;
        }
    }
    /**
     * 返回类型为区域范围类型的枚举串
     */
    public enum AREA_TYPE {
        CHINA(0), PROVINCE(1), CITY(2);
        private final int elementName;
        AREA_TYPE(int elementName){
            this.elementName = elementName;
        }
        public int getElementName() {
            return elementName;
        }
    }
    /**
     * 返回国家名称的枚举串
     */
    public enum COUNTRY_NAME {
        CHINA("全国");
        private final String elementName;
        COUNTRY_NAME(String elementName){
            this.elementName = elementName;
        }
        public String getElementName() {
            return elementName;
        }
    }
    /**
     * 返回类型为机器人回答类型的枚举串
     */
    public enum ANSWER_TYPE {
        INDIRECT("-1"), DIRECT("0"), CLICK("1");
        private final String elementName;
        ANSWER_TYPE(String elementName){
            this.elementName = elementName;
        }
        public String getElementName() {
            return elementName;
        }
    }
}

