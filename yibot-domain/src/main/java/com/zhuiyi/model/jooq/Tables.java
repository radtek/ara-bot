/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq;


import com.zhuiyi.model.jooq.tables.*;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in yibot
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 各城市faq数据统计表
     */
    public static final TCityOverview T_CITY_OVERVIEW = TCityOverview.T_CITY_OVERVIEW;

    /**
     * 机器对话流水表
     */
    public static final TDialog T_DIALOG = TDialog.T_DIALOG;

    /**
     * 原始dialog数据记录表
     */
    public static final TDialogBack T_DIALOG_BACK = TDialogBack.T_DIALOG_BACK;

    /**
     * 机器对话流水明细表
     */
    public static final TDialogDetail T_DIALOG_DETAIL = TDialogDetail.T_DIALOG_DETAIL;

    /**
     * faq触达数据统计表
     */
    public static final TFaqTouchOverview T_FAQ_TOUCH_OVERVIEW = TFaqTouchOverview.T_FAQ_TOUCH_OVERVIEW;

    /**
     * 原始feedback数据记录表
     */
    public static final TFeedbackBack T_FEEDBACK_BACK = TFeedbackBack.T_FEEDBACK_BACK;

    /**
     * 热点区域FAQ数据统计表
     */
    public static final THotAreaFqOverview T_HOT_AREA_FQ_OVERVIEW = THotAreaFqOverview.T_HOT_AREA_FQ_OVERVIEW;

    /**
     * 热点区域数据统计表
     */
    public static final THotAreaOverview T_HOT_AREA_OVERVIEW = THotAreaOverview.T_HOT_AREA_OVERVIEW;

    /**
     * 热点FAQ数据统计表
     */
    public static final THotFaqOverview T_HOT_FAQ_OVERVIEW = THotFaqOverview.T_HOT_FAQ_OVERVIEW;

    /**
     * 用户评价统计表
     */
    public static final TJudgeOverview T_JUDGE_OVERVIEW = TJudgeOverview.T_JUDGE_OVERVIEW;

    /**
     * 耗时和访问量趋势统计表
     */
    public static final TPartTrendOverview T_PART_TREND_OVERVIEW = TPartTrendOverview.T_PART_TREND_OVERVIEW;

    /**
     * 各省faq数据热点分析统计表
     */
    public static final TProvinceOverview T_PROVINCE_OVERVIEW = TProvinceOverview.T_PROVINCE_OVERVIEW;

    /**
     * 会话表
     */
    public static final TSession T_SESSION = TSession.T_SESSION;

    /**
     * FAQ触达率数据统计表
     */
    public static final TTouchOverview T_TOUCH_OVERVIEW = TTouchOverview.T_TOUCH_OVERVIEW;

    /**
     * 整体趋势统计表
     */
    public static final TWholeTrendOverview T_WHOLE_TREND_OVERVIEW = TWholeTrendOverview.T_WHOLE_TREND_OVERVIEW;

    /**
     * 转人工数据统计表
     */
    public static final TZrgOverview T_ZRG_OVERVIEW = TZrgOverview.T_ZRG_OVERVIEW;
}
