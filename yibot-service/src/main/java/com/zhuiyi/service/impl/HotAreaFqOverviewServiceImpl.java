package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotAreaFqOverview;
import com.zhuiyi.repository.HotAreaFqOverviewRepository;
import com.zhuiyi.service.HotAreaFqOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@Service("hotAreaFqOverviewService")
//@CacheConfig(cacheNames = "hotAreaFqOverview", keyGenerator = "commonKeyGenerator")
public class HotAreaFqOverviewServiceImpl implements HotAreaFqOverviewService {

    @Autowired
    private HotAreaFqOverviewRepository hotAreaFqOverviewRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public HotAreaFqOverview save(HotAreaFqOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotAreaFqOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public HotAreaFqOverview update(HotAreaFqOverview obj, String operator, Object... values) throws InternalServiceException {
        return hotAreaFqOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(HotAreaFqOverview obj, Object... values) throws InternalServiceException {
        hotAreaFqOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        hotAreaFqOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<HotAreaFqOverview> findAll(Object... values) {
        return hotAreaFqOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return hotAreaFqOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public HotAreaFqOverview findById(Long id) throws InternalServiceException {
        HotAreaFqOverview hotAreaFqOverview = hotAreaFqOverviewRepository.findById(id);
        if (hotAreaFqOverview != null) {
            return hotAreaFqOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public HotAreaFqOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaName 区域名称
     * @param faqId    faq标识
     * @return 分地域的热门FAQ统计对象
     */
    @Override
    public HotAreaFqOverview findByAppidAndDateSignAndAreaNameAndFaqId(String appid, String dataSign, String areaName, int faqId) {
        return hotAreaFqOverviewRepository.findByAppidAndDateSignAndAreaNameAndFaqId(appid, dataSign, areaName, faqId);
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param areaType 区域类型
     * @return 分地域的热门FAQ统计对象列表
     */
    @Override
    public List<HotAreaFqOverview> findByAppidAndDateSignAndAreaType(String appid, String dataSign, int areaType) {
        return hotAreaFqOverviewRepository.findByAppidAndDateSignAndAreaType(appid, dataSign, areaType);
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @param faq      FAQ问句
     * @return 分地域的热门FAQ统计对象列表
     */
    @Override
    public List<HotAreaFqOverview> findByAppidAndDateSignAndFaq(String appid, String dataSign, String faq) {
        return hotAreaFqOverviewRepository.findByAppidAndDateSignAndFaq(appid, dataSign, faq);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param dateSign 查询的日期数据
     * @return List<HotAreaFqOverview> FAQ全国热点统计分析数据对象列表
     */
    @Override
    public List<HotAreaFqOverview> findHotFaqByAppidAndDateSign(String appid, String dateSign) {
        List<HotAreaFqOverview> hotAreaFqOverviewList = new ArrayList<>();
        //直接从数据库中捞取一天的数据进行分析
        String sql = "select area_name,faq,visit_num,visit_trend " +
                "from t_hot_area_fq_overview where appid = '" + appid + "' and date_sign = '" + dateSign + "' and faq <> '-'  and visit_trend > 0 " +
                "and  visit_num >= (select avg(visit_num) from t_hot_area_fq_overview where appid = '" + appid + "' and date_sign = '" + dateSign + "' and faq <> '-') order by visit_trend desc";
        List<Object> objList = entityManager.createNativeQuery(sql).getResultList();
        if (objList.size() > 0) {
            objList.forEach(x -> {
                HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();
                Object[] cells = (Object[]) x;
                hotAreaFqOverviewTemp.setAreaName((String) cells[0]);
                hotAreaFqOverviewTemp.setFaq((String) cells[1]);
                hotAreaFqOverviewTemp.setVisitNum((int) cells[2]);
                hotAreaFqOverviewTemp.setVisitTrend((String) cells[3]);
                hotAreaFqOverviewList.add(hotAreaFqOverviewTemp);
            });
        }
        return hotAreaFqOverviewList;
    }
}
