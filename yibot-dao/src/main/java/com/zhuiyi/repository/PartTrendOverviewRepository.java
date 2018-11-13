package com.zhuiyi.repository;

import com.zhuiyi.model.PartTrendOverview;
import com.zhuiyi.model.PartTrendOverviewId;
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

public interface PartTrendOverviewRepository extends JpaRepository<PartTrendOverview, PartTrendOverviewId>, JpaSpecificationExecutor<PartTrendOverview>, Serializable{
    /**
     * 按对象id查找对象
     * @param id 对象标识
     * @return 返回PartTrendOverview对象
     */
    PartTrendOverview findById(Long id);

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
     * @return 部分指标趋势统计对象
     */
    List<PartTrendOverview> findByAppidAndDateSign(String appid, String dataSign);

    /**
     * 批量删除对象
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value="delete from PartTrendOverview where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

