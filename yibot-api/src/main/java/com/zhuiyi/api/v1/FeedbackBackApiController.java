package com.zhuiyi.api.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.convertor.FeedbackConvertor;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.model.FeedbackBack;
import com.zhuiyi.model.Session;
import com.zhuiyi.model.dto.ZrgConfigReqDto;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.DialogDetailService;
import com.zhuiyi.service.DialogService;
import com.zhuiyi.service.FeedbackBackService;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author code-magic
 * @version 1.0 date: 2018/07/17 description: own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/feedback")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class FeedbackBackApiController {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private DialogService dialogService;
	@Autowired
	private DialogDetailService dialogDetailService;
	
	private FeedbackBackService feedbackBackService;
	private ResCodeProperties resCodeProperties;
	
	@Autowired
	RedisService redisService;

	@Autowired
	public FeedbackBackApiController(FeedbackBackService feedbackBackService, ResCodeProperties resCodeProperties) {
		this.feedbackBackService = feedbackBackService;
		this.resCodeProperties = resCodeProperties;
	}

	/**
	 * 新增实体
	 *
	 * @param params 实体对象
	 * @param error  实体对象数据检查中的错误信息
	 * @return ResponseVO 响应数据
	 */
	@ApiOperation(value = "save feedback", notes = "handle feebback info")
	@ApiImplicitParam(name = "input", type = "body", value = "feedback info", required = true, dataType = "String")
	@PostMapping
	public ResponseVO create(HttpServletRequest req) {
		ResponseVO responseVO = new ResponseVO();
		// 对实体对象数据进行检查
		try {
			Map<String, String> filedMap = new HashMap<>(64);
			String input = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			if(!StringUtils.isEmpty(input)) {
            	log.info("[param feedback is] {}",input);
                String[] fileds = input.split(" \\| ");
                for(String i : fileds) {
                    if(StringUtils.isNotEmpty(i)) {
                    	if(!i.contains("[")) {
                    		filedMap.put(i.substring(0, i.indexOf(":")), i.substring(i.indexOf(":")+1, i.length()));
                    	} else {
                    		filedMap.put("receiveTime", i.substring(1, i.length()-1));
                    	}
                    }
                }
            }
			
			FeedbackBack feedbackBack = new FeedbackBack();
			FeedbackConvertor.initFeedback(feedbackBack, filedMap);
			feedbackBackService.save(feedbackBack, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
			
			Session session = sessionService.findByIdAndAppidAndDateMonth(filedMap.get("sessionId"), feedbackBack.getAppid(), DateUtil.formatTime(DateUtil.convertDateToLDT(feedbackBack.getExactTime()), GlobConts.DATE_PARTERN_MONTH));
			
			if(session == null) {
				if(StringUtils.isNoneEmpty(filedMap.get("click_type"))) {
					log.info("转人工");
					if("11".equals(filedMap.get("click_type")) || "12".equals(filedMap.get("click_type"))) {
						Session session2 = new Session();
						FeedbackConvertor.initSession(session2, filedMap);
						sessionService.save(session2, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
					}
				}
			} else {
				if(StringUtils.isNoneEmpty(filedMap.get("zrg_type"))) {
					session.setZrgAt(Integer.parseInt(filedMap.get("zrg_type")));
				}
				if(StringUtils.isNoneEmpty(filedMap.get("click_type"))) {
					log.info("推荐点击");
					if("1".equals(filedMap.get("click_type"))) {
						Dialog dialog = new Dialog();
						FeedbackConvertor.initDialog(dialog, filedMap);
						dialog.setAnswerType("1");
						DialogDetail dialogDetail = new DialogDetail();
						FeedbackConvertor.initDialogDetail(dialogDetail, filedMap);
						dialogService.save(dialog, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
						dialogDetailService.save(dialogDetail, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
						session.setRoundNum(session.getRoundNum()+1);
						sessionService.update(session, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
					}
					log.info("点赞/点踩");
					if("2".equals(filedMap.get("click_type")) || "2".equals(filedMap.get("click_type"))) {
						Dialog dialog = dialogService.getDialogLogs(session.getAppid(), session.getDateMonth(), filedMap.get("session_id") + filedMap.get("round_cnt"));
						if(dialog == null) {
							log.info("没有直接命中，也没有间接命中，应当告警");
						} else {
							if("2".equals(filedMap.get("click_type"))) {
								dialog.setIsClickGood(1);
								session.setClickGoodNum(session.getClickGoodNum()+1);
							} else {
								dialog.setIsClickBad(1);
								session.setClickBadNum(session.getClickBadNum()+1);
							}
						}
						dialogService.update(dialog, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
						sessionService.update(session, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
					}
					
					log.info("转人工");
					if("11".equals(filedMap.get("click_type")) || "12".equals(filedMap.get("click_type"))) {
						session.setZrgAt(Integer.parseInt(filedMap.get("round_cnt")));
						String zrgKey = CustomObjectUtil.buildKeyString(session.getAppid(), null, null, InitConfigController.PRE_SYS_CONFIG);
						String data = (String) redisService.findHash(zrgKey, InitConfigController.PRE_SYS_CONFIG_ZRG);
						if(StringUtils.isEmpty(data)) {
							session.setZrgType("0_on");
						} else {
							ZrgConfigReqDto configReqDto = JSON.parseObject(data, ZrgConfigReqDto.class);
							String  onwktm = DateUtil.isWorkTime(configReqDto.getWorkTime(), feedbackBack.getExactTime());
							String docId = filedMap.get("doc_id");
							String zdorbd = "0";
							for(String i :configReqDto.getBdZrg()) {
								if(i.equals(docId)) {
									zdorbd = "12";
									break;
								}
							}
							for(String i :configReqDto.getZdZrg()) {
								if(i.equals(docId)) {
									zdorbd = "11";
									break;
								}
							}
							session.setZrgType(zdorbd+"_"+onwktm);
						}
						sessionService.update(session, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
					}
				} else {
					
					
					
					
					
				}
				
			}
			responseVO.setCode(resCodeProperties.getSuccessCode());
			responseVO.setMessage(resCodeProperties.getSuccessMsg());
		} catch (Exception e) {
			log.error("handle FeedbackLogInputDto params failed {}", e);
			responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
			responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
			responseVO.setError(e.getMessage());
		}
		return responseVO;
	}
	
	

}
