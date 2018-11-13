package com.zhuiyi.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogId;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface DialogRepository extends JpaRepository<Dialog, DialogId>, JpaSpecificationExecutor<Dialog>, Serializable{
    /**
     * 按对象id查找对象
     * @param id 对象标识
     * @return 返回Dialog对象
     */
    Dialog findById(String id);

    /**
     * 根据对象id删除对象
     * @param id 对象标识
     */
    void deleteById(String id);

    /**
     * 批量删除对象
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value="delete from Dialog where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<String> ids);
    
    public Dialog findByAppidAndDateMonthAndId(String appid, String dateMongth, String id);
    
    
    public List<Dialog> findByAppidAndDateMonthAndSessionId(String appid, String dateMongth, String sessionId, Pageable pageRequest);
}

