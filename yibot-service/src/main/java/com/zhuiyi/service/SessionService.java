package com.zhuiyi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Session;
import com.zhuiyi.model.dto.SearchLogReqDto;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface SessionService extends CommonService<Session,String>{


	public Session findByIdAndAppid(String id,String appid) throws InternalServiceException;

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
     * 根据条件查找session对象
     *
     * @param id        对象标识
     * @param appid     分库标识
     * @param dateMonth 分表标识
     * @return 返回Session对象
     */
    Session findByIdAndAppidAndDateMonth(String id, String appid, String dateMonth);
    
    
    List<Map<String,Object>> searchSessions(SearchLogReqDto search) ;
}

