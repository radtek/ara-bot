package com.zhuiyi.convertor;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogDetail;

public class DailogConvertor {

	public static void initDailogDetail(DialogDetail obj, Map<String, String> params) throws Exception {
		obj.setExactTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		if (StringUtils.isNotEmpty(params.get("recv_time"))) {
			obj.setRecvTime(DateUtil.string2Date(params.get("recv_time"), GlobConts.DATE_PARTERN_SHORT_TRIM));
		}
		if (StringUtils.isNotEmpty(params.get("ret_msg"))) {
			obj.setRetmsg(params.get("ret_msg"));
		} else
		if (StringUtils.isNotEmpty(params.get("top1"))) {
			obj.setDirectFaqId(params.get("top1"));
		}
		if (StringUtils.isNotEmpty(params.get("top3"))) {
			obj.setIndirectFaqId(params.get("top3"));
		}
		if (StringUtils.isNotEmpty(params.get("top1"))) {
			obj.setDirectFaq(params.get("fq"));
		}
		if (StringUtils.isEmpty(params.get("top1"))) {
			obj.setIndirectFaq(params.get("fq"));
		}
		if (StringUtils.isNotEmpty(params.get("user_ip"))) {
			obj.setUserIp(params.get("user_ip"));
		}
		if (StringUtils.isNotEmpty(params.get("query"))) {
			obj.setQuery(params.get("query"));
		}
		if (StringUtils.isNotEmpty(params.get("biz_portal"))) {
			obj.setBizPortal(params.get("biz_portal"));
		}
		if (StringUtils.isNotEmpty(params.get("biz_ret_code"))) {
			obj.setBizRetCode(Integer.parseInt(params.get("biz_ret_code")));
		}
		if (StringUtils.isNotEmpty(params.get("biz_docid"))) {
			obj.setBizDocid(params.get("biz_docid"));
		}
		if (StringUtils.isNotEmpty(params.get("biz_ret_str"))) {
			obj.setBizRetStr(params.get("biz_ret_str"));
		}
		if (StringUtils.isNotEmpty(params.get("ccd_cost"))) {
			obj.setCcdCost(Integer.parseInt(params.get("ccd_cost")));
		}
		if (StringUtils.isNotEmpty(params.get("x_forward"))) {
			obj.setXforward(params.get("x_forward"));
		}
		if (StringUtils.isNotEmpty(params.get("biz_cost"))) {
			obj.setBizCost(Integer.parseInt(params.get("biz_cost")));
		}
		if (StringUtils.isNotEmpty(params.get("answer_index"))) {
			obj.setAnswerIndex(params.get("answer_index"));
		}
		if (StringUtils.isNotEmpty(params.get("place"))) {
			obj.setPlace(params.get("place"));
		}
		if (StringUtils.isNotEmpty(params.get("other"))) {
			obj.setOther(params.get("other"));
		}
		if (StringUtils.isNotEmpty(params.get("sen_level"))) {
			obj.setSenLevel(Integer.parseInt(params.get("sen_level")));
		}
		if (StringUtils.isNotEmpty(params.get("sen_words"))) {
			obj.setSenWords(params.get("sen_words"));
		}
		if (StringUtils.isNotEmpty(params.get("all_confidence"))) {
			obj.setConfidence(params.get("all_confidence"));
		}
	}

