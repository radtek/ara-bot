/**
* 文件名：ZrgConfigReqDto.java
*
* 版本信息：
* 日期：2018年8月20日 下午7:20:11
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月20日 下午7:20:11
*/
@Data
public class ZrgConfigReqDto {
	private String bd; //业务
	private String workTime;//工作时间
	private String zdZrg[];//主动转人工,faq列表
	private String bdZrg[];//被动转人工,faq列表
	private String zdZrgType;//主动转人工
	private String bdZrgType;//被动转人工
	private String isTest;//是否测试业务
	private String recommandClick;//是否包含推荐点击
	private String dm5;//是否md5加密

}
