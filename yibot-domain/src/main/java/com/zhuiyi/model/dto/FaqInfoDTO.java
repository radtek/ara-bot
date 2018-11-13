package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: Faq信息统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqInfoDTO implements Serializable {
    /**
     * 用于统计的FAQ信息键
     */
    private String key;
    /**
     * 数量
     */
    private Long value;
}