	public static void initDailog(Dialog obj, Map<String, String> params) throws Exception {
		if (StringUtils.isNotEmpty(params.get("receiveTime"))) {
			obj.setExactTime(DateUtil.string2Date(params.get("receiveTime"), GlobConts.DATE_PARTERN_LONG));
		}
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		obj.setDateSign(DateUtil.formatNow(GlobConts.DATE_PARTERN_DATE_SIGN));
		if(StringUtils.isNotEmpty(params.get("top1")) && StringUtils.isNotEmpty(params.get("top3"))) {
			if(StringUtils.isNotEmpty(params.get("query")) && StringUtils.isNotEmpty(params.get("fq")) && params.get("query").equals(params.get("fq")) ) {
				obj.setAnswerType("01");
			} else {
				obj.setAnswerType("0");
			}
		} else {
			obj.setAnswerType("-1");
		}
        if (StringUtils.isNotEmpty(params.get("top1"))) {
            obj.setDirectFaqId(params.get("top1"));
        }
        if (StringUtils.isNotEmpty(params.get("top3"))) {
            obj.setIndirectFaqId(params.get("top3"));
        }
        if (StringUtils.isNotEmpty(params.get("top1"))) {
            obj.setDirectFaq(params.get("fq"));
        }
        if (StringUtils.isEmpty(params.get("top1"))) {
            obj.setIndirectFaq(params.get("fq"));
        }
		//  self['faq_cnt'] = len(adaobj.top3.split(","))
		if (StringUtils.isNotEmpty(params.get("top3"))) {
			obj.setFaqNum(params.get("top3").split(",").length);
		}
		//self['ten_min'] = adaobj.time[0:15] + "0"    ------- HH:mm
		if (StringUtils.isNotEmpty(params.get("receiveTime"))) {
			obj.setTenMin(CustomTimeUtil.getTenMinute(obj.getExactTime()));
		}
		obj.setIsReject(0);
		obj.setIsClickGood(0);
		obj.setIsClickBad(0);
		//self['click_zrg'] = redisop.getzrgtype(self['pid'], self['bid'], self['top1'], self['curr_time'])
		obj.setIsZrg("-");
		if (StringUtils.isNotEmpty(params.get("session"))) {
			if(!StringUtils.equals(params.get("session"), "0") && !StringUtils.equals(params.get("session"), "") && !StringUtils.equals(params.get("session"), "-")) {
				obj.setSessionId(params.get("session"));
			} else {
				obj.setSessionId(params.get("session") +"_"+ params.get("search_id"));
			}
		}
		if (StringUtils.isNotEmpty(params.get("search_id"))) {
			obj.setSearchId(params.get("search_id"));
		}
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		if (StringUtils.isNotEmpty(params.get("ret_code")) || StringUtils.isNotEmpty(params.get("retcode"))) {
			obj.setRetcode(params.get("ret_code")==null?Integer.parseInt(params.get("retcode")):Integer.parseInt(params.get("ret_code")));
		}
		if (StringUtils.isNotEmpty(params.get("source"))) {
			obj.setSource(params.get("source"));
		}
		obj.setCountry("-");
		obj.setProvince("-");
		obj.setCity("-");
		if (StringUtils.isNotEmpty(params.get("adaptor"))) {
			obj.setAdaptorCost(Integer.parseInt(params.get("adaptor")));
		}
		if (StringUtils.isNotEmpty(params.get("clientip"))) {
			obj.setClientip(params.get("clientip"));
		}
		if (StringUtils.isNotEmpty(params.get("biz_type"))) {
			obj.setBizType(Integer.parseInt(params.get("biz_type")));
		}
		if (StringUtils.isNotEmpty(params.get("business_name"))) {
			obj.setBusinessName(params.get("business_name"));
		}
		if (StringUtils.isNotEmpty(params.get("channel"))) {
			obj.setChannel(params.get("channel"));
		}
		if (StringUtils.isNotEmpty(params.get("cid"))) {
			obj.setCid(params.get("cid"));
		}
		if (StringUtils.isNotEmpty(params.get("eid"))) {
			obj.setEid(params.get("eid"));
		}
		if (StringUtils.isNotEmpty(params.get("labels"))) {
			obj.setLables(params.get("labels"));
		}
		// im渠道：yiconnect、live800
		if (StringUtils.isNotEmpty(params.get("im"))) {
			obj.setIm(params.get("im"));
		}
		if (StringUtils.isNotEmpty(params.get("clientip"))) {
			obj.setClient(params.get("client"));
		}
		if (StringUtils.isNotEmpty(params.get("tags"))) {
			obj.setTags(params.get("tags"));
		}
		obj.setGmtCreate(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setGmtCreate(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setReserved1("-");
		obj.setReserved2("-");
		obj.setReserved3("-");
		obj.setReserved4("-");
		obj.setReserved5("-");
	}
}
