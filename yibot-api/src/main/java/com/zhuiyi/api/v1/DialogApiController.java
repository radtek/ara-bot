package com.zhuiyi.api.v1;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.BizUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.convertor.DailogConvertor;
import com.zhuiyi.convertor.DialogAnswerConvertor;
import com.zhuiyi.convertor.SessionConvertor;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogAnswer;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.model.IpAreaMatch;
import com.zhuiyi.model.Session;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.DialogAnswerService;
import com.zhuiyi.service.DialogDetailService;
import com.zhuiyi.service.DialogService;
import com.zhuiyi.service.IpAreaMatchService;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/adaptor")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class DialogApiController {
	
	private  static String REFIX_SESSION_COUNT = "yibot:count:" ;
	
	@Autowired
	private RedisService redisService ;
	
	@Autowired
	private DialogDetailService dialogDetailService;

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private IpAreaMatchService ipAreaMatchService;

	@Autowired
	private DialogAnswerService answerService;

    private DialogService dialogService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public DialogApiController(DialogService dialogService, ResCodeProperties resCodeProperties){
        this.dialogService = dialogService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
    * FUN:此方法描述的是：
    * @author: klauszhou@wezhuiyi.com
    * @version: 2018年8月6日 下午5:42:36
    */
    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
    @ApiImplicitParam(name = "params", value = "对象实体数据",required = true, dataType = "String")
    @PostMapping
    public ResponseVO receiveAdaptorLog(HttpServletRequest req) {
        ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
            try {
            	Map<String,String> filedMap = new HashMap<>(64);
                String input = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                if(!StringUtils.isEmpty(input)) {
                	input = URLDecoder.decode(input, "UTF-8");
                	log.info("[param is] {}",input);
                    String[] fileds = input.split(" \\| ");
                    filedMap.put("receiveTime", fileds[0].substring(1, fileds[0].length()-1));
                    for(int i = 1;i< fileds.length ; i++) {
                    	String data = fileds[i];
                        if(StringUtils.isNotEmpty(data)) {
                        	filedMap.put(data.substring(0, data.indexOf(":")), data.substring(data.indexOf(":")+1, data.length()));
                        }
                    }
                }
                Dialog dialog = new Dialog();
                DialogDetail dialogDetail = new DialogDetail();
                DailogConvertor.initDailog(dialog, filedMap);
                if(StringUtils.isNoneBlank(dialog.getClientip())) {
                	log.info("IP area is {} ", dialog.getClientip());
                	IpAreaMatch area = ipAreaMatchService.findById(dialog.getClientip());
                	if(area != null) {
                		log.info("area is {} ",JSON.toJSONString(area));
                		dialog.setCity(area.getCity());
                		dialog.setProvince(area.getProvince());
                		dialog.setCountry(area.getCountry());
                	}
                }
                Session session = null;
                Session tempSession = new Session();
                Date startDate = CustomTimeUtil.getDayStartTimeByDate(dialog.getExactTime(), 0L);
                Date endDate = CustomTimeUtil.getDayStartTimeByDate(dialog.getExactTime(), 1L);
                session = sessionService.findByIdAndAppidAndDateMonthAndStartTimeBetween(dialog.getSessionId(), dialog.getAppid(),dialog.getDateMonth(),startDate,endDate);
                SessionConvertor.initSession(tempSession, filedMap);
                if( session == null) {
                	dialog.setId(dialog.getSessionId()+"_1");
                	tempSession.setCity(dialog.getCity());
                	tempSession.setProvince(dialog.getProvince());
                	tempSession.setCountry(dialog.getCountry());
                	sessionService.save(tempSession, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                	redisService.save(REFIX_SESSION_COUNT+dialog.getSessionId(), 1, 12L, TimeUnit.HOURS);
                } else {
                	redisService.increment(REFIX_SESSION_COUNT+dialog.getSessionId(), 1L);
                	session.setRoundNum(session.getRoundNum()+(Integer)redisService.find(REFIX_SESSION_COUNT+dialog.getSessionId()));
                	session.setStartTime(
                			session.getStartTime().after(DateUtil.convertLDTToDate(LocalDateTime.now()))?
                					DateUtil.convertLDTToDate(LocalDateTime.now()):session.getStartTime()
                			);
                	session.setEndTime(
                			session.getEndTime().before(DateUtil.convertLDTToDate(LocalDateTime.now()))?
                					DateUtil.convertLDTToDate(LocalDateTime.now()):session.getEndTime()
                			);
                	session.setSenLevel(tempSession.getSenLevel()>session.getSenLevel()? session.getSenLevel(): tempSession.getSenLevel());
                	if(tempSession.getHasHanxuan() != null && tempSession.getHasHanxuan() == 1) {
                		session.setHasHanxuan(1);
                	}
                	if(tempSession.getHasReject() != null && tempSession.getHasReject() == 1) {
                		session.setHasReject(1);
                	}
                	if(tempSession.getHasAns1() != null && tempSession.getHasAns1() == 1) {
                		session.setHasAns1(1);
                	}
                	if(tempSession.getHasAns3() != null && tempSession.getHasAns3() == 3) {
                		session.setHasAns3(3);
                	}
                	session.setGmtModified(DateUtil.convertLDTToDate(LocalDateTime.now()));
                	if(tempSession.getZrgType()!=null&&session.getZrgType()!=null && tempSession.getZrgType().startsWith("0") && !session.getZrgType().startsWith("0")) {
                		session.setZrgType(tempSession.getZrgType());
                	}
                	sessionService.update(session, GlobaSystemConstant.CUSTOM_SYSTEM_USER);

                	dialog.setId(dialog.getSessionId()+"_"+(Integer)redisService.find(REFIX_SESSION_COUNT+dialog.getSessionId()));
                }
                DailogConvertor.initDailogDetail(dialogDetail, filedMap);
                DialogAnswer answer = new DialogAnswer();
                DialogAnswerConvertor.initAnswer(answer, filedMap);
                DailogConvertor.initDailogDetail(dialogDetail, filedMap);
                dialogDetail.setId(dialog.getId());
				dialogService.save(dialog, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                dialogDetailService.save(dialogDetail, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                answer.setId(dialog.getId());
                answerService.save(answer, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
            } catch (Exception e) {
            	log.error("error invoked : {}", e);
                responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
                responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
                responseVO.setError(e.getMessage());
            }
         return responseVO;
    }

    /**
     * FUN:删除实体
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "删除指定对象数据", notes = "从数据表中删除指定对象数据")
    @DeleteMapping(value="/{id}")
    public ResponseVO delete(@PathVariable String id) {
        ResponseVO responseVO = new ResponseVO();
        Dialog dialogTemp;
        try {
            dialogTemp = dialogService.findById(id);
            dialogService.delete(dialogTemp);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
            responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }
    /**
     * FUN:删除实体
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "搜索会话历史数据", notes = "搜索会话历史数据")
    @GetMapping("/searchLogs")
    public ResponseVO searchLogs(
    		@RequestParam(name="bId",required=false) Long bId,
    		@RequestParam(name="sessionId",required=false) String sessionId,
    		@RequestParam(name="pageno",required=false,defaultValue="1")  int pageno,
    		@RequestParam(name="pagesize",required=false,defaultValue="20")  int pagesize,
    		@RequestParam(name="stadate",required=false)  String stadate
    		) {
    	ResponseVO responseVO = new ResponseVO();
    	if(pageno<0) {
    		log.info("default query page 1 record");
    		pageno = 0;
    	}
    	if(pagesize <=0) {
    		log.info("if pagesize less then 0, then set default 100");
    		pagesize = 100;
    	}
    	if(pagesize >= 1000) {
    		log.info("limit 1000 records per query ");
    		pagesize = 1000;
    	}
		responseVO.setData(dialogService.getDialogLogs(String.valueOf(BizUtil.parseAppid(bId.intValue())), 
				DateUtil.string2Date2String(stadate, GlobConts.DATE_PARTERN_LONG, GlobConts.DATE_PARTERN_MONTH), 
				sessionId, PageRequest.of(pageno, pagesize)));
		responseVO.setCode(resCodeProperties.getSuccessCode());
		responseVO.setMessage(resCodeProperties.getSuccessMsg());
    	return responseVO;
    }

}