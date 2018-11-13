package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: 资料库分析FAQ统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqTouchOverviewDTO implements Serializable {
    /**
     * 访问量
     */
    private String question;
    private Integer touchCount;
    private Double touchRate;
    private Integer commentCount;
    private Integer assentCount;
    private Double assentRate;
    private Integer noAssentCount;
    private Double noAssentRate;
    private Integer zrgCount;
    private Double zrgRate;
    private Integer emotionCount;
    private Double emotionRate;
}
