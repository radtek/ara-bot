/**
* 文件名：BizUtil.java
*
* 版本信息：
* 日期：2018年8月17日 下午3:36:35
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.common.util;

import lombok.extern.slf4j.Slf4j;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月17日 下午3:36:35
*/
@Slf4j
public class BizUtil {
	private BizUtil() {
		
	}
	
	
	/**
	* 此方法描述的是：将前端的bid转换为业务id
	* @author: klauszhou@wezhuiyi.com
	* @version: 2018年8月17日 下午3:49:15
	*/
	public static Integer parseAppid(Integer bid) {
		Integer pId = 0;
		Integer bId = 0;
	    pId = bid >> 16 ;
	    bId = bid - (pId << 16);
		log.trace("pId is {}",pId);
		log.trace("bId is {}",bId);
		return Integer.parseInt(pId+""+bId);
	}
	
	
	public static void main(String[] args) {
		System.out.println(parseAppid(131662825));
	}

}
