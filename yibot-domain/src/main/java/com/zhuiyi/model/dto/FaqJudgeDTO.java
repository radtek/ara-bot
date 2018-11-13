package com.zhuiyi.model.dto;

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
public class FaqJudgeDTO implements Serializable {
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
     * FAQ点赞点踩率
     */
    private String rate;
}
