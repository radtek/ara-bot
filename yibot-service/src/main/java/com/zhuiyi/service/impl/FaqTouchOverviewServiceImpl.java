package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.FaqTouchOverview;
import com.zhuiyi.model.dto.FaqTouchOverviewDTO;
import com.zhuiyi.model.dto.FaqTouchRateDTO;
import com.zhuiyi.repository.FaqTouchOverviewRepository;
import com.zhuiyi.service.FaqTouchOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@Service("faqTouchOverviewService")
//@CacheConfig(cacheNames = "faqTouchOverview", keyGenerator = "commonKeyGenerator")
public class FaqTouchOverviewServiceImpl implements FaqTouchOverviewService {

    @Autowired
    private FaqTouchOverviewRepository faqTouchOverviewRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public FaqTouchOverview save(FaqTouchOverview obj, String operator, Object... values) throws InternalServiceException {
        return faqTouchOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public FaqTouchOverview update(FaqTouchOverview obj, String operator, Object... values) throws InternalServiceException {
        return faqTouchOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(FaqTouchOverview obj, Object... values) throws InternalServiceException {
        faqTouchOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        faqTouchOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<FaqTouchOverview> findAll(Object... values) {
        return faqTouchOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return faqTouchOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public FaqTouchOverview findById(Long id) throws InternalServiceException {
        FaqTouchOverview faqTouchOverview = faqTouchOverviewRepository.findById(id);
        if (faqTouchOverview != null) {
            return faqTouchOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public FaqTouchOverview findByParam(String param, Object value) {
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
    public List<FaqTouchOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return faqTouchOverviewRepository.findByAppidAndDateSign(appid, dataSign);
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
    public List<FaqTouchOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return faqTouchOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param faqId     FAQ标识
     * @param faqType   FAQ类型
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    @Override
    public List<FaqTouchRateDTO> findByAppidAndFaqIdAndBizTypeAndDateSignBetween(String appid, int faqId, int faqType, String startDate, String endDate) {
        List<FaqTouchRateDTO> faqTouchOverviewDTOList = new ArrayList<>();
        faqTouchOverviewRepository.findByAppidAndFaqIdAndBizTypeAndDateSignBetween(appid, faqId, faqType, startDate, endDate).forEach(x -> {
            FaqTouchRateDTO faqTouchOverviewDTOTemp = new FaqTouchRateDTO();
            faqTouchOverviewDTOTemp.setDataSign(x.getDateSign());
            faqTouchOverviewDTOTemp.setAskCount(x.getTouchNum());
            faqTouchOverviewDTOTemp.setPercentage(Double.parseDouble(x.getTouchRate()));
            faqTouchOverviewDTOTemp.setAssentCount(x.getClickGood());
            faqTouchOverviewDTOTemp.setAssentRate(Double.parseDouble(x.getClickGoodRate()));
            faqTouchOverviewDTOTemp.setNoAssentCount(x.getClickBad());
            faqTouchOverviewDTOTemp.setNoAssentRate(Double.parseDouble(x.getClickBadRate()));
            faqTouchOverviewDTOTemp.setZrgCount(x.getZdZrg());
            faqTouchOverviewDTOTemp.setZrgRate(Double.parseDouble(x.getZrgRate()));
            faqTouchOverviewDTOList.add(faqTouchOverviewDTOTemp);
        });
        return faqTouchOverviewDTOList;
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param dateArray 查询的日期数组
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    @Override
    public List<FaqTouchRateDTO> findSumByAppidAndDateArray(String appid, List<String> dateArray) {
        List<FaqTouchRateDTO> faqTouchOverviewDTOList = new ArrayList<>();
        dateArray.forEach(x -> {
            Object[] objArray = (Object[])faqTouchOverviewRepository.findSumByAppidAndDateSign(appid, x);
            FaqTouchRateDTO faqTouchOverviewDTOTemp = new FaqTouchRateDTO();
            faqTouchOverviewDTOTemp.setDataSign(x);
            if(null != objArray[0]){
                faqTouchOverviewDTOTemp.setPercentage(((BigDecimal)objArray[1]).doubleValue());
                faqTouchOverviewDTOTemp.setAskCount(((BigDecimal)objArray[2]).intValue());
                faqTouchOverviewDTOTemp.setAssentCount(((BigDecimal)objArray[3]).intValue());
                faqTouchOverviewDTOTemp.setNoAssentCount(((BigDecimal)objArray[4]).intValue());
                faqTouchOverviewDTOTemp.setAssentRate(((BigDecimal)objArray[5]).doubleValue());
                faqTouchOverviewDTOTemp.setNoAssentRate(((BigDecimal) objArray[6]).doubleValue());
                faqTouchOverviewDTOTemp.setZrgCount(((BigDecimal)objArray[7]).intValue());
                faqTouchOverviewDTOTemp.setZrgRate(((BigDecimal)objArray[8]).doubleValue());
            }else{
                faqTouchOverviewDTOTemp.setPercentage(0D);
                faqTouchOverviewDTOTemp.setAskCount(0);
                faqTouchOverviewDTOTemp.setAssentCount(0);
                faqTouchOverviewDTOTemp.setNoAssentCount(0);
                faqTouchOverviewDTOTemp.setAssentRate(0D);
                faqTouchOverviewDTOTemp.setNoAssentRate(0D);
                faqTouchOverviewDTOTemp.setZrgCount(0);
                faqTouchOverviewDTOTemp.setZrgRate(0D);
            }
            faqTouchOverviewDTOList.add(faqTouchOverviewDTOTemp);
        });
        return faqTouchOverviewDTOList;
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return List<FaqTouchRateDTO> FAQ分析数据统计对象列表
     */
    @Override
    public List<FaqTouchRateDTO> findSumByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        List<FaqTouchRateDTO> faqTouchOverviewDTOList = new ArrayList<>();
        faqTouchOverviewRepository.findSumByAppidAndDateSignBetween(appid, startDate, endDate).forEach(x -> {
            FaqTouchRateDTO faqTouchOverviewDTOTemp = new FaqTouchRateDTO();
            Object[] objArray = (Object[]) x;
            faqTouchOverviewDTOTemp.setDataSign((String) objArray[0]);
            faqTouchOverviewDTOTemp.setAskCount((Integer) objArray[1]);
            faqTouchOverviewDTOTemp.setPercentage((Double) objArray[2]);
            faqTouchOverviewDTOTemp.setAssentCount((Integer) objArray[3]);
            faqTouchOverviewDTOTemp.setAssentRate((Double) objArray[4]);
            faqTouchOverviewDTOTemp.setNoAssentCount((Integer) objArray[5]);
            faqTouchOverviewDTOTemp.setNoAssentRate((Double) objArray[6]);
            faqTouchOverviewDTOTemp.setZrgCount((Integer) objArray[7]);
            faqTouchOverviewDTOTemp.setZrgRate((Double) objArray[8]);
            faqTouchOverviewDTOList.add(faqTouchOverviewDTOTemp);
        });
        return faqTouchOverviewDTOList;
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @param faqId     查询的日期数据
     * @param faqType   查询的日期数据
     * @param labels    渠道条件
     * @param sortField 排序字段
     * @param sortType  排序类型
     * @param pageNo    当前页数
     * @param pageSize  每页数量
     * @return List<FaqTouchOverviewDTO> FAQ标准问统计分析数据对象列表
     */
    @Override
    public List<FaqTouchOverviewDTO> findFaqPageByAppidAndDateSignBetween(String appid, String startDate, String endDate, Integer faqId, Integer faqType, String labels, String sortField, Integer sortType, Integer pageNo, Integer pageSize) {
        List<FaqTouchOverviewDTO> faqTouchOverviewDTOList = new ArrayList<>();
        //直接从数据库中捞取一天的数据进行分析
        String partSql = "select faq, faq_id, biz_type, touch_num,  touch_rate,sum(click_good+click_bad) AS commentCount, click_good,click_good_rate, " +
                "click_bad,click_bad_rate, total_zrg,zrg_rate " +
                "from t_faq_touch_overview where appid = '" + appid + "' and date_sign >= '" + startDate + "' and date_sign <= '" + endDate + "' ";

//        String partSql = "select faq, faq_id, biz_type, sum(touch_num) as touchCount,  sum(click_good) AS assentCount,sum(click_bad) AS noAssentCount," +
//                "sum(click_good+click_bad) AS commentCount, sum(click_good) / (sum(click_good + click_bad)) AS assentRate,  " +
//                "sum(click_bad) / (sum(click_good + click_bad)) AS noAssentRate, sum(total_zrg) AS zrgCount,sum(total_zrg) / sum(touch_num) AS zrgRate," +
//                "from t_faq_touch_overview where appid = '" + appid + "' and date_sign >= '" + startDate + "' and date_sign <= '" + endDate + "' ";
        String groupSQL = " GROUP BY faq, biz_type ";
        String orderSQL =  " ORDER BY " + sortField;
//        String limitSQL = " limit " + (pageNo-1) * pageSize +","+ pageSize;
        if (faqId != null && faqType != null) {
            partSql += " and faq_id = " + faqId + " and biz_type = " + faqType + " ";
        }else{
            if (1 == sortType) {
                orderSQL += " desc ";
            }
        }
        if (labels != null) {
            partSql += " and labels = " + labels +  " ";
        }
        String sql = partSql + groupSQL + orderSQL;
        List<Object> objList = entityManager.createNativeQuery(sql).getResultList();

        if (objList.size() > 0) {
            objList.forEach(x -> {
                FaqTouchOverviewDTO faqTouchOverviewDTOTemp = new FaqTouchOverviewDTO();
                Object[] cells = (Object[]) x;
                faqTouchOverviewDTOTemp.setQuestion((String) cells[0]);
                faqTouchOverviewDTOTemp.setTouchCount((int) cells[3]);
                faqTouchOverviewDTOTemp.setTouchRate(Double.valueOf((String)cells[4]));
                faqTouchOverviewDTOTemp.setCommentCount(((BigDecimal) cells[5]).intValue());
                faqTouchOverviewDTOTemp.setAssentCount((int) cells[6]);
                faqTouchOverviewDTOTemp.setAssentRate(Double.valueOf((String) cells[7]));
                faqTouchOverviewDTOTemp.setNoAssentCount((int) cells[8]);
                faqTouchOverviewDTOTemp.setNoAssentRate(Double.valueOf((String) cells[9]));
                faqTouchOverviewDTOTemp.setZrgCount((int) cells[10]);
                faqTouchOverviewDTOTemp.setZrgRate(Double.valueOf((String) cells[11]));
                //目前默认不计算
                faqTouchOverviewDTOTemp.setEmotionCount(0);
                faqTouchOverviewDTOTemp.setEmotionRate(0D);
                faqTouchOverviewDTOList.add(faqTouchOverviewDTOTemp);
            });
        }
        return faqTouchOverviewDTOList;
    }
}
