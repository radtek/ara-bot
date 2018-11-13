package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

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
public class FaqTouchRateArrayDTO implements Serializable {
    /**
     * 日期数组
     */
    private List<String> dateArray;
    /**
     * 资料库分析FAQ统计数据数组
     */
    private List<HashMap<String,Object>> valueArray;
}
