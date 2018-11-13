package com.zhuiyi.aspect;

import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.Session;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/31 20:41
 * description: 统计埋点逻辑类
 * own:
 */
@Aspect
@Component
@Slf4j
public class CountHandleAspect {

    @Autowired
    private RedisService redisService;

    @After("execution(* com.zhuiyi.service.impl.DialogServiceImpl.update*(..))")
    public void doAfterUpdateDialog(JoinPoint joinPoint){
        log.debug("[SYS][SERVICE][Content: doAfterUpdateDialog][start...]");
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        Dialog dialog = (Dialog) obj[0];
        judgeCountHandle(dialog);
    }

    @After("execution(* com.zhuiyi.service.impl.DialogServiceImpl.save*(..))")
    public void doAfterSaveDialog(JoinPoint joinPoint){
        log.debug("[SYS][SERVICE][Content: doAfterSaveDialog][start...]");
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        Dialog dialog = (Dialog) obj[0];
        dialogCountHandle(dialog);
    }

    @After("execution(* com.zhuiyi.service.impl.SessionServiceImpl.update*(..))")
    public void doAfterUpdateSession(JoinPoint joinPoint){
        log.debug("[SYS][SERVICE][Content: doAfterUpdateSession][start...]");
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        Session session = (Session) obj[0];
        zrgCountHandle(session);
    }

    @After("execution(* com.zhuiyi.service.impl.SessionServiceImpl.save*(..))")
    public void doAfterSaveSession(JoinPoint joinPoint){
        log.debug("[SYS][SERVICE][Content: doAfterSaveSession][start...]");
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        Session session = (Session) obj[0];
        sessionCountHandle(session);
        zrgCountHandle(session);
    }

    /**
     * 机器对话埋点计算
     *
     * @param dialog 埋点对象
     */
    public void dialogCountHandle(Dialog dialog) {
        //增加dialog筛选计算条件
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getSource()) && !GlobaSystemConstant.KEY_KF_NAME_N_STRING.equals(dialog.getCid())
                && !GlobaSystemConstant.KEY_BUS_NAME_N_STRING.equals(dialog.getBusinessName()) && !"999999999".equals(dialog.getDirectFaqId())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(dialog.getExactTime());

