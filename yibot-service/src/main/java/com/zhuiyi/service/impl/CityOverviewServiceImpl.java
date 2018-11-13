package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.CityOverview;
import com.zhuiyi.repository.CityOverviewRepository;
import com.zhuiyi.service.CityOverviewService;
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

@Service("cityOverviewService")
//@CacheConfig(cacheNames = "cityOverview", keyGenerator = "commonKeyGenerator")
public class CityOverviewServiceImpl implements CityOverviewService {

    @Autowired
    private CityOverviewRepository cityOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public CityOverview save(CityOverview obj, String operator, Object... values) throws InternalServiceException {
        return cityOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public CityOverview update(CityOverview obj, String operator, Object... values) throws InternalServiceException {
        return cityOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(CityOverview obj, Object... values) throws InternalServiceException {
        cityOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        cityOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<CityOverview> findAll(Object... values) {
        return cityOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return cityOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public CityOverview findById(Long id) throws InternalServiceException {
        CityOverview cityOverview = cityOverviewRepository.findById(id);
        if (cityOverview != null) {
            return cityOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public CityOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 各城市FAQ统计对象列表
     */
    @Override
    public List<CityOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return cityOverviewRepository.findByAppidAndDateSign(appid,dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 各城市FAQ统计对象列表
     */
    @Override
    public List<CityOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return cityOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
