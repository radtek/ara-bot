package com.zhuiyi.convertor;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhuiyi.common.util.DateUtil;
import com.zhuiyi.model.DialogAnswer;

public class DialogAnswerConvertor {
	
	public static void initAnswer(DialogAnswer obj, Map<String, String> params) throws Exception {
		if (StringUtils.isNotEmpty(params.get("pid")) && StringUtils.isNotEmpty(params.get("bid"))) {
			obj.setAppid(params.get("pid") + params.get("bid"));
		}
		if (StringUtils.isNotEmpty(params.get("answer"))) {
			obj.setAnswer(params.get("answer"));
		}
		obj.setGmtCreate(DateUtil.convertLDTToDate(LocalDateTime.now()));
		obj.setGmtCreate(DateUtil.convertLDTToDate(LocalDateTime.now()));
	}

	
	
}
