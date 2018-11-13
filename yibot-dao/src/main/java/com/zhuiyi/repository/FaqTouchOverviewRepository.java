package com.zhuiyi.repository;

import com.zhuiyi.model.FaqTouchOverview;
import com.zhuiyi.model.FaqTouchOverviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/31
 * description:
 * own: zhuiyi
 */

public interface FaqTouchOverviewRepository extends JpaRepository<FaqTouchOverview, FaqTouchOverviewId>, JpaSpecificationExecutor<FaqTouchOverview>, Serializable {
    /**
     * 按对象id查找对象
     *
     * @param id 对象标识
     * @return 返回FaqTouchOverview对象
     */
    FaqTouchOverview findById(Long id);

    /**
     * 根据对象id删除对象
     *
     * @param id 对象标识
     */
    void deleteById(Long id);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return FAQ分析数据统计对象列表
     */
    List<FaqTouchOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return FAQ分析数据统计对象
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
     * @return List<FaqTouchOverview> FAQ分析数据统计对象列表
     */
    List<FaqTouchOverview> findByAppidAndFaqIdAndBizTypeAndDateSignBetween(String appid, int faqId, int faqType, String startDate, String endDate);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return List<Object> FAQ分析数据统计对象列表
     */
    @Query(value = "SELECT date_sign,0.0 as percentage,sum(touch_num) as askCount,sum(click_good) as assentCount,sum(click_bad) as noAssentCount,sum(click_good) / (sum(click_good + click_bad)) as assentRate," +
            "sum(click_bad) / (sum(click_good + click_bad)) as noAssentRate,sum(total_zrg) as zrgCount,sum(total_zrg) / sum(touch_num) as zrgRate FROM t_faq_touch_overview WHERE appid = ?1 " +
            "AND date_sign >= ?2 AND date_sign <= ?3 GROUP BY date_sign", nativeQuery = true)
    List<Object> findSumByAppidAndDateSignBetween(@Param("appid") String appid,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid     业务标识
     * @param datesign 查询的日期数据
     * @return Object FAQ分析数据统计对象列表
     */
    @Query(value = "SELECT date_sign,0.0 as percentage,sum(touch_num) as askCount,sum(click_good) as assentCount,sum(click_bad) as noAssentCount,(sum(click_good)/(sum(click_good + click_bad))) as assentRate," +
            "(sum(click_bad)/(sum(click_good + click_bad))) as noAssentRate,sum(total_zrg) as zrgCount,(sum(total_zrg)/sum(touch_num)) as zrgRate FROM t_faq_touch_overview WHERE appid = ?1 " +
            "AND date_sign = ?2 ", nativeQuery = true)
    Object findSumByAppidAndDateSign(@Param("appid") String appid,@Param("datesign") String datesign);

    /**
     * 批量删除对象
     *
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value = "delete from FaqTouchOverview where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

