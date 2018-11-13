package com.zhuiyi.repository;

import com.zhuiyi.model.Session;
import com.zhuiyi.model.SessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface SessionRepository extends JpaRepository<Session, SessionId>, JpaSpecificationExecutor<Session>, Serializable {

    /**
     * @param id
     * @param appid
     * @return
     */
    @Query(value = "select a from Session a where a.id=:id and a.appid = :appid")
    Session findByIdsAndAppid(@Param("id") String id, @Param("appid") String appid);

    /**
     * 按对象id查找对象
     *
     * @param id 对象标识
     * @return 返回Session对象
     */
    Session findById(String id);

    /**
     * 根据条件查找session对象
     *
     * @param id        对象标识
     * @param appid     分库标识
     * @param dateMonth 分表标识
     * @param startDate 分区区间开始
     * @param endDate   分区区间结束
     * @return 返回Session对象
     */
    Session findByIdAndAppidAndDateMonthAndStartTimeBetween(String id, String appid, String dateMonth, Date startDate, Date endDate);
    
    /**
    * 此方法描述的是：
    * @author: klauszhou@wezhuiyi.com
    * @version: 2018年8月20日 上午10:35:03
    */
    Session findByIdAndAppidAndDateMonth(String id, String appid, String dateMonth);

    /**
     * 根据对象id删除对象
     *
     * @param id 对象标识
     */
    void deleteById(String id);

    /**
     * 批量删除对象
     *
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value = "delete from Session where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<String> ids);
}

