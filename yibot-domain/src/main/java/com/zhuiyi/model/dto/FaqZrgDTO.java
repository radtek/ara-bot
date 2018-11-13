package com.zhuiyi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: Faq点赞点踩信息统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaqZrgDTO implements Serializable {
    /**
     * FAQ问句
     */
    private String question;
    /**
     * FAQ问句ID
     */
    private String faqid;
    /**
     * FAQ类型
     */
    private String faqtype;
    /**
     * 数量
     */
    private String count;
    /**
     * 今日总量
     */
    private String amount;
    /**
     * 相比于昨天的振幅
     */
    private String rate;
}
