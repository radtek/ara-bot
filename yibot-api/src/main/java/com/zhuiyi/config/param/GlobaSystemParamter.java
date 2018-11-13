package com.zhuiyi.config.param;

import java.util.HashMap;

/**
 * @author: tree
 * @version: 1.0
 * date: 2018/6/19 11:52
 * @description: xxx
 * own:
 */
public class GlobaSystemParamter {
    /**
     *  设置静态缓存变量用于提供系统性能
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
}

