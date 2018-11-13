package com.zhuiyi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.BizUtil;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Session;
import com.zhuiyi.model.dto.SearchLogReqDto;
import com.zhuiyi.repository.SessionRepository;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.SessionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@Service("sessionService")
//@CacheConfig(cacheNames = "session", keyGenerator = "commonKeyGenerator")
@Slf4j
public class SessionServiceImpl implements SessionService {

	
    @PersistenceContext
    EntityManager em;
    
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private RedisService redisService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Session save(Session obj, String operator, Object... values) throws InternalServiceException {
        //增加session筛选计算条件
        Session sessionTemp = sessionRepository.save(obj);
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(sessionTemp.getSource())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(sessionTemp.getStartTime());
            //构造对应的redisKey
            String sessionKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "session");
            String sessionNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:session:num");
            redisService.addHashAndExist(sessionKey, sessionTemp.getId(), sessionTemp, 26L, TimeUnit.HOURS);
            redisService.incrementAndExist(sessionNumKey, 1L, 26L, TimeUnit.HOURS);
        }
        return sessionRepository.save(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Session update(Session obj, String operator, Object... values) throws InternalServiceException {
        Session sessionTemp = sessionRepository.save(obj);
        //增加session筛选计算条件
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(sessionTemp.getSource())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(sessionTemp.getStartTime());

            //构造对应的redisKey
            String sessionPoolKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_SESSION_NAME_STRING);
            String zrgNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:num");
            String zrgActiveNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:active:num");
            String zrgPassiveNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:passive:num");
            String zrgAtZeroTurnNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:at:zero:num");
            String zrgAtOneTurnNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:at:one:num");
            String zrgAtTwoTurnNumKey = CustomObjectUtil.buildKeyString(sessionTemp.getAppid(), monthAndToday, null, "ov:zrg:at:two:num");

            //维护session池 每一个session 对转人工的相关操作只计算一次 (因为大多bot只要转人工了，会话就结束了)
            if (!redisService.isMemberOfSet(sessionPoolKey, sessionTemp.getId())) {
                //维护转人工资源
                if (sessionTemp.getZrgType().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.ACTIVE.getElementName())
                        || sessionTemp.getZrgType().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.PASSIVE.getElementName())) {
                    redisService.addSetAndExist(sessionPoolKey, sessionTemp.getId(), 26L, TimeUnit.HOURS);
                    redisService.incrementAndExist(zrgNumKey, 1L, 26L, TimeUnit.HOURS);

                    if (sessionTemp.getZrgType().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.ACTIVE.getElementName())) {
                        redisService.incrementAndExist(zrgActiveNumKey, 1L, 26L, TimeUnit.HOURS);
                    }
                    if (sessionTemp.getZrgType().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.PASSIVE.getElementName())) {
                        redisService.incrementAndExist(zrgPassiveNumKey, 1L, 26L, TimeUnit.HOURS);
                    }
                    if (sessionTemp.getZrgAt() == 0) {
                        redisService.incrementAndExist(zrgAtZeroTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                    } else if (sessionTemp.getZrgAt() == 1) {
                        redisService.incrementAndExist(zrgAtOneTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                    }else if (sessionTemp.getZrgAt() == 2) {
                        redisService.incrementAndExist(zrgAtTwoTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                    }
                }
            }
        }
        return sessionTemp;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(Session obj, Object... values) throws InternalServiceException {
        sessionRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(String id) throws InternalServiceException {
        sessionRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<Session> findAll(Object... values) {
        return sessionRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return sessionRepository.count();
    }

    @Override
//    //@Cacheable(key = "#id", unless = "#result eq null")
    public Session findByIdAndAppid(String id,String appid) throws InternalServiceException {
        try {
			return sessionRepository.findByIdsAndAppid(id,appid);
		} catch (Exception e) {
			throw new InternalServiceException(e.getMessage());
		}
    }

    /**
     * 根据条件查找session对象
     *
     * @param id        对象标识
     * @param appid     分库标识
     * @param dateMonth 分表标识
     * @param startDate 分区区间开始
     * @param endDate   分区区间结束
     * @return 返回Session对象
     */
    @Override
    public Session findByIdAndAppidAndDateMonthAndStartTimeBetween(String id, String appid, String dateMonth, Date startDate, Date endDate) {
        return sessionRepository.findByIdAndAppidAndDateMonthAndStartTimeBetween(id, appid, dateMonth, startDate, endDate);
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public Session findById(String id) throws InternalServiceException {
        Session sessionTemp = sessionRepository.findById(id);
        if(sessionTemp != null){
            return sessionTemp;
        }else{
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public Session findByParam(String param, Object value) {
        return null;
    }

	/** (non-Javadoc)
	* @see com.zhuiyi.service.SessionService#searchSessions(com.zhuiyi.model.dto.SearchLogReqDto)
	*/
	@Override
	public List<Map<String,Object>> searchSessions(SearchLogReqDto search) {
		String selectSql = " select * from t_session c";
		StringBuilder whereSql = new StringBuilder().append(" where c.appid="+BizUtil.parseAppid(search.getBId()));
		
		if(StringUtils.isNoneEmpty(search.getStadate())) {
			whereSql.append(" and c.date_month >= "+DateUtil.string2Date2String(search.getStadate(), GlobConts.DATE_PARTERN_LONG,GlobConts.DATE_PARTERN_MONTH));
		} else {
			whereSql.append(" and c.date_month >= "+DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		}
		if(StringUtils.isNoneEmpty(search.getStadate())) {
			whereSql.append(" and c.date_sign >= "+DateUtil.string2Date2String(search.getStadate(), GlobConts.DATE_PARTERN_LONG,GlobConts.DATE_PARTERN_DATE_SIGN));
		}
		if(StringUtils.isNoneEmpty(search.getEnddate())) {
			whereSql.append(" and c.date_sign <= "+DateUtil.string2Date2String(search.getEnddate(), GlobConts.DATE_PARTERN_LONG,GlobConts.DATE_PARTERN_DATE_SIGN));
		}
		if(StringUtils.isNoneBlank(search.getUsereval())) {
			if("2".equals(search.getUsereval())) {
				whereSql.append(" and c.click_good_cnt <> '0' ");
			}
			if("3".equals(search.getUsereval())) {
				whereSql.append(" and c.click_bad_cnt <> '0' ");
			}
		}
		if(StringUtils.isNoneBlank(search.getAnswertype())) {
			if("c1".equals(search.getAnswertype())){
				whereSql.append(" and c.has_ans1='1'");
			}
			if("c3".equals(search.getAnswertype())){
				whereSql.append(" and c.has_ans3='3'");
			}
			if("o1".equals(search.getAnswertype())){
				whereSql.append(" and c.has_ans1='1' and c.has_ans3<>'3' ");
			}
			if("o3".equals(search.getAnswertype())){
				whereSql.append(" and c.has_ans1<>'1'  and c.has_ans3='3'");
			}
		}
		if(StringUtils.isNoneBlank(search.getHaszrg())) {
			if("1".equals(search.getHaszrg())) {
				whereSql.append(" and c.zrg_at >= 0 ");
			}
			if("2".equals(search.getHaszrg())) {
				whereSql.append(" and c.zrg_at < 0 ");
			}
		}
		if(StringUtils.isNoneBlank(search.getHashanxuan())) {
			if("1".equals(search.getHashanxuan())) {
				whereSql.append(" and c.has_hanxuan='1' ");
			}
			if("2".equals(search.getHashanxuan())) {
				whereSql.append(" and c.has_hanxuan<>'1' ");
			}
		}
		if(StringUtils.isNoneBlank(search.getHasreject())) {
			if("1".equals(search.getHasreject())) {
				whereSql.append(" and c.has_reject='1' ");
			}
			if("2".equals(search.getHasreject())) {
				whereSql.append(" and c.has_reject<>'1' ");
			}
		}
		if(StringUtils.isNoneBlank(search.getHassenword())) {
			if("1".equals(search.getHassenword())) {
				whereSql.append(" and c.sen_level='10' ");
			}
			if("2".equals(search.getHassenword())) {
				whereSql.append(" and c.sen_level<>'10' ");
			}
		}
		if(StringUtils.isNoneBlank(search.getAddress())) {
			whereSql.append(" and c.city='"+search.getAddress()+"'  ");
		}
		if(StringUtils.isNoneBlank(search.getUserid())) {
			whereSql.append(" and c.user_id='" +search.getUserid()+"'");
		}
		if(StringUtils.isNoneBlank(search.getQuery())) {
			String dateMongth = StringUtils.isNoneEmpty(search.getStadate())?
					DateUtil.string2Date2String(search.getStadate(), GlobConts.DATE_PARTERN_LONG,GlobConts.DATE_PARTERN_MONTH)
					:DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH);
			whereSql.append(" and exists ("
					+ " select 1 from t_dialog_detail_"+dateMongth+" a , t_dialog_"+dateMongth+ " b"
					+ " where a.appid= "+BizUtil.parseAppid(search.getBId()) 
					+ " and b.appid= "+BizUtil.parseAppid(search.getBId())
					+ " and a.id = b.id"
					+ " and b.session_id = c.id"
					)
			.append(" and a.query like '%" + search.getQuery()+"%'") 
			.append(" )");
		}
		if(StringUtils.isNoneBlank(search.getFaqid())) {
			if(StringUtils.isNoneBlank(search.getFaqtype())) {
				if(search.getFaqtype().equals("10")) {
					whereSql.append(" and c.biz_type='1' ");
				}
				if(search.getFaqtype().equals("0")) {
					whereSql.append(" and c.biz_type<>'1' ");
				}
			}
			if(StringUtils.isNoneBlank(search.getFaqin())) {
				if(search.getFaqin().equals("1")) {
					whereSql.append(" and c.direct_faq_id='"+search.getFaqid()+"' ");
				}
				if(search.getFaqin().equals("3")) {
					whereSql.append(" and c.direct_faq_id='"+search.getFaqid()+"' and c.indirect_faq_id like %"+search.getFaqid()+"% ");
				}
				if(search.getFaqin().equals("22")) {
					whereSql.append(" and (c.direct_faq_id='"+search.getFaqid()+"' or c.indirect_faq_id like %"+search.getFaqid()+"% ) and c.click_good>0 ");
				}
				if(search.getFaqin().equals("33")) {
					whereSql.append(" and (c.direct_faq_id='"+search.getFaqid()+"' or c.indirect_faq_id like %"+search.getFaqid()+"% ) and c.click_bad>0 ");
				}
			}
		}
		whereSql.append(" order by c.start_time desc limit "+(search.getPageNo()-1)*search.getPageSize()+","+ search.getPageSize());
		
		Query q = em.createNativeQuery(selectSql+whereSql.toString());
//		q.unwrap(SQLQuery.class)
//        .addScalar("total", LongType.INSTANCE)
//        .addScalar("amountOfSales", LongType.INSTANCE)
//        .addScalar("amountOfProducts", LongType.INSTANCE)
//        .setResultTransformer(Transformers.aliasToBean(DialogDto.class));
		q.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> authors = q.getResultList();
		
		log.trace("authors is ---->>> {} ",JSON.toJSONString(authors));
		return authors;
	}

	/** (non-Javadoc)
	* @see com.zhuiyi.service.SessionService#findByIdAndAppidAndDateMonth(java.lang.Long, java.lang.String, java.lang.String)
	*/
	@Override
	public Session findByIdAndAppidAndDateMonth(String id, String appid, String dateMonth) {
		return sessionRepository.findByIdAndAppidAndDateMonth(id, appid, dateMonth);
	}
}
