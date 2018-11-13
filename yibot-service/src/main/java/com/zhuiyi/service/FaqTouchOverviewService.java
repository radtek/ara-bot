package com.zhuiyi.service;

import com.zhuiyi.model.FaqTouchOverview;
import com.zhuiyi.model.dto.FaqTouchOverviewDTO;
import com.zhuiyi.model.dto.FaqTouchRateDTO;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface FaqTouchOverviewService extends CommonService<FaqTouchOverview,Long>{
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return FAQ触达率数据统计对象列表
     */
    List<FaqTouchOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param dateArray 查询的日期数组
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    List<FaqTouchRateDTO> findSumByAppidAndDateArray(String appid, List<String> dateArray);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return FAQ触达率数据统计对象
     */
    List<FaqTouchOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param faqId     FAQ标识
     * @param faqType   FAQ类型
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    List<FaqTouchRateDTO> findByAppidAndFaqIdAndBizTypeAndDateSignBetween(String appid, int faqId, int faqType, String startDate, String endDate);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    List<FaqTouchRateDTO> findSumByAppidAndDateSignBetween(String appid, String startDate, String endDate);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @param faqId      查询的日期数据
     * @param faqType      查询的日期数据
     * @param labels    渠道条件
     * @param sortField   排序字段
     * @param sortType   排序类型
     * @param pageNo   当前页数
     * @param pageSize   每页数量
     * @return List<FaqTouchOverviewDTO> FAQ标准问统计分析数据对象列表
     */
    List<FaqTouchOverviewDTO> findFaqPageByAppidAndDateSignBetween(String appid, String startDate, String endDate, Integer faqId, Integer faqType, String labels, String sortField, Integer sortType, Integer pageNo, Integer pageSize);
}

