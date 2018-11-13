package com.zhuiyi.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: tree
 * @version: 1.0
 * date: 2018/6/19 15:45
 * @description: 请求响应数据VO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回异常信息
     */
    private String error;
    /**
     * 响应时间戳
     */
    private String timestamp;
    /**
     * 访问地址
     */
    private String path;
    /**
     * 返回Json对象数据
     */
    private Object data;
    /**
     * 访问令牌
     */
    private String aToken;
    /**
     * 刷新令牌
     */
    private String rToken;
    /**
     * 令牌有效期
     */
    private Long expires;
    /**
     * 返回数据所属的日期数据
     */
    private String date;
}
