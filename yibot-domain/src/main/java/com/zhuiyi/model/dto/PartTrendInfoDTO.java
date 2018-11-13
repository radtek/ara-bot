package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: 整体趋势统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartTrendInfoDTO implements Serializable {
    /**
     * 十分钟时间
     */
    private String time;
    /**
     * 平均访问量
     */
    private Long askAvg;
    /**
     * 平均访问量
     */
    private Double costAvg;
    /**
     * 平均访问量
     */
    private Long zrgCount;
    /**
     * 平均访问量
     */
    private Double zrgRate;
    /**
     * 十分钟数组
     */
    private Long sessionCount;
}
