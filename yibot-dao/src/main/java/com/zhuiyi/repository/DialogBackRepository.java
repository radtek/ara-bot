package com.zhuiyi.repository;

import com.zhuiyi.model.DialogBack;
import com.zhuiyi.model.DialogBackId;
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

public interface DialogBackRepository extends JpaRepository<DialogBack, DialogBackId>, JpaSpecificationExecutor<DialogBack>, Serializable{
    /**
     * 按对象id查找对象
     * @param id 对象标识
     * @return 返回DialogBack对象
     */
    DialogBack findById(Long id);

    /**
     * 根据对象id删除对象
     * @param id 对象标识
     */
    void deleteById(Long id);

    /**
     * 批量删除对象
     * @param ids 实体对象id列表
     */
    @Modifying
    @Query(value="delete from DialogBack where id in (:ids)", nativeQuery = true)
    void batchDeleteByIds(@Param("ids") List<Long> ids);
}

