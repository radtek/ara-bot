/**
* 文件名：DialogUserRespDto.java
*
* 版本信息：
* 日期：2018年8月15日 下午3:05:46
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月15日 下午3:05:46
*/
@Data
public class DialogUserRespDto {
	private String askId;
	private String city;
	private String clickType;
	private String emotion;
	private String ip;
	private String isSensitive;
	private String query;
	private String queryType;
	private String[] sensitiveWords;
	private String timestamp;
}
