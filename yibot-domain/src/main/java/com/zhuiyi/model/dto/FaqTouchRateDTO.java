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
public class FaqTouchRateDTO implements Serializable {
    /**
     * 访问量
     */
    private String dataSign;
    private Integer askCount;
    private Double percentage;
    private Integer assentCount;
    private Double assentRate;
    private Integer noAssentCount;
    private Double noAssentRate;
    private Integer zrgCount;
    private Double zrgRate;
}
