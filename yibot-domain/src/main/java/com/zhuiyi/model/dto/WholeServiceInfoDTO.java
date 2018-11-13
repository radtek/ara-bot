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
public class WholeServiceInfoDTO implements Serializable {
    /**
     *  日期数据
     */
    private Double value;
    /**
     *  同比（前日）
     */
    private Double tongRatio;
    /**
     *  同比（前一周）
     */
    private Double ringRatio;
}
