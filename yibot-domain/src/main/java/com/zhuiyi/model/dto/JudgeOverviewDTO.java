package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: Faq点赞点踩统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeOverviewDTO implements Serializable {
    /**
     *
     */
    private String date;
    private String agreeCount;
    private String agreeRate;
    private String disagreeCount;
    private String disagreeRate;
    private String views;
    private String directAnswer;
    private String directAnswerAC;
    private String directAnswerDC;
    private String recommendAnswer;
    private String recommendAnswerViews;
    private String recommendAnswerAC;
    private String recommendAnswerDC;
    private String judgeCount;
    private String judgeRate;
    private List<FaqJudgeDTO> topAgree;
    private List<FaqJudgeDTO> topDisagree;
}
