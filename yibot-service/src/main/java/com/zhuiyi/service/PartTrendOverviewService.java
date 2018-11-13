package com.zhuiyi.service;

import com.zhuiyi.model.PartTrendOverview;

import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface PartTrendOverviewService extends CommonService<PartTrendOverview,Long>{
    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 部分指标趋势统计对象列表
     */
    List<PartTrendOverview> findByAppidAndDateSign(String appid, String dataSign);
}

