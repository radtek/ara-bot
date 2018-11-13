/**
* 文件名：SearchLogRespDto.java
*
* 版本信息：
* 日期：2018年8月15日 上午11:57:39
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import java.util.List;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月15日 上午11:57:39
*/
@Data
public class SearchLogRespDto {
	
	private int count;
	private List<SessionRespDto> rows;

}
