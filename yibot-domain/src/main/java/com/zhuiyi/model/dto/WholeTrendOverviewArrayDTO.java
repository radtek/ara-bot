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
 * description: 整体趋势统计数据数组 DTO类 （统计多天）
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WholeTrendOverviewArrayDTO implements Serializable {
    /**
     * 日期数组
     */
    private List<String> dateArray;
    /**
     * 提问量数组
     */
    private List<Long> totalArray;
    /**
     * 转人工率数组
     */
    private List<Double> zrgRateArray;
    /**
     * 整体趋势数据数组
     */
    private List<Object> valueArray;
}
