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
public class WholeServiceOverviewArrayDTO implements Serializable {
    /**
     * 日期数组
     */
    private List<String> dateArray;
    /**
     * 提问量数组
     */
    private List<WholeTrendOverviewDTO> valueArray;
}
