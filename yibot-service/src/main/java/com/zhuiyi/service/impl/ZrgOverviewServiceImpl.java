package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ZrgOverview;
import com.zhuiyi.repository.ZrgOverviewRepository;
import com.zhuiyi.service.ZrgOverviewService;
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

@Service("zrgOverviewService")
//@CacheConfig(cacheNames = "zrgOverview", keyGenerator = "commonKeyGenerator")
public class ZrgOverviewServiceImpl implements ZrgOverviewService {

    @Autowired
    private ZrgOverviewRepository zrgOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public ZrgOverview save(ZrgOverview obj, String operator, Object... values) throws InternalServiceException {
        return zrgOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public ZrgOverview update(ZrgOverview obj, String operator, Object... values) throws InternalServiceException {
        return zrgOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(ZrgOverview obj, Object... values) throws InternalServiceException {
        zrgOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        zrgOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<ZrgOverview> findAll(Object... values) {
        return zrgOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return zrgOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public ZrgOverview findById(Long id) throws InternalServiceException {
        ZrgOverview zrgOverview = zrgOverviewRepository.findById(id);
        if (zrgOverview != null) {
            return zrgOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public ZrgOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 转人工FAQ数据统计对象列表
     */
    @Override
    public List<ZrgOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return zrgOverviewRepository.findByAppidAndDateSign(appid, dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 转人工FAQ数据统计对象列表
     */
    @Override
    public List<ZrgOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return zrgOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
