package com.zhuiyi.repository;

import com.zhuiyi.model.HotAreaOverview;
import com.zhuiyi.model.HotAreaOverviewId;
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

public interface HotAreaOverviewRepository extends JpaRepository<HotAreaOverview, HotAreaOverviewId>, JpaSpecificationExecutor<HotAreaOverview>, Serializable {
    /**
     * 按对象id查找对象
     *
     * @param id 对象标识
     * @return 返回HotAreaOverview对象
     */
    HotAreaOverview findById(Long id);

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
     * @param areaName 区域名称
     * @return 热门地域统计对象
     */
    HotAreaOverview findByAppidAndDateSignAndAreaName(String appid, String dataSign, String areaName);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
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
     * @param areaName   地域名称
     * @param areaType   地域类型
     * @param visitNum   访问量
     * @param visitTrend   访问量振幅
     * @return 热门FAQ统计对象列表
     */
    List<HotAreaOverview> findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(String appid, String startDate, String endDate, int visitNum, String visitTrend, String areaName, int areaType);

    /**
     * 批量删除对象
     *
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value = "delete from HotAreaOverview where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

