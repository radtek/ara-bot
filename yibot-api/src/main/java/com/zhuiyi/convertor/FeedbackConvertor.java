package com.zhuiyi.convertor;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.common.constant.GlobConts;
import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.model.FeedbackBack;
import com.zhuiyi.model.Session;

public class FeedbackConvertor {
	public static void initFeedback(FeedbackBack obj, Map<String, String> params) throws Exception{
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setExactTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setData(JSON.toJSONString(params));
		obj.setIsRedo(0);
	}
	public static void initDialog(Dialog obj, Map<String, String> params) throws Exception{
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setId(params.get("sessionId") + params.get("round_cnt"));
		obj.setExactTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		obj.setDateSign(DateUtil.formatNow(GlobConts.DATE_PARTERN_DATE_SIGN));
		obj.setDirectFaq(params.get("faq"));
	}
	public static void initDialogDetail(DialogDetail obj, Map<String, String> params) throws Exception{
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setId(params.get("sessionId") + params.get("round_cnt"));
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		obj.setExactTime(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setQuery(params.get("question"));
	}
	public static void initSession(Session obj, Map<String, String> params) throws Exception{
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		obj.setId(params.get("sessionId"));
		obj.setDateMonth(DateUtil.formatNow(GlobConts.DATE_PARTERN_MONTH));
		obj.setDateSign(DateUtil.formatNow(GlobConts.DATE_PARTERN_DATE_SIGN));
		obj.setRoundNum(0);
		obj.setAccount(params.get("account"));
		obj.setZrgType(params.get("zrg_type"));
		if("3".equals(params.get("click_type"))) {
			obj.setClickBadNum(1);
		}
		if("2".equals(params.get("click_type"))) {
			obj.setClickGoodNum(1);
		}
	}
}
