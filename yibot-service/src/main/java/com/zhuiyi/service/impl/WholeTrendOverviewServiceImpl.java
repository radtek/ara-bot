package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.WholeTrendOverview;
import com.zhuiyi.repository.WholeTrendOverviewRepository;
import com.zhuiyi.service.WholeTrendOverviewService;
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

@Service("wholeTrendOverviewService")
public class WholeTrendOverviewServiceImpl implements WholeTrendOverviewService {

    @Autowired
    private WholeTrendOverviewRepository wholeTrendOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public WholeTrendOverview save(WholeTrendOverview obj, String operator, Object... values) throws InternalServiceException {
        return wholeTrendOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public WholeTrendOverview update(WholeTrendOverview obj, String operator, Object... values) throws InternalServiceException {
        return wholeTrendOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(WholeTrendOverview obj, Object... values) throws InternalServiceException {
        wholeTrendOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteById(Long id) throws InternalServiceException {
        wholeTrendOverviewRepository.deleteById(id);
    }

    @Override
    public List<WholeTrendOverview> findAll(Object... values) {
        return wholeTrendOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return wholeTrendOverviewRepository.count();
    }

    @Override
    public WholeTrendOverview findById(Long id) throws InternalServiceException {
        WholeTrendOverview wholeTrendOverview = wholeTrendOverviewRepository.findById(id);
        if (wholeTrendOverview != null) {
            return wholeTrendOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    public WholeTrendOverview findByParam(String param, Object value) {
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
    public List<WholeTrendOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return wholeTrendOverviewRepository.findByAppidAndDateSign(appid, dataSign);
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
    public List<WholeTrendOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return wholeTrendOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
