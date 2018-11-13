package com.zhuiyi.repository;

import com.zhuiyi.model.ProvinceOverview;
import com.zhuiyi.model.ProvinceOverviewId;
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

public interface ProvinceOverviewRepository extends JpaRepository<ProvinceOverview, ProvinceOverviewId>, JpaSpecificationExecutor<ProvinceOverview>, Serializable{
    /**
     * 按对象id查找对象
     * @param id 对象标识
     * @return 返回ProvinceOverview对象
     */
    ProvinceOverview findById(Long id);

    /**
     * 根据对象id删除对象
     * @param id 对象标识
     */
    void deleteById(Long id);

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 各省份FAQ数据统计对象
     */
    List<ProvinceOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 各省份FAQ数据统计对象
     */
    List<ProvinceOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate);

    /**
     * 批量删除对象
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value="delete from ProvinceOverview where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

