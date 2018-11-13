/**
* 文件名：InitConfigController.java
*
* 版本信息：
* 日期：2018年8月20日 下午5:53:50
* Copyright 版权所有 @Zhuiyi Inc 2018
*/
package com.zhuiyi.api.v1;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.model.dto.ZrgConfigReqDto;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.RedisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
* 此类描述的是：
* @author: klauszhou@wezhuiyi.com
* @version: 2018年8月20日 下午5:53:50
*/
@RestController
@RequestMapping("/yibot/v1/api/initconfig")
@Api(value = "配置服务API", description = "配置服务相关接口定义")
@Slf4j
public class InitConfigController {
	@Autowired
	RedisService redisService;
	
	
	public static final String PRE_SYS_CONFIG = "sys:config";
	public static final String PRE_SYS_CONFIG_ZRG = "zrg";
	
	@GetMapping
	@ApiOperation(value = "获取配置", notes = "根据业务ID获取配置服务接口")
	public ResponseVO getConfig(String pid, String bid) {
		ResponseVO res = new ResponseVO();
		String zrgKey = CustomObjectUtil.buildKeyString((String) pid+""+bid, null, null, PRE_SYS_CONFIG);
		String data = (String) redisService.findHash(zrgKey, PRE_SYS_CONFIG_ZRG);
		if(StringUtils.isNoneEmpty(data)) {
			res.setData(JSON.parseObject(data, ZrgConfigReqDto.class));
			res.setCode(0);
			res.setMessage("success");
		} else {
			res.setCode(-1);
		}
		return res;
	}
	
	@PostMapping
	@ApiOperation(value = "更新配置", notes = "配置服务相关接口定义")
	public ResponseVO setConfig( @RequestBody ZrgConfigReqDto data) {
		ResponseVO res = new ResponseVO();
		log.trace("ZrgConfigReqDto is {}", data);
		String sysConfigKey = CustomObjectUtil.buildKeyString(data.getBd(), null, null, PRE_SYS_CONFIG);
		redisService.addHashAndExist(sysConfigKey, PRE_SYS_CONFIG_ZRG,JSON.toJSONString(data),-1L, TimeUnit.SECONDS);
		res.setCode(0);
		return res;
	}
	
	

}
