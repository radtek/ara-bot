package com.zhuiyi.convertor;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.model.Session;

public class SessionConvertor {
	
	public static void initSession(Session obj, Map<String, String> params) throws Exception {
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		obj.setDateSign(DateUtil.formatNow(GlobConts.DATE_PARTERN_DATE_SIGN));
		if (StringUtils.isNotEmpty(params.get("session"))) {
			obj.setId(params.get("session"));
		}
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setStartTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setEndTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setRoundNum(1);
		if (StringUtils.isNotEmpty(params.get("account"))) {
			obj.setAccount(params.get("account"));
		}
		if (StringUtils.isNotEmpty(params.get("user_ip"))) {
			obj.setUserIp(params.get("user_ip"));
		}
		if (StringUtils.isNotEmpty(params.get("province"))) {
			obj.setProvince(params.get("province"));
		}
		if (StringUtils.isNotEmpty(params.get("country"))) {
			obj.setCountry(params.get("country"));
		}
		if (StringUtils.isNotEmpty(params.get("city"))) {
			obj.setCity(params.get("city"));
		}
		//'sen_level': self['sen_level'] if self['sen_level'] != '-' else '99',       # 这个字段要在数据库当中进行修改，一个session当中保存最高的敏感级
		if (StringUtils.isNotEmpty(params.get("sen_level"))) {
			if("-".equals(params.get("sen_level"))) {
				obj.setSenLevel(99);
			} else {
				obj.setSenLevel(Integer.parseInt(params.get("sen_level")));
			}
		} else {
			obj.setSenLevel(99);
		}
		obj.setJudgeType(0);
		obj.setClickGoodNum(0);
		obj.setClickBadNum(0);
		if (StringUtils.isNotEmpty(params.get("click_zrg"))) {
			obj.setZrgType(params.get("click_zrg"));
		}
		if (StringUtils.isNotEmpty(params.get("source_from"))) {
			obj.setSource(params.get("source_from"));
		}
		if (StringUtils.isNotEmpty(params.get("channel"))) {
			obj.setChannel(params.get("channel"));
		}
		if (StringUtils.isNotEmpty(params.get("channel"))) {
			obj.setChannel(params.get("channel"));
		}
		//'zrg_at': self['sequence_id'].split("_")[-1] if self['click_zrg'].startswith("11") or self['click_zrg'].startswith("12") else '-1',   # 获取会话轮数, 这个字段没有意义
		if (StringUtils.isNotEmpty(params.get("click_zrg"))) {
			if(params.get("click_zrg").startsWith("11") || params.get("click_zrg").startsWith("12")) {
				if(StringUtils.isNotEmpty(params.get("sequence_id"))) {
					String[] ss = params.get("sequence_id").split("-");
					obj.setZrgAt(Integer.parseInt(ss[ss.length-1]));
				}
			} else {
				obj.setZrgAt(-1);
			}
		}
		if (StringUtils.isNotEmpty(params.get("biz_type"))) {
			obj.setHasHanxuan(Integer.parseInt(params.get("biz_type")));
		}
		if (StringUtils.isNotEmpty(params.get("biz_type"))) {
			obj.setHasHanxuan(Integer.parseInt(params.get("biz_type")));
		}
		if (StringUtils.isNotEmpty(params.get("reject_recog"))) {
			obj.setHasReject(Integer.parseInt(params.get("reject_recog")));
		}
		if (StringUtils.isNotEmpty(params.get("faq_cnt"))) {
			if(params.get("faq_cnt").equals("1") ){
				obj.setHasAns1(1);
			} else {
				obj.setHasAns1(0);
			}
			if(params.get("faq_cnt").equals("3")) {
				obj.setHasAns3(3);
			} else {
				obj.setHasAns3(0);
			}
				
		}
		
		if (StringUtils.isNotEmpty(params.get("cid"))) {
			obj.setCid(params.get("cid"));
		}
		if (StringUtils.isNotEmpty(params.get("eid"))) {
			obj.setEid(params.get("eid"));
		}
		if (StringUtils.isNotEmpty(params.get("business_name"))) {
			obj.setBusinessName(params.get("business_name"));
		}
		if (StringUtils.isNotEmpty(params.get("client"))) {
			obj.setClient(params.get("client"));
		}
		if (StringUtils.isNotEmpty(params.get("labels"))) {
			obj.setLables(params.get("labels"));
		}
		if (StringUtils.isNotEmpty(params.get("im"))) {
			obj.setIm(params.get("im"));
		}
	}
}
