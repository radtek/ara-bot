package com.zhuiyi.service;

import com.zhuiyi.model.ZrgOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface ZrgOverviewService extends CommonService<ZrgOverview,Long>{
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 转人工FAQ数据统计对象列表
     */
    List<ZrgOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 转人工FAQ数据统计对象列表
     */
    List<ZrgOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate);
}

