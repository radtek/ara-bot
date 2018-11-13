/**
* 文件名：DialogDto.java
*
* 版本信息：
* 日期：2018年8月10日 下午4:58:03
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月10日 下午4:58:03
*/
@Data
public class DialoRespDto {
	private DialogBotRespDto bot;
	private DialogUserRespDto user;

}
