package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.PartTrendOverview;
import com.zhuiyi.repository.PartTrendOverviewRepository;
import com.zhuiyi.service.PartTrendOverviewService;
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

@Service("partTrendOverviewService")
//@CacheConfig(cacheNames = "partTrendOverview", keyGenerator = "commonKeyGenerator")
public class PartTrendOverviewServiceImpl implements PartTrendOverviewService {

    @Autowired
    private PartTrendOverviewRepository partTrendOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public PartTrendOverview save(PartTrendOverview obj, String operator, Object... values) throws InternalServiceException {
        return partTrendOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public PartTrendOverview update(PartTrendOverview obj, String operator, Object... values) throws InternalServiceException {
        return partTrendOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(PartTrendOverview obj, Object... values) throws InternalServiceException {
        partTrendOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        partTrendOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<PartTrendOverview> findAll(Object... values) {
        return partTrendOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return partTrendOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public PartTrendOverview findById(Long id) throws InternalServiceException {
        PartTrendOverview partTrendOverview = partTrendOverviewRepository.findById(id);
        if (partTrendOverview != null) {
            return partTrendOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public PartTrendOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 部分指标趋势统计对象列表
     */
    @Override
    public List<PartTrendOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return partTrendOverviewRepository.findByAppidAndDateSign(appid,dataSign);
    }
}
