package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotAreaOverview;
import com.zhuiyi.repository.HotAreaOverviewRepository;
import com.zhuiyi.service.HotAreaOverviewService;
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

@Service("hotAreaOverviewService")
//@CacheConfig(cacheNames = "hotAreaOverview", keyGenerator = "commonKeyGenerator")
public class HotAreaOverviewServiceImpl implements HotAreaOverviewService {

    @Autowired
    private HotAreaOverviewRepository hotAreaOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public HotAreaOverview save(HotAreaOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotAreaOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public HotAreaOverview update(HotAreaOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotAreaOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(HotAreaOverview obj, Object... values) throws InternalServiceException {
        hotAreaOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        hotAreaOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<HotAreaOverview> findAll(Object... values) {
        return hotAreaOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return hotAreaOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public HotAreaOverview findById(Long id) throws InternalServiceException {
        HotAreaOverview hotAreaOverview = hotAreaOverviewRepository.findById(id);
        if (hotAreaOverview != null) {
            return hotAreaOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public HotAreaOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaName 区域名称
     * @return 热门地域统计对象
     */
    @Override
    public HotAreaOverview findByAppidAndDateSignAndAreaName(String appid, String dataSign, String areaName) {
        return hotAreaOverviewRepository.findByAppidAndDateSignAndAreaName(appid, dataSign, areaName);
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaType 区域类型
     * @return 热门地域统计对象列表
     */
    @Override
    public List<HotAreaOverview> findByAppidAndDateSignAndAreaType(String appid, String dataSign, int areaType) {
        return hotAreaOverviewRepository.findByAppidAndDateSignAndAreaType(appid, dataSign, areaType);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid      业务标识
     * @param startDate  开始的日期数据
     * @param endDate    结束的日期数据
     * @param visitNum   访问量
     * @param visitTrend 访问量振幅
     * @param areaName   地域名称
     * @param areaType   地域类型
     * @return 热门FAQ统计对象列表
     */
    @Override
    public List<HotAreaOverview> findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(String appid, String startDate, String endDate, int visitNum, String visitTrend, String areaName, int areaType) {
        return hotAreaOverviewRepository.findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(appid, startDate, endDate, visitNum, visitTrend, areaName, areaType);
    }
}
