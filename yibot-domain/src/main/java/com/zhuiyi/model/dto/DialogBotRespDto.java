/**
* 文件名：DialogBotRespDto.java
*
* 版本信息：
* 日期：2018年8月15日 下午3:05:29
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import java.util.List;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月15日 下午3:05:29
*/
@Data
public class DialogBotRespDto {
	private List<DialogAnsContentDto> ansContent;
	private String ansType;
	private String delay;
	private String faqType;
	private String isReject;
	private String returnAns;
}
