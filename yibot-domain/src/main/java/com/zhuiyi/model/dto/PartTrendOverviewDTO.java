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
 * description: 整体趋势统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartTrendOverviewDTO implements Serializable {
    /**
     * 平均耗时数组
     */
    private List<Double> costTrend;
    /**
     * 访问量数组
     */
    private List<Long> askTrend;
    /**
     * 十分钟数组
     */
    private List<String> time;
}
