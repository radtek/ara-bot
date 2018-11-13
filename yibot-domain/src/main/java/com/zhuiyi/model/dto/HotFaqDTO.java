package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: 热门Faq信息统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotFaqDTO implements Serializable {
    /**
     * 用于统计的FAQ信息键
     */
    private Long ask;
    /**
     * 数量
     */
    private String askTrend;
    /**
     * 热门FAQ列表
     */
    private List<Map<String,Object>> hot;
}
