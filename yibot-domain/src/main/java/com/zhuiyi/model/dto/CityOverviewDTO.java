package com.zhuiyi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: Faq提问统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityOverviewDTO implements Serializable {
    /**
     * 总访问量
     */
    private Long total_request;
    private FaqInfoDTO p_max;
    private FaqInfoDTO p_min;
    private Long p_avg;
    private FaqInfoDTO c_max;
    private FaqInfoDTO c_min;
    private List<FaqTopDTO> c_data;
}
