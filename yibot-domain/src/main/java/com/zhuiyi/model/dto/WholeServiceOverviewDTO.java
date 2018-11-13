package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

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
public class WholeServiceOverviewDTO implements Serializable {
    /**
     *  日期数据
     */
    private String data_sign;
    /**
     *  提问量（次）
     */
    private HashMap<String,Object> total;
    /**
     *  峰值（次／秒）
     */
    private HashMap<String,Object> qps;
    /**
     *  平均耗时（ms）
     */
    private HashMap<String,Object> cost;
    /**
     *  会话数（次）
     */
    private HashMap<String,Object> session;
    /**
     *  服务可用率
     */
    private HashMap<String,Object> respond;
    /**
     *  直接回答数（次）
     */
    private HashMap<String,Object> direct;
    /**
     *  用户数（个）
     */
    private HashMap<String,Object> user;
    /**
     *  命中率(直接回答率)
     */
    private HashMap<String,Object> hitRate;
    /**
     *  点赞率
     */
    private HashMap<String,Object> likeRate;
    /**
     *  点赞次数
     */
    private HashMap<String,Object> like;
    /**
     *  点踩率
     */
    private HashMap<String,Object> disLikeRate;
    /**
     *  点踩次数
     */
    private HashMap<String,Object> disLike;
    /**
     *  转人工数
     */
    private HashMap<String,Object> artificial;
    /**
     *  转人工率
     */
    private HashMap<String,Object> zrgRate;
    /**
     *  推荐回答数（间接回答数）
     */
    private HashMap<String,Object> recommend;
}
