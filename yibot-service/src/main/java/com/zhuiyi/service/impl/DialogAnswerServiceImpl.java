package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.DialogAnswer;
import com.zhuiyi.repository.DialogAnswerRepository;
import com.zhuiyi.service.DialogAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/14
 * description:
 * own: zhuiyi
 */

@Service("dialogAnswerService")
//@CacheConfig(cacheNames = "dialogAnswer", keyGenerator = "commonKeyGenerator")
public class DialogAnswerServiceImpl implements DialogAnswerService {

    @Autowired
    private DialogAnswerRepository dialogAnswerRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public DialogAnswer save(DialogAnswer obj, String operator, Object... values) throws InternalServiceException {
        return dialogAnswerRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public DialogAnswer update(DialogAnswer obj, String operator, Object... values) throws InternalServiceException {
        return dialogAnswerRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(DialogAnswer obj, Object... values) throws InternalServiceException {
        dialogAnswerRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(String id) throws InternalServiceException {
        dialogAnswerRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<DialogAnswer> findAll(Object... values) {
        return dialogAnswerRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return dialogAnswerRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public DialogAnswer findById(String id) throws InternalServiceException {
        DialogAnswer dialogAnswerTemp = dialogAnswerRepository.findById(id);
        if(dialogAnswerTemp != null){
            return dialogAnswerTemp;
        }else{
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public DialogAnswer findByParam(String param, Object value) {
        return null;
    }
}
