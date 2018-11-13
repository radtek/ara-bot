package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.DialogBack;
import com.zhuiyi.repository.DialogBackRepository;
import com.zhuiyi.service.DialogBackService;
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

@Service("dialogBackService")
//@CacheConfig(cacheNames = "dialogBack", keyGenerator = "commonKeyGenerator")
public class DialogBackServiceImpl implements DialogBackService {

    @Autowired
    private DialogBackRepository dialogBackRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public DialogBack save(DialogBack obj, String operator, Object... values) throws InternalServiceException {
        return dialogBackRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public DialogBack update(DialogBack obj, String operator, Object... values) throws InternalServiceException {
        return dialogBackRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(DialogBack obj, Object... values) throws InternalServiceException {
        dialogBackRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(Long id) throws InternalServiceException {
        dialogBackRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<DialogBack> findAll(Object... values) {
        return dialogBackRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return dialogBackRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public DialogBack findById(Long id) throws InternalServiceException {
        DialogBack dialogBack = dialogBackRepository.findById(id);
        if (dialogBack != null) {
            return dialogBack;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public DialogBack findByParam(String param, Object value) {
        return null;
    }
}
