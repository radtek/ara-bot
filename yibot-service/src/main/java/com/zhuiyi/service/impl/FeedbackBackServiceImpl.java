package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.FeedbackBack;
import com.zhuiyi.repository.FeedbackBackRepository;
import com.zhuiyi.service.FeedbackBackService;
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

@Service("feedbackBackService")
//@CacheConfig(cacheNames = "feedbackBack", keyGenerator = "commonKeyGenerator")
public class FeedbackBackServiceImpl implements FeedbackBackService {

    @Autowired
    private FeedbackBackRepository feedbackBackRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public FeedbackBack save(FeedbackBack obj, String operator, Object... values) throws InternalServiceException {
        return feedbackBackRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public FeedbackBack update(FeedbackBack obj, String operator, Object... values) throws InternalServiceException {
        return feedbackBackRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(FeedbackBack obj, Object... values) throws InternalServiceException {
        feedbackBackRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        feedbackBackRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<FeedbackBack> findAll(Object... values) {
        return feedbackBackRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return feedbackBackRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public FeedbackBack findById(Long id) throws InternalServiceException {
        FeedbackBack feedbackBack = feedbackBackRepository.findById(id);
        if (feedbackBack != null) {
            return feedbackBack;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public FeedbackBack findByParam(String param, Object value) {
        return null;
    }
}
