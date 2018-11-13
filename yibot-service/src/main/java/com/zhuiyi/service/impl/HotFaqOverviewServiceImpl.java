package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotFaqOverview;
import com.zhuiyi.repository.HotFaqOverviewRepository;
import com.zhuiyi.service.HotFaqOverviewService;
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

@Service("hotFaqOverviewService")
//@CacheConfig(cacheNames = "hotFaqOverview", keyGenerator = "commonKeyGenerator")
public class HotFaqOverviewServiceImpl implements HotFaqOverviewService {

    @Autowired
    private HotFaqOverviewRepository hotFaqOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public HotFaqOverview save(HotFaqOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotFaqOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public HotFaqOverview update(HotFaqOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotFaqOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(HotFaqOverview obj, Object... values) throws InternalServiceException {
        hotFaqOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        hotFaqOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<HotFaqOverview> findAll(Object... values) {
        return hotFaqOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return hotFaqOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public HotFaqOverview findById(Long id) throws InternalServiceException {
        HotFaqOverview hotFaqOverview = hotFaqOverviewRepository.findById(id);
        if (hotFaqOverview != null) {
            return hotFaqOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public HotFaqOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param faqId    faq标识
     * @return 热门FAQ统计对象
     */
    @Override
    public HotFaqOverview findByAppidAndDateSignAndFaqId(String appid, String dataSign, int faqId) {
        return hotFaqOverviewRepository.findByAppidAndDateSignAndFaqId(appid, dataSign, faqId);
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return 热门FAQ统计对象列表
     */
    @Override
    public List<HotFaqOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return hotFaqOverviewRepository.findByAppidAndDateSign(appid, dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid      业务标识
     * @param startDate  开始的日期数据
     * @param endDate    结束的日期数据
     * @param visitNum   访问量
     * @param visitTrend 访问量振幅
     * @return 热门FAQ统计对象列表
     */
    @Override
    public List<HotFaqOverview> findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualOrderByVisitTrendDesc(String appid, String startDate, String endDate, int visitNum, String visitTrend) {
        return hotFaqOverviewRepository.findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualOrderByVisitTrendDesc(appid, startDate, endDate, visitNum, visitTrend);
    }
}
