/**
* 文件名：SessionRespDto.java
*
* 版本信息：
* 日期：2018年8月15日 上午11:59:14
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import java.util.List;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月15日 上午11:59:14
*/
@Data
public class SessionRespDto {
	
	private String assess;
	private String channel;
	private String createdAt;
	private String delay;
	private String firstActiveCity;
	private String firstActiveIp;
	private String firstActiveTime;
	private String id;
	private String isSensitive;
	private String isZrg;
	private String other;
	private String reserve1;
	private String reserve2;
	private String reserve3;
	private String reserve4;
	private String reserve5;
	private String session;
	private String source;
	private String updatedAt;
	private String userId;
	private List<DialoRespDto> dialogs;
}
