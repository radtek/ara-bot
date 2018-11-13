package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.repository.DialogDetailRepository;
import com.zhuiyi.service.DialogDetailService;
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

@Service("dialogDetailService")
//@CacheConfig(cacheNames = "dialogDetail", keyGenerator = "commonKeyGenerator")
public class DialogDetailServiceImpl implements DialogDetailService {

    @Autowired
    private DialogDetailRepository dialogDetailRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#result.id")
    public DialogDetail save(DialogDetail obj, String operator, Object... values) throws InternalServiceException {
        return dialogDetailRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CachePut(key = "#obj.id")
    public DialogDetail update(DialogDetail obj, String operator, Object... values) throws InternalServiceException {
        return dialogDetailRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(DialogDetail obj, Object... values) throws InternalServiceException {
        dialogDetailRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(String id) throws InternalServiceException {
        dialogDetailRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<DialogDetail> findAll(Object... values) {
        return dialogDetailRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return dialogDetailRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public DialogDetail findById(String id) throws InternalServiceException {
        DialogDetail dialogDetailTemp = dialogDetailRepository.findById(id);
        if(dialogDetailTemp != null){
            return dialogDetailTemp;
        }else{
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public DialogDetail findByParam(String param, Object value) {
        return null;
    }
}