            String appPoolKey = CustomObjectUtil.buildKeyString(null, null, null, GlobaSystemConstant.KEY_APP_NAME_STRING);
            //构造对应的redisKey
            String dialogKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "dialog");
            String tenDialogKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, dialog.getTenMin(), "dialog");
            String dialogNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:dialog:num");
            String clickNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:click:num");
            String costNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:cost:num");
            String userNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:user:num");
            String userPoolKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_USER_NAME_STRING);
            String chanlePoolKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_CHANEL_NAME_STRING);
            String directNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:direct:num");
            String indirectNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:indirect:num");
            String respondNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:respond:num");
            String provinceRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "province:rank");
            String cityRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "city:rank");
            String faqKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "faq");
            String faqRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "faq:rank");
            String cityFaqRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "city:faq:rank");
            String provinceFaqRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "province:faq:rank");

            //递增对话数量和总耗时
            if (dialog.getRetcode() == 0) {
                redisService.addHashAndExist(dialogKey, dialog.getId(), dialog, 26L, TimeUnit.HOURS);
                redisService.addHashAndExist(tenDialogKey, dialog.getId(), dialog, 15L, TimeUnit.MINUTES);
                redisService.incrementAndExist(dialogNumKey, 1L, 26L, TimeUnit.HOURS);
                long cost = dialog.getAdaptorCost().longValue();
                //设置cost超时
                Long num = redisService.increment(costNumKey, cost);
                if (num == cost) {
                    redisService.setExpire(costNumKey, 26L, TimeUnit.HOURS);
                }
            }

            //维护业务编码池
            if (!redisService.isMemberOfSet(appPoolKey, dialog.getAppid())) {
                redisService.addSetAndExist(appPoolKey, dialog.getAppid(), -1L, TimeUnit.HOURS);
            }

            //维护用户池
            if (dialog.getAccount() != null && dialog.getAccount().length() > 0 && !redisService.isMemberOfSet(userPoolKey, dialog.getAccount())) {
                redisService.addSetAndExist(userPoolKey, dialog.getAccount(), 26L, TimeUnit.HOURS);
                redisService.incrementAndExist(userNumKey, 1L, 26L, TimeUnit.HOURS);
            }
            //维护渠道池
            if (dialog.getCid() != null && dialog.getCid().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey + ":cid", dialog.getCid())) {
                    redisService.addSetAndExist(chanlePoolKey + ":cid", dialog.getCid(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialog.getEid() != null && dialog.getEid().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey + ":eid" , dialog.getEid())) {
                    redisService.addSetAndExist(chanlePoolKey + ":eid", dialog.getEid(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialog.getClient() != null && dialog.getClient().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey + ":client", dialog.getClient())) {
                    redisService.addSetAndExist(chanlePoolKey + ":client", dialog.getClient(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialog.getLables() != null && dialog.getLables().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey + ":lables", dialog.getLables())) {
                    redisService.addSetAndExist(chanlePoolKey + ":lables", dialog.getLables(), 26L, TimeUnit.HOURS);
                }
            }
            if (dialog.getIm() != null && dialog.getIm().length() > 0) {
                if (!redisService.isMemberOfSet(chanlePoolKey + ":im", dialog.getIm())) {
                    redisService.addSetAndExist(chanlePoolKey + ":im", dialog.getIm(), 26L, TimeUnit.HOURS);
                }
            }

            //递增直接回答数量
            if (dialog.getFaqNum() == 1 && dialog.getRetcode() == 0) {
                redisService.incrementAndExist(directNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //递增间接回答数量
            if (dialog.getFaqNum() == 3 && dialog.getRetcode() == 0) {
                redisService.incrementAndExist(indirectNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //递增服务可用数量
            if (dialog.getRetcode() == 0) {
                redisService.incrementAndExist(respondNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //维护推荐点击的统计数据
            if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialog.getAnswerType())) {
                redisService.incrementAndExist(clickNumKey, 1L, 26L, TimeUnit.HOURS);
            }

            //维护FAQ资源池
            if (dialog.getDirectFaqId() != null && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getDirectFaqId())) {
                redisService.addHashAndExist(faqKey, dialog.getDirectFaqId() + ":" + dialog.getBizType(), dialog.getDirectFaq(), 26L, TimeUnit.HOURS);
            }

            //维护FAQ 的城市和省份排行榜
            if (!GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getCity())) {
                redisService.addZSetAndExist(cityRankKey, dialog.getCity(), 1D, 26L, TimeUnit.HOURS);
                redisService.addZSetAndExist(provinceRankKey, dialog.getProvince(), 1D, 26L, TimeUnit.HOURS);
                //维护FAQ资源池
                if (dialog.getDirectFaqId() != null && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getDirectFaqId())) {
                    //维护FAQ排行榜
                    redisService.addZSetAndExist(faqRankKey, dialog.getDirectFaqId() + ":" + dialog.getBizType(), 1D, 26L, TimeUnit.HOURS);
                    if (GlobaSystemConstant.FAQ_TYPE.FAQ.getElementName() == dialog.getBizType()) {
                        //维护FAQ各城市排行榜
                        redisService.addZSetAndExist(cityFaqRankKey + ":" + dialog.getCity(), dialog.getDirectFaqId(), 1D, 26L, TimeUnit.HOURS);
                        //维护FAQ各省份排行榜
                        redisService.addZSetAndExist(provinceFaqRankKey + ":" + dialog.getProvince(), dialog.getDirectFaqId(), 1D, 26L, TimeUnit.HOURS);
                    }
                }
            }
        }
    }

    /**
     * 点赞点踩埋点计算
     *
     * @param dialog 埋点对象
     * @return void
     */
    public void judgeCountHandle(Dialog dialog) {
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getSource()) && 0 == dialog.getRetcode()
                && !GlobaSystemConstant.KEY_BUS_NAME_N_STRING.equals(dialog.getBusinessName()) && !"999999999".equals(dialog.getDirectFaqId())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(dialog.getExactTime());
            String likeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:like:num");
            String dislikeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:dislike:num");
            String directLikeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:direct:like:num");
            String directDislikeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:direct:dislike:num");
            String indirectLikeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:indirect:like:num");
            String indirectDislikeNumKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "ov:indirect:dislike:num");
            String likeRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "faq:like:rank");
            String dislikeRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "faq:dislike:rank");
            String zrgRankKey = CustomObjectUtil.buildKeyString(dialog.getAppid(), monthAndToday, null, "faq:zrg:rank");
            //维护点赞的统计数据
            if (dialog.getIsClickGood() != 0) {
                redisService.incrementAndExist(likeNumKey, 1L, 26L, TimeUnit.HOURS);
                if (GlobaSystemConstant.ANSWER_TYPE.DIRECT.getElementName().equals(dialog.getAnswerType())) {
                    redisService.incrementAndExist(directLikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialog.getAnswerType())) {
                    redisService.incrementAndExist(indirectLikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                //维护FAQ 点赞排行榜
                if (GlobaSystemConstant.FAQ_TYPE.CHAT.getElementName() != dialog.getBizType() && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getDirectFaqId())
                        && !"999999999".equals(dialog.getDirectFaqId())) {
                    redisService.addZSetAndExist(likeRankKey, dialog.getDirectFaqId() + ":" + dialog.getBizType(), 1D, 26L, TimeUnit.HOURS);
                }
            }
            //维护点踩的统计数据
            if (dialog.getIsClickBad() != 0) {
                redisService.incrementAndExist(dislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                if (GlobaSystemConstant.ANSWER_TYPE.DIRECT.getElementName().equals(dialog.getAnswerType())) {
                    redisService.incrementAndExist(directDislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                if (GlobaSystemConstant.ANSWER_TYPE.CLICK.getElementName().equals(dialog.getAnswerType())) {
                    redisService.incrementAndExist(indirectDislikeNumKey, 1L, 26L, TimeUnit.HOURS);
                }
                //维护FAQ 点踩排行榜
                if (GlobaSystemConstant.FAQ_TYPE.CHAT.getElementName() != dialog.getBizType() && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getDirectFaqId())
                        && !"999999999".equals(dialog.getDirectFaqId())) {
                    redisService.addZSetAndExist(dislikeRankKey, dialog.getDirectFaqId() + ":" + dialog.getBizType(), 1D, 26L, TimeUnit.HOURS);
                }
            }
            //维护FAQ 转人工排行榜
            if (!dialog.getIsZrg().startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.NO.getElementName())
                    && !GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(dialog.getDirectFaq()) && !"999999999".equals(dialog.getDirectFaqId())) {
                redisService.addZSetAndExist(zrgRankKey, dialog.getDirectFaqId() + ":" + dialog.getBizType(), 1D, 52L, TimeUnit.HOURS);
            }
        }
    }

    /**
     * 会话数埋点计算
     *
     * @param session 埋点对象
     * @return void
     */
    public void sessionCountHandle(Session session) {
        //增加session筛选计算条件
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(session.getSource())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(session.getStartTime());
            //构造对应的redisKey
            String sessionKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "session");
            String sessionNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:session:num");
            redisService.addHashAndExist(sessionKey, session.getId(), session, 26L, TimeUnit.HOURS);
            redisService.incrementAndExist(sessionNumKey, 1L, 26L, TimeUnit.HOURS);
        }
    }

    /**
     * 转人工埋点计算
     *
     * @param session 埋点对象
     * @return void
     */
    public void zrgCountHandle(Session session) {
        //增加session筛选计算条件
        if (GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE.equals(session.getSource())) {
            String monthAndToday = CustomTimeUtil.getMonthAndDay(session.getStartTime());
            String zrgType = "0_off";
            if (null != session.getZrgType()) {
                zrgType = session.getZrgType();
            }
            int zrgAt = -1;
            if (null != session.getZrgAt()) {
                zrgAt = session.getZrgAt();
            }

            //构造对应的redisKey
            String sessionPoolKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_SESSION_NAME_STRING);
            String zrgNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:num");
            String zrgActiveNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:active:num");
            String zrgPassiveNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:passive:num");
            String zrgAtZeroTurnNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:at:zero:num");
            String zrgAtOneTurnNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:at:one:num");
            String zrgAtTwoTurnNumKey = CustomObjectUtil.buildKeyString(session.getAppid(), monthAndToday, null, "ov:zrg:at:two:num");

            //判断是否在session池中 每一个session 对转人工的相关操作只计算一次 (因为大多bot只要转人工了，会话就结束了)
            if (!redisService.isMemberOfSet(sessionPoolKey, session.getId())) {
                //维护转人工资源
                if (zrgType.startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.ACTIVE.getElementName())
                        || zrgType.startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.PASSIVE.getElementName())) {
                    //session池埋点计算
                    redisService.addSetAndExist(sessionPoolKey, session.getId(), 26L, TimeUnit.HOURS);
                    redisService.incrementAndExist(zrgNumKey, 1L, 26L, TimeUnit.HOURS);
                    //主动转人工埋点计算
                    if (zrgType.startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.ACTIVE.getElementName())) {
                        redisService.incrementAndExist(zrgActiveNumKey, 1L, 26L, TimeUnit.HOURS);
                    }
                    //被动转人工埋点计算
                    if (zrgType.startsWith(GlobaSystemConstant.ZRG_TYPE_FLAG.PASSIVE.getElementName())) {
                        redisService.incrementAndExist(zrgPassiveNumKey, 1L, 26L, TimeUnit.HOURS);
                    }
                    //转人工埋点计算
                    switch (zrgAt) {
                        case 0:
                            //第0轮转人工埋点计算
                            redisService.incrementAndExist(zrgAtZeroTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                            break;
                        case 1:
                            //第1轮转人工埋点计算
                            redisService.incrementAndExist(zrgAtOneTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                            break;
                        case 2:
                            //第2轮转人工埋点计算
                            redisService.incrementAndExist(zrgAtTwoTurnNumKey, 1L, 26L, TimeUnit.HOURS);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
