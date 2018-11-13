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
 * description: FAQ标准问统计分析数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqTouchOverviewArrayDTO implements Serializable {
    /**
     * 访问量
     */
    private Integer count;
    private List<FaqTouchOverviewDTO> rows;
}
