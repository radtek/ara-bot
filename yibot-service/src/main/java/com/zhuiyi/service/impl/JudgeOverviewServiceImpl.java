package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.JudgeOverview;
import com.zhuiyi.repository.JudgeOverviewRepository;
import com.zhuiyi.service.JudgeOverviewService;
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

@Service("judgeOverviewService")
//@CacheConfig(cacheNames = "judgeOverview", keyGenerator = "commonKeyGenerator")
public class JudgeOverviewServiceImpl implements JudgeOverviewService {

    @Autowired
    private JudgeOverviewRepository judgeOverviewRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public JudgeOverview save(JudgeOverview obj, String operator, Object... values) throws InternalServiceException {
        return judgeOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public JudgeOverview update(JudgeOverview obj, String operator, Object... values) throws InternalServiceException {
        return judgeOverviewRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(JudgeOverview obj, Object... values) throws InternalServiceException {
        judgeOverviewRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        judgeOverviewRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<JudgeOverview> findAll(Object... values) {
        return judgeOverviewRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return judgeOverviewRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public JudgeOverview findById(Long id) throws InternalServiceException {
        JudgeOverview judgeOverview = judgeOverviewRepository.findById(id);
        if (judgeOverview != null) {
            return judgeOverview;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public JudgeOverview findByParam(String param, Object value) {
        return null;
    }

    /**
     * 按业务标识和日期数据查询数据
     *
     * @param appid    业务标识
     * @param dataSign 日期数据
     * @return FAQ点赞和点踩统计数据对象列表
     */
    @Override
    public List<JudgeOverview> findByAppidAndDateSign(String appid, String dataSign) {
        return judgeOverviewRepository.findByAppidAndDateSign(appid,dataSign);
    }

    /**
     * 按业务标识和日期数据区间查询数据
     *
     * @param appid     业务标识
     * @param startDate 开始的日期数据
     * @param endDate   结束的日期数据
     * @return FAQ点赞和点踩统计数据对象列表
     */
    @Override
    public List<JudgeOverview> findByAppidAndDateSignBetween(String appid, String startDate, String endDate) {
        return judgeOverviewRepository.findByAppidAndDateSignBetween(appid, startDate, endDate);
    }
}
