package com.zhuiyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.repository.DialogRepository;
import com.zhuiyi.service.DialogService;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@SuppressWarnings("JavadocReference")
@Service("dialogService")
//@CacheConfig(cacheNames = "dialog", keyGenerator = "commonKeyGenerator")
@Slf4j
public class DialogServiceImpl implements DialogService {

    @Autowired
    private DialogRepository dialogRepository;
    @Autowired
    private RedisService redisService;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Dialog save(Dialog obj, String operator, Object... values) throws InternalServiceException {
        Dialog dialogTemp = dialogRepository.save(obj);
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialogTemp.getSource()) && !GlobaSystemConstant.KEY_KF_NAME_N_STRING.equals(dialogTemp.getCid())
                && !GlobaSystemConstant.KEY_BUS_NAME_N_STRING.equals(dialogTemp.getBusinessName()) && !"999999999".equals(dialogTemp.getDirectFaqId())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(dialogTemp.getExactTime());

            String appPoolKey = CustomObjectUtil.buildKeyString(null, null, null, GlobaSystemConstant.KEY_APP_NAME_STRING);
            //构造对应的redisKey
            String dialogKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "dialog");
            String tenDialogKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, dialogTemp.getTenMin(), "dialog");
            String dialogNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:dialog:num");
            String costNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:cost:num");
            String userNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:user:num");
            String userPoolKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_USER_NAME_STRING);
            String chanlePoolKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_CHANEL_NAME_STRING);
            String directNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:direct:num");
            String indirectNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:indirect:num");
            String respondNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:respond:num");
            String provinceRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "province:rank");
            String cityRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "city:rank");
            String faqKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "faq");
            String faqRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "faq:rank");
            String cityFaqRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "city:faq:rank");
            String provinceFaqRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "province:faq:rank");

            //递增对话数量和总耗时
            if (dialogTemp.getRetcode() == 0) {
                redisService.addHashAndExist(dialogKey, dialogTemp.getId(), dialogTemp, 26L, TimeUnit.HOURS);
                redisService.addHashAndExist(tenDialogKey, dialogTemp.getId(), dialogTemp, 15L, TimeUnit.MINUTES);
                redisService.incrementAndExist(dialogNumKey, 1L, 26L, TimeUnit.HOURS);
                long cost = dialogTemp.getAdaptorCost().longValue();
                //设置cost超时
                Long num = redisService.increment(costNumKey, cost);
                if (num == cost) {
                    redisService.setExpire(costNumKey, 26L, TimeUnit.HOURS);
                }
            }

            //维护业务编码池
            if (!redisService.isMemberOfSet(appPoolKey, dialogTemp.getAppid())) {
                redisService.addSetAndExist(appPoolKey, dialogTemp.getAppid(), -1L, TimeUnit.HOURS);
            }

            //维护用户池
            if (!redisService.isMemberOfSet(userPoolKey, dialogTemp.getAccount())) {
                redisService.addSetAndExist(userPoolKey, dialogTemp.getAccount(), 26L, TimeUnit.HOURS);
                redisService.incrementAndExist(userNumKey, 1L, 26L, TimeUnit.HOURS);
            }
            //维护渠道池
            if (dialogTemp.getCid() != null && dialogTemp.getCid().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey, dialogTemp.getCid())) {
                    redisService.addSetAndExist(chanlePoolKey, dialogTemp.getCid(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialogTemp.getEid() != null && dialogTemp.getEid().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey, dialogTemp.getEid())) {
                    redisService.addSetAndExist(chanlePoolKey, dialogTemp.getEid(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialogTemp.getClient() != null && dialogTemp.getClient().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey, dialogTemp.getClient())) {
                    redisService.addSetAndExist(chanlePoolKey, dialogTemp.getClient(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialogTemp.getLables() != null && dialogTemp.getLables().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey, dialogTemp.getLables())) {
                    redisService.addSetAndExist(chanlePoolKey, dialogTemp.getLables(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialogTemp.getIm() != null && dialogTemp.getIm().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey, dialogTemp.getIm())) {
                    redisService.addSetAndExist(chanlePoolKey, dialogTemp.getIm(), 26L, TimeUnit.HOURS);
                }
            }

            //递增直接回答数量
            if (dialogTemp.getFaqNum() == 1 && dialogTemp.getRetcode() == 0) {
                redisService.incrementAndExist(directNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //递增间接回答数量
            if (dialogTemp.getFaqNum() == 3 && dialogTemp.getRetcode() == 0) {
                redisService.incrementAndExist(indirectNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //递增服务可用数量
            if (dialogTemp.getRetcode() == 0) {
                redisService.incrementAndExist(respondNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //维护FAQ 的城市和省份排行榜
            if (!GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialogTemp.getCity())) {
                redisService.addZSetAndExist(cityRankKey, dialogTemp.getCity(), 1D, 26L, TimeUnit.HOURS);
                redisService.addZSetAndExist(provinceRankKey, dialogTemp.getProvince(), 1D, 26L, TimeUnit.HOURS);
                //维护FAQ资源池
                if (obj.getDirectFaqId() != null && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(obj.getDirectFaqId())) {
                    //排除寒暄FAQ
                    if (GlobaSystemConstant.FAQ_TYPE.CHAT.getElementName() != dialogTemp.getBizType()) {
                        redisService.addHashAndExist(faqKey, obj.getDirectFaqId() + ":" + dialogTemp.getBizType(), obj.getDirectFaq(), 26L, TimeUnit.HOURS);
                    }
                    //维护FAQ排行榜
                    redisService.addZSetAndExist(faqRankKey, obj.getDirectFaqId() + ":" + dialogTemp.getBizType(), 1D, 26L, TimeUnit.HOURS);
                    if (GlobaSystemConstant.FAQ_TYPE.FAQ.getElementName() == dialogTemp.getBizType()) {
                        //维护FAQ各城市排行榜
                        redisService.addZSetAndExist(cityFaqRankKey + ":" + dialogTemp.getCity(), obj.getDirectFaqId(), 1D, 26L, TimeUnit.HOURS);
                        //维护FAQ各省份排行榜
                        redisService.addZSetAndExist(provinceFaqRankKey + ":" + dialogTemp.getProvince(), obj.getDirectFaqId(), 1D, 26L, TimeUnit.HOURS);
                    }
                }
            }

            //维护FAQ 的城市和省份排行榜
            if (!GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialogTemp.getCity())) {
                redisService.addZSetAndExist(cityRankKey, dialogTemp.getCity(), 1D, 26L, TimeUnit.HOURS);
                redisService.addZSetAndExist(provinceRankKey, dialogTemp.getProvince(), 1D, 26L, TimeUnit.HOURS);
            }
        }
        return dialogTemp;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Dialog update(Dialog obj, String operator, Object... values) throws InternalServiceException {
        Dialog dialogTemp = dialogRepository.save(obj);
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialogTemp.getSource()) && 0 == dialogTemp.getRetcode()
                && !GlobaSystemConstant.KEY_BUS_NAME_N_STRING.equals(dialogTemp.getBusinessName()) && !"999999999".equals(dialogTemp.getDirectFaqId())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(dialogTemp.getExactTime());
            String likeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:like:num");
            String dislikeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:dislike:num");
            String clickNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:click:num");
            String directLikeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:direct:like:num");
            String directDislikeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:direct:dislike:num");
            String indirectLikeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:indirect:like:num");
            String indirectDislikeNumKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "ov:indirect:dislike:num");
            String likeRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "faq:like:rank");
            String dislikeRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "faq:dislike:rank");
            String zrgRankKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, "faq:zrg:rank");
            //维护点赞的统计数据
            if (dialogTemp.getIsClickGood() != 0) {
                redisService.incrementAndExist(likeNumKey, 1L, 26L, TimeUnit.HOURS);
                if (GlobaSystemConstant.ANSWER_TYPE.DIRECT.getElementName().equals(dialogTemp.getAnswerType())) {
                    redisService.incrementAndExist(directLikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialogTemp.getAnswerType())) {
                    redisService.incrementAndExist(indirectLikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                //维护FAQ 点赞排行榜
                if (GlobaSystemConstant.FAQ_TYPE.CHAT.getElementName() != dialogTemp.getBizType() && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(obj.getDirectFaqId())
                        && !"999999999".equals(obj.getDirectFaqId())) {
                    redisService.addZSetAndExist(likeRankKey, obj.getDirectFaqId() + ":" + dialogTemp.getBizType(), 1D, 26L, TimeUnit.HOURS);
                }
            }
            //维护点踩的统计数据
            if (dialogTemp.getIsClickBad() != 0) {
                redisService.incrementAndExist(dislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                if (GlobaSystemConstant.ANSWER_TYPE.DIRECT.getElementName().equals(dialogTemp.getAnswerType())) {
                    redisService.incrementAndExist(directDislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialogTemp.getAnswerType())) {
                    redisService.incrementAndExist(indirectDislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                //维护FAQ 点踩排行榜
                if (GlobaSystemConstant.FAQ_TYPE.CHAT.getElementName() != dialogTemp.getBizType() && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(obj.getDirectFaqId())
                        && !"999999999".equals(obj.getDirectFaqId())) {
                    redisService.addZSetAndExist(dislikeRankKey, obj.getDirectFaqId() + ":" + dialogTemp.getBizType(), 1D, 26L, TimeUnit.HOURS);
                }
            }
            //维护推荐点击的统计数据
            if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialogTemp.getAnswerType())) {
                redisService.incrementAndExist(clickNumKey, 1L, 26L, TimeUnit.HOURS);
            }
            //维护FAQ 转人工排行榜
            if (!dialogTemp.getIsZrg().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.NO.getElementName())
                    && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(obj.getDirectFaq()) && !"999999999".equals(obj.getDirectFaqId())) {
                redisService.addZSetAndExist(zrgRankKey, obj.getDirectFaqId() + ":" + dialogTemp.getBizType(), 1D, 52L, TimeUnit.HOURS);
            }
        }
        return dialogTemp;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#obj.id")
    public void delete(Dialog obj, Object... values) throws InternalServiceException {
        dialogRepository.delete(obj);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(key = "#id")
    public void deleteById(String id) throws InternalServiceException {
        dialogRepository.deleteById(id);
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public List<Dialog> findAll(Object... values) {
        return dialogRepository.findAll();
    }

    @Override
    public Long count(Object... values) {
        return dialogRepository.count();
    }

    @Override
    //@Cacheable(key = "#id", unless = "#result eq null")
    public Dialog findById(String id) throws InternalServiceException {
        Dialog dialogTemp = dialogRepository.findById(id);
        if (dialogTemp != null) {
            return dialogTemp;
        } else {
            throw new InternalServiceException("查找的对象不存在");
        }
    }

    @Override
    //@Cacheable(unless = "#result eq null")
    public Dialog findByParam(String param, Object value) {
        return null;
    }

    /**
     * FUN:条件查询时动态组装条件
     */
    private Specification<Dialog> searchLogsWhere(
            Long bid,
            Long pid,
            int pageno,
            int pagesize,
            Date stadate,
            Date enddate,
            String faqid,
            String faqin,
            String faqtype,
            String usereval,
            String answertype,
            String haszrg,
            String hashanxuan,
            String hassenword,
            String hasreject,
            String address,
            String query,
            String userid
    ){
        return new Specification<Dialog>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Dialog> root, CriteriaQuery<?> select, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //业务ID过滤
                if(bid!=null&&pid!=null){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                //时间区间过滤
                if(stadate != null && enddate != null){
                    predicates.add(cb.between((root.<Date>get("exact_time")), stadate, enddate));
                }
                if(StringUtils.isNoneEmpty(faqid)){
                    if(StringUtils.isNoneEmpty(faqtype)) {
                        if("10".equals(faqtype)) {
                            predicates.add(cb.equal(root.<String>get("biz_type"),"1"));
                        }
                        if("0".equals(faqtype)) {
                            predicates.add(cb.notEqual(root.<String>get("faqid"),"1"));
                        }
                    }

                    if(StringUtils.isNoneEmpty(faqin)) {
                        if("1".equals(faqin)) {
                            predicates.add(cb.equal(root.<String>get("direct_faq_id"), faqin));
                        }
                        if("3".equals(faqin)) {
                            predicates.add(cb.and(cb.equal(root.<String>get("direct_faq_id"), "-")));
                            predicates.add(cb.and(cb.like(root.<String>get("indirect_faq_id"), faqid)));
                        }
                        if("22".equals(faqin)) {
                            List<Predicate> orPredicates = Lists.newArrayList();
                            orPredicates.add(cb.equal(root.<String>get("direct_faq_id"), faqid));
                            orPredicates.add(cb.or(cb.like(root.<String>get("indirect_faq_id"), faqid)));
                            predicates.add(cb.and(orPredicates.toArray(new Predicate[orPredicates.size()])));
                            predicates.add(cb.and(cb.gt(root.<Integer>get("click_good"), 0)));
                        }
                        if("33".equals(faqin)) {
                            List<Predicate> orPredicates = Lists.newArrayList();
                            orPredicates.add(cb.equal(root.<String>get("direct_faq_id"), faqid));
                            orPredicates.add(cb.or(cb.like(root.<String>get("indirect_faq_id"), faqid)));
                            predicates.add(cb.and(orPredicates.toArray(new Predicate[orPredicates.size()])));
                            predicates.add(cb.and(cb.gt(root.<Integer>get("click_bad"), 0)));
                        }

                    } else {
                        List<Predicate> orPredicates = Lists.newArrayList();
                        orPredicates.add(cb.equal(root.<String>get("direct_faq_id"), faqid));
                        orPredicates.add(cb.or(cb.like(root.<String>get("indirect_faq_id"), faqid)));

                        predicates.add(cb.and(orPredicates.toArray(new Predicate[orPredicates.size()])));
                    }

                }
                if(StringUtils.isNoneEmpty(faqtype)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(usereval)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(answertype)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(haszrg)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(hashanxuan)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(hassenword)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(hasreject)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(address)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(query)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }
                if(StringUtils.isNoneEmpty(userid)){
                    predicates.add(cb.equal(root.<String>get("appid"),bid+""+pid));
                }

                return select.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }
	/** (non-Javadoc)
	* @see com.zhuiyi.service.DialogService#getDialogLogs()
	*/
	@Override
	public Dialog getDialogLogs(
    		Long bid,
    		Long pid,
    		int pageno,
    		int pagesize,
    		Date stadate,
    		Date enddate,
    		String faqid,
    		String faqin,
    		String faqtype,
    		String usereval,
    		String answertype,
    		String haszrg,
    		String hashanxuan,
    		String hassenword,
    		String hasreject,
    		String address,
    		String query,
    		String userid) {


//		wherecase = " and source_from='-' and cid<>'kf' and top1<>'999999999' and business_name<>'guess' "
//				sessfilter = " source='-' and cid<>'kf' and business_name<>'guess' "
//		curr_time,user_ip,city,account,source_from,channel,other,sen_level,zrg_at
		StringBuilder selectSql = new StringBuilder().append("select"
				+ " a.id as session_id,"
				+ ""
				+ ""
				+ ""
				+ ""
				+ "b.start_time, b.zrg_at "
				+ "from t_dialog a, t_dialog_detail d, t_session b");
		StringBuilder whereSql = new StringBuilder()
				.append(" where a.session_id=b.id ")
				.append(" and d.id = a.id")
				.append(" and a.appid = "+bid+""+pid)
				.append(" and a.date_month = '201808'")
				.append(" and d.appid = "+bid+""+pid)
				.append(" and d.date_month = '201808'")
				.append(" and b.appid = "+bid+""+pid )
				.append(" and b.date_month = '201808'")
				.append(" and b.source='-' and b.cid <> 'kf' and b.business_name <> 'guess' ")
				.append(" and exists ("
						+ " select 1 from t_session_201808 c "
						+ " where c.appid="+bid+""+pid
						+ " and b.id = c.id");

		if(StringUtils.isNoneBlank(usereval)) {
			if("2".equals(usereval)) {
				whereSql.append(" and c.click_good_cnt <> '0' ");
			}
			if("3".equals(usereval)) {
				whereSql.append(" and c.click_bad_cnt <> '0' ");
			}
		}
		if(StringUtils.isNoneBlank(answertype)) {
			if("c1".equals(answertype)){
				whereSql.append(" and c.has_ans1='1'");
			}
			if("c3".equals(answertype)){
				whereSql.append(" and c.has_ans3='3'");
			}
			if("o1".equals(answertype)){
				whereSql.append(" and c.has_ans1='1' and c.has_ans3<>'3' ");
			}
			if("o3".equals(answertype)){
				whereSql.append(" and c.has_ans1<>'1'  and c.has_ans3='3'");
			}
		}

		if(StringUtils.isNoneBlank(haszrg)) {
			if("1".equals(haszrg)) {
				whereSql.append(" and c.zrg_at >= 0 ");
			}
			if("2".equals(haszrg)) {
				whereSql.append(" and c.zrg_at < 0 ");
			}
		}
		if(StringUtils.isNoneBlank(hashanxuan)) {
			if("1".equals(hashanxuan)) {
				whereSql.append(" and c.has_hanxuan='1' ");
			}
			if("2".equals(hashanxuan)) {
				whereSql.append(" and c.has_hanxuan<>'1' ");
			}
		}
		if(StringUtils.isNoneBlank(hasreject)) {
			if("1".equals(hasreject)) {
				whereSql.append(" and c.has_reject='1' ");
			}
			if("2".equals(hasreject)) {
				whereSql.append(" and c.has_reject<>'1' ");
			}
		}
		if(StringUtils.isNoneBlank(hassenword)) {
			if("1".equals(hassenword)) {
				whereSql.append(" and c.sen_level='10' ");
			}
			if("2".equals(hassenword)) {
				whereSql.append(" and c.sen_level<>'10' ");
			}
		}
		if(StringUtils.isNoneBlank(address)) {
			whereSql.append(" and c.city='"+address+"'  ");
		}
		if(StringUtils.isNoneBlank(userid)) {
			whereSql.append(" and c.user_id='" +userid+"'");
		}
		if(StringUtils.isNoneBlank(query)) {
			whereSql.append(" and c.query like '%" +userid+"%'");
		}
		if(StringUtils.isNoneBlank(faqid)) {
			if(StringUtils.isNoneBlank(faqtype)) {
				if(faqtype.equals("10")) {
					whereSql.append(" and c.biz_type='1' ");
				}
				if(faqtype.equals("0")) {
					whereSql.append(" and c.biz_type<>'1' ");
				}
			}
			if(StringUtils.isNoneBlank(faqin)) {
				if(faqin.equals("1")) {
					whereSql.append(" and c.direct_faq_id='"+faqid+"' ");
				}
				if(faqin.equals("3")) {
					whereSql.append(" and c.direct_faq_id='"+faqid+"' and c.indirect_faq_id like %"+faqid+"% ");
				}
				if(faqin.equals("22")) {
					whereSql.append(" and (c.direct_faq_id='"+faqid+"' or c.indirect_faq_id like %"+faqid+"% ) and c.click_good>0 ");
				}
				if(faqin.equals("33")) {
					whereSql.append(" and (c.direct_faq_id='"+faqid+"' or c.indirect_faq_id like %"+faqid+"% ) and c.click_bad>0 ");
				}
			}
		}
		whereSql.append(" order by c.start_time desc limit "+pageno*pagesize+","+ pagesize);
		whereSql.append(" )");
		String dialogSql = selectSql.append(whereSql.toString()).toString();

		Query q = em.createNativeQuery(dialogSql);
//		q.unwrap(SQLQuery.class)
//        .addScalar("total", LongType.INSTANCE)
//        .addScalar("amountOfSales", LongType.INSTANCE)
//        .addScalar("amountOfProducts", LongType.INSTANCE)
//        .setResultTransformer(Transformers.aliasToBean(DialogDto.class));
		q.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> authors = q.getResultList();

//		String sessionCountSql = "select count(1) from t_session where appid = " +bid+""+pid
//				+ " and date_mongth = '' "
//				+ " and ";
//		dialogRepository.count(spec);

        log.info("getResultList is {}",JSON.toJSONString(authors));


//        List<SessionRespDto> session = new ArrayList<>();
//
//        String tempSession = "";
//        for(Map<String,Object> i: authors) {
//        	SessionRespDto sessionRespDto = null;
//        	List<DialoRespDto> dialoRespDtos = null;
//        	tempSession = (String)i.get("session_id");
//        	if(tempSession.equals("")) {
//        		sessionRespDto.setDialogs(dialoRespDtos);
//        		session.add(sessionRespDto);
//
//        	} else {
//        		sessionRespDto = new SessionRespDto();
//        		dialoRespDtos = new ArrayList<>();
//        	}
//        	sessionRespDto.setId("");
//        	sessionRespDto.setFirstActiveTime((String)i.get(""));
//
//
//        }



        return null;
    }

	/** (non-Javadoc)
	* @see com.zhuiyi.service.DialogService#getDialogLogs(java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public Dialog getDialogLogs(String appid, String dateMongth, String id) {
		return dialogRepository.findByAppidAndDateMonthAndId(appid, dateMongth, id);
	}

	/** (non-Javadoc)
	* @see com.zhuiyi.service.DialogService#getDialogLogs(java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public List<Dialog> getDialogLogs(String appid, String dateMongth, String sessionId,PageRequest pageRequest) {
		return dialogRepository.findByAppidAndDateMonthAndSessionId(appid, dateMongth, sessionId,pageRequest);
	}
}
