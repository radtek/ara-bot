package com.zhuiyi.service;

import com.zhuiyi.model.HotAreaFqOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface HotAreaFqOverviewService extends CommonService<HotAreaFqOverview,Long>{
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaName 区域名称
     * @param faqId    faq标识
     * @return 分地域的热门FAQ统计对象
     */
    HotAreaFqOverview findByAppidAndDateSignAndAreaNameAndFaqId(String appid, String dataSign, String areaName, int faqId);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaType 区域类型
     * @return 分地域的热门FAQ统计对象列表
     */
    List<HotAreaFqOverview> findByAppidAndDateSignAndAreaType(String appid, String dataSign, int areaType);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param faq FAQ问句
     * @return List<HotAreaFqOverview> 分地域的热门FAQ统计对象列表
     */
    List<HotAreaFqOverview> findByAppidAndDateSignAndFaq(String appid, String dataSign, String faq);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param dateSign 查询的日期数据
     * @return List<HotAreaFqOverview> FAQ全国热点统计分析数据对象列表
     */
    List<HotAreaFqOverview> findHotFaqByAppidAndDateSign(String appid, String dateSign);

}

