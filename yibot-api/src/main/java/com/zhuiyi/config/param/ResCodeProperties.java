package com.zhuiyi.config.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: tree
 * @version: 1.0
 * date: 2018/6/19 11:52
 * @description: 响应信息类
 * own:
 */
@Component
@Data
@ConfigurationProperties(prefix = "yibot.rescode")
public class ResCodeProperties {
    /**
     * 正常返回
     */
    private int successCode;
    private String successMsg;
    /**
     * 请求未授权
     */
    private int reqUnauthorizedCode;
    private String reqUnauthorizedMsg;
    /**
     * 请求被服务端拒绝
     */
    private int reqForbiddenCode;
    private String reqForbiddenMsg;
    /**
     * 请求报文格式错误
     */
    private int reqFormatErrorCode;
    private String reqFormatErrorMsg;
    /**
     * 请求数据校验失败
     */
    private int reqDataCheckFailCode;
    private String reqDataCheckFailMsg;
    /**
     * 请求数据缺失
     */
    private int reqDataMissCode;
    private String reqDataMissMsg;
    /**
     * 交互令牌校验失败
     */
    private int tokenCheckFailCode;
    private String tokenCheckFailMsg;
    /**
     * 交互令牌超时失效
     */
    private int tokenExpiredCode;
    private String tokenExpiredMsg;
    /**
     * 请求的内容不存在
     */
    private int reqContentNotExistCode;
    private String reqContentNotExistMsg;
    /**
     * 交互令牌数据缺失
     */
    private int tokenDataMissCode;
    private String tokenDataMissMsg;
    /**
     * 授权账户/密码校验失败
     */
    private int pswNotCorrectCode;
    private String pswNotCorrectMsg;
    /**
     * 上传数据超过限制条数
     */
    private int pushDataExceedsLimitCode;
    private String pushDataExceedsLimitMsg;
    /**
     * 操作执行失败
     */
    private int operateFailErrorCode;
    private String operateFailErrorMsg;
    /**
     * 服务不可用
     */
    private int serviceDisabledErrorCode;
    private String serviceDisabledErrorMsg;
    /**
     * 服务器内部错误
     */
    private int internalServerErrorCode;
    private String internalServerErrorMsg;
}
