package com.zhuiyi.service.impl;

import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.IpAreaMatch;
import com.zhuiyi.repository.IpAreaMatchRepository;
import com.zhuiyi.service.IpAreaMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

@Service("ipAreaMatchService")
//@CacheConfig(cacheNames = "ipAreaMatch", keyGenerator = "commonKeyGenerator")
public class IpAreaMatchServiceImpl implements IpAreaMatchService {

    @Autowired
    private IpAreaMatchRepository ipAreaMatchRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CachePut(key = "#result.id")
    public IpAreaMatch save(IpAreaMatch obj, String operator, Object... values) throws InternalServiceException {
        return ipAreaMatchRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CachePut(key = "#obj.id")
    public IpAreaMatch update(IpAreaMatch obj, String operator, Object... values) throws InternalServiceException {
        return ipAreaMatchRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CacheEvict(key = "#obj.id")
    public void delete(IpAreaMatch obj, Object... values) throws InternalServiceException {
        ipAreaMatchRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
//    @CacheEvict(key = "#id")
    public void deleteById(String id) throws InternalServiceException {
        ipAreaMatchRepository.deleteById(id);
    }

    @Override
//    @Cacheable(unless = "#result eq null")
    public List<IpAreaMatch> findAll(Object... values) {
        return ipAreaMatchRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return ipAreaMatchRepository.count();
    }

    @Override
//    @Cacheable(key = "#id", unless = "#result eq null")
    public IpAreaMatch findById(String id) throws InternalServiceException {
        try {
			Optional<IpAreaMatch> ipAreaMatchOptional = ipAreaMatchRepository.findById(id);
			if (ipAreaMatchOptional.isPresent()) {
			    return ipAreaMatchOptional.get();
			}else {
			    return null;
			}
		} catch (Exception e) {
			throw new InternalServiceException(e.getMessage());
		}
    }

    @Override
//    @Cacheable(unless = "#result eq null")
    public IpAreaMatch findByParam(String param, Object value) {
        return null;
    }
}
