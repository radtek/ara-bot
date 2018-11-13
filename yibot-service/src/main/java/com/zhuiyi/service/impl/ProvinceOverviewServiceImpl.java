package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ProvinceOverview;
import com.zhuiyi.repository.ProvinceOverviewRepository;
import com.zhuiyi.service.ProvinceOverviewService;
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

@Service("provinceOverviewService")
//@CacheConfig(cacheNames = "provinceOverview", keyGenerator = "commonKeyGenerator")
public class ProvinceOverviewServiceImpl implements ProvinceOverviewService {

    @Autowired
    private ProvinceOverviewRepository provinceOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public ProvinceOverview save(ProvinceOverview obj, String operator, Object... values) throws InternalServiceException {
        return provinceOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public ProvinceOverview update(ProvinceOverview obj, String operator, Object... values) throws InternalServiceException {
        return provinceOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(ProvinceOverview obj, Object... values) throws InternalServiceException {
        provinceOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        provinceOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<ProvinceOverview> findAll(Object... values) {
        return provinceOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return provinceOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public ProvinceOverview findById(Long id) throws InternalServiceException {
        ProvinceOverview provinceOverview = provinceOverviewRepository.findById(id);
        if (provinceOverview != null) {
            return provinceOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public ProvinceOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 各省份FAQ数据统计对象
     */
    @Override
    public List<ProvinceOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return provinceOverviewRepository.findByAppidAndDateSign(appid, dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 各省份FAQ数据统计对象
     */
    @Override
    public List<ProvinceOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return provinceOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
