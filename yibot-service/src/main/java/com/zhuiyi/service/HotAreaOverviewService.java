package com.zhuiyi.service;

import com.zhuiyi.model.HotAreaOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface HotAreaOverviewService extends CommonService<HotAreaOverview, Long> {
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaName 区域名称
     * @return 热门地域统计对象
     */
    HotAreaOverview findByAppidAndDateSignAndAreaName(String appid, String dataSign, String areaName);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识N
     * @param dataSign 日期数据
     * @param areaType 区域类型
     * @return 热门地域统计对象列表
     */
    List<HotAreaOverview> findByAppidAndDateSignAndAreaType(String appid, String dataSign, int areaType);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @param visitNum   访问量
     * @param visitTrend   访问量振幅
     * @param areaName   地域名称
     * @param areaType   地域类型
     * @return 热门FAQ统计对象列表
     */
    List<HotAreaOverview> findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(String appid, String startDate, String endDate, int visitNum, String visitTrend, String areaName, int areaType);
}

