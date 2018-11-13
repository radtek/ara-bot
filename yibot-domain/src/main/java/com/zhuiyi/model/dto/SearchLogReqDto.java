/**
* 文件名：SearchLogReqDto.java
*
* 版本信息：
* 日期：2018年8月17日 下午3:07:48
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.model.dto;

import lombok.Data;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月17日 下午3:07:48
*/
@Data
public class SearchLogReqDto {
	
    private Integer bId;
    private String userid;
    private String query;
    private String faqid;
    private String faqin;
    private String stadate;
    private String enddate;
    private String usereval;
    private String answertype;
    private String haszrg;
    private String hashanxuan;
    private String hassenword;
    private String hasreject;
    private String address;
    private String faq;
    private String faqtype;
    private String question;
    private Integer pageNo = 1;
    private Integer pageSize = 20;

}
