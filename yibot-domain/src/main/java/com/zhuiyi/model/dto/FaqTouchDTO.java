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
 * description: Faq信息统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaqTouchDTO implements Serializable {
    /**
     * FAQ问句
     */
    private String question;
    /**
     * 数量
     */
    private String count;
    /**
     *
     */
    private Long type;
    /**
     *
     */
    private Long faqtype;
    /**
     *
     */
    private Double touchRate;
    /**
     *
     */
    private Long ch;
}
