package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.TouchOverview;
import com.zhuiyi.repository.TouchOverviewRepository;
import com.zhuiyi.service.TouchOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@Service("touchOverviewService")
//@CacheConfig(cacheNames = "touchOverview", keyGenerator = "commonKeyGenerator")
public class TouchOverviewServiceImpl implements TouchOverviewService {

    @Autowired
    private TouchOverviewRepository touchOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public TouchOverview save(TouchOverview obj, String operator, Object... values) throws InternalServiceException {
        return touchOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public TouchOverview update(TouchOverview obj, String operator, Object... values) throws InternalServiceException {
        return touchOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(TouchOverview obj, Object... values) throws InternalServiceException {
        touchOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        touchOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<TouchOverview> findAll(Object... values) {
        return touchOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return touchOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public TouchOverview findById(Long id) throws InternalServiceException {
        TouchOverview touchOverview = touchOverviewRepository.findById(id);
        if (touchOverview != null) {
            return touchOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public TouchOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return FAQ触达率数据统计对象列表
     */
    @Override
    public List<TouchOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return touchOverviewRepository.findByAppidAndDateSign(appid, dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return FAQ触达率数据统计对象
     */
    @Override
    public List<TouchOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return touchOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
