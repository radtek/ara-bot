package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.WholeServiceOverview;
import com.zhuiyi.repository.WholeServiceOverviewRepository;
import com.zhuiyi.service.WholeServiceOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

@Service("wholeServiceOverviewService")
//@CacheConfig(cacheNames = "wholeServiceOverview", keyGenerator = "commonKeyGenerator")
public class WholeServiceOverviewServiceImpl implements WholeServiceOverviewService {

    @Autowired
    private WholeServiceOverviewRepository wholeServiceOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CachePut(key = "#result.id")
    public WholeServiceOverview save(WholeServiceOverview obj, String operator, Object... values) throws InternalServiceException {
        return wholeServiceOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CachePut(key = "#obj.id")
    public WholeServiceOverview update(WholeServiceOverview obj, String operator, Object... values) throws InternalServiceException {
        return wholeServiceOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CacheEvict(key = "#obj.id")
    public void delete(WholeServiceOverview obj, Object... values) throws InternalServiceException {
        wholeServiceOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        wholeServiceOverviewRepository.deleteById(id);
    }

    @Override
//    @Cacheable(unless = "#result eq null")
    public List<WholeServiceOverview> findAll(Object... values) {
        return wholeServiceOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return wholeServiceOverviewRepository.count();
    }

    @Override
//    @Cacheable(key = "#id", unless = "#result eq null")
    public WholeServiceOverview findById(Long id) throws InternalServiceException {
        WholeServiceOverview wholeServiceOverviewTemp = wholeServiceOverviewRepository.findById(id);
        if(wholeServiceOverviewTemp != null){
            return wholeServiceOverviewTemp;
        }else{
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
//    @Cacheable(unless = "#result eq null")
    public WholeServiceOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 整体数据统计对象列表
     */
    @Override
    public List<WholeServiceOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return wholeServiceOverviewRepository.findByAppidAndDateSign(appid, dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return 整体数据统计对象
     */
    @Override
    public List<WholeServiceOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return wholeServiceOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
