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
public class WholeTrendOverviewDTO implements Serializable {
    /**
     *  提问量（次）
     */
    private Long total;
    /**
     *  峰值（次／秒）
     */
    private Long qps;
    /**
     *  平均耗时（ms）
     */
    private Long cost;
    /**
     *  会话数（次）
     */
    private Long session;
    /**
     *  服务可用率
     */
    private String respond;
    /**
     *  直接回答数（次）
     */
    private Long direct;
    /**
     *  用户数（个）
     */
    private Long user;
    /**
     *  命中率(直接回答率)
     */
    private Double hitRate;
    /**
     *  点赞率
     */
    private Double likeRate;
    /**
     *  点赞次数
     */
    private Long like;
    /**
     *  点踩率
     */
    private Double disLikeRate;
    /**
     *  点踩次数
     */
    private Long disLike;
    /**
     *  转人工数
     */
    private Long artificial;
    /**
     *  转人工率
     */
    private Double zrgRate;
    /**
     *  推荐回答数（间接回答数）
     */
    private Long recommend;
}
