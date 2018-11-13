package com.zhuiyi.service;

import com.zhuiyi.exception.InternalServiceException;
import java.util.List;
import java.io.Serializable;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface CommonService<T,ID extends Serializable>  {
    /**
     * 保存对象信息
     * @param obj（对象）
     * @param operator（操作者）
     * @param values（泛型参数，主要是为了适配各种模型）
     * @throws InternalServiceException 系统内部业务逻辑层异常
     * @return
     */
    T save(T obj, String operator, Object... values) throws InternalServiceException;

    /**
     * 修改对象信息
     * @param obj（对象）
     * @param operator（操作者）
     * @param values（泛型参数，主要是为了适配各种模型）
     * @throws InternalServiceException 系统内部业务逻辑层异常
     * @return boolean  是否成功
     */
    T update(T obj, String operator, Object... values) throws InternalServiceException;

    /**
     * 删除对象
     * @param obj（对象）
     * @param values（泛型参数，主要是为了适配各种模型）
     * @throws InternalServiceException 系统内部业务逻辑层异常
     * @return 对象
     */
    void delete(T obj, Object... values) throws InternalServiceException;

    /**
     * 按对象标识删除对象
     * @param id（对象id）
     * @throws InternalServiceException 系统内部业务逻辑层异常
     * @return 对象
     */
    void deleteById(ID id) throws InternalServiceException;

    /**
     * 获取对象列表
     * @param values（查询条件）
     * @return 对象list
     */
    List<T> findAll(Object... values);

    /**
     * 获取对象总数
     * @param values（查询条件）
     * @return 对象总数
     */
    Long count(Object... values);

    /**
     * 根据id获取对象
     * @param id（对象id）
     * @throws InternalServiceException 系统内部业务逻辑层异常
     * @return 对象
     */
    T findById(ID id)  throws InternalServiceException;

    /**
     * 根据属性以及属性值获取对象
     * @param param 属性
     * @param value 属性值
     * @return 对象
     */
    T findByParam(String param, Object value);
}
