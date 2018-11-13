package com.zhuiyi.service;

import com.zhuiyi.model.WholeServiceOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

public interface WholeServiceOverviewService extends CommonService<WholeServiceOverview,Long>{
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 整体数据统计对象列表
     */
    List<WholeServiceOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 整体数据统计对象
     */
    List<WholeServiceOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate);
}

