package com.zhuiyi.service;

import com.zhuiyi.model.HotFaqOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface HotFaqOverviewService extends CommonService<HotFaqOverview, Long> {
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param faqId    faq标识
     * @return 热门FAQ统计对象
     */
    HotFaqOverview findByAppidAndDateSignAndFaqId(String appid, String dataSign, int faqId);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 热门FAQ统计对象列表
     */
    List<HotFaqOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @param visitNum   访问量
     * @param visitTrend   访问量振幅
     * @return 热门FAQ统计对象列表
     */
    List<HotFaqOverview> findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualOrderByVisitTrendDesc(String appid, String startDate, String endDate, int visitNum, String visitTrend);
}

