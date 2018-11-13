package com.zhuiyi.repository;

import com.zhuiyi.model.HotAreaFqOverview;
import com.zhuiyi.model.HotAreaFqOverviewId;
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

public interface HotAreaFqOverviewRepository extends JpaRepository<HotAreaFqOverview, HotAreaFqOverviewId>, JpaSpecificationExecutor<HotAreaFqOverview>, Serializable {
    /**
     * 按对象id查找对象
     *
     * @param id 对象标识
     * @return 返回HotAreaFqOverview对象
     */
    HotAreaFqOverview findById(Long id);

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
     * @return 分地域的热门FAQ统计对象列表
     */
    List<HotAreaFqOverview> findByAppidAndDateSignAndFaq(String appid, String dataSign, String faq);

    /**
     * 批量删除对象
     *
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value = "delete from HotAreaFqOverview where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

