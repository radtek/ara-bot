package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotFaqOverview;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.HotFaqOverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/hotfaqoverview")
@Api(value = "热点分析数据统计服务API", description = "热点分析数据统计相关接口定义")
@Slf4j
public class HotFaqOverviewApiController {

    private HotFaqOverviewService hotFaqOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public HotFaqOverviewApiController(HotFaqOverviewService hotFaqOverviewService, ResCodeProperties resCodeProperties) {
        this.hotFaqOverviewService = hotFaqOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询FAQ热点上升统计分析数据
     *
     * @param bId      业务标识
     * @param token    交互令牌
     * @param date     查询的日期
     * @param limitAsk 访问量下限设置
     * @param limitFlu 访问量振幅下限设置
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取满意度统计分析数据", notes = "根据条件查询满意度统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "token", value = "交互令牌", dataType = "String", paramType = "query", defaultValue = "56b203057f50c6c4de"),
            @ApiImplicitParam(name = "date", value = "查询的日期", dataType = "String", paramType = "query", defaultValue = "20180810"),
            @ApiImplicitParam(name = "limitAsk", value = "访问量下限设置", dataType = "int", paramType = "query", defaultValue = "40"),
            @ApiImplicitParam(name = "limitFlu", value = "访问量振幅下限设置", dataType = "String", paramType = "query", defaultValue = "0.1")
    })
    @GetMapping("/queryByDateAndChanel")
    public ResponseVO queryByDateAndChanel(@NotNull String bId, @NotNull String date, String token, int limitAsk, String limitFlu) {
        ResponseVO responseVO = new ResponseVO();
        String endDate = CustomTimeUtil.changeDateFormate(date);
        String startDate = CustomTimeUtil.getFormatAnyDate(date, 30L);
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //根据条件查询数据
            List<HotFaqOverview> hotOverviewList = hotFaqOverviewService.findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualOrderByVisitTrendDesc(bId, startDate, endDate, limitAsk, limitFlu);
            //返回结果给调用方
            if (null != hotOverviewList && hotOverviewList.size() > 0) {
                List<Map<String, Object>> mapList = new ArrayList<>();
                //处理提问总量数组，满意度率数组，数据数组
                hotOverviewList.forEach(x -> {
                    Map<String, Object> mapTemp = new HashMap<>(4);
                    mapTemp.put("ask", x.getVisitNum());
                    mapTemp.put("q", x.getFaq());
                    mapTemp.put("flu", x.getVisitNum());
                    mapTemp.put("t", x.getVisitNum());
                    mapList.add(mapTemp);
                });
                //处理成功
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
                responseVO.setData(mapList);
            } else {
                responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
                responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
                responseVO.setError(resCodeProperties.getReqContentNotExistMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseVO.setCode(resCodeProperties.getInternalServerErrorCode());
            responseVO.setMessage(resCodeProperties.getInternalServerErrorMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 查询实体列表
     *
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "获取数据列表", notes = "获取查询对象的数据列表")
    @GetMapping
    public ResponseVO queryList() {
        ResponseVO responseVO = new ResponseVO();
        List<HotFaqOverview> hotFaqOverviewList = hotFaqOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(hotFaqOverviewList));
        return responseVO;
    }

    /**
     * 按id查询实体
     *
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "获取指定对象数据", notes = "以对象标识查询指定对象的数据")
    @GetMapping(value = "/{id}")
    public ResponseVO query(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        HotFaqOverview hotFaqOverview;
        try {
            hotFaqOverview = hotFaqOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(hotFaqOverview));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 新增实体
     *
     * @param hotFaqOverview 实体对象
     * @param error          实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
//    @ApiImplicitParam(name = "hotFaqOverview", value = "对象实体数据",required = true, dataType = "HotFaqOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid HotFaqOverview hotFaqOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                hotFaqOverviewService.save(hotFaqOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
            } catch (InternalServiceException e) {
                responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
                responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
                responseVO.setError(e.getMessage());
            }
        } else {
            responseVO.setCode(resCodeProperties.getReqFormatErrorCode());
            responseVO.setMessage(resCodeProperties.getReqFormatErrorMsg());
            responseVO.setError(error.getAllErrors().get(0).getDefaultMessage());
        }
        return responseVO;
    }

    /**
     * 更新实体
     *
     * @param hotFaqOverview 实体对象
     * @param error          实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
//    @ApiImplicitParam(name = "hotFaqOverview", value = "对象实体数据",required = true, dataType = "HotFaqOverview")
    @PutMapping(value = "/{id}")
    public ResponseVO update(@PathVariable Long id, @RequestBody @Valid HotFaqOverview hotFaqOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                hotFaqOverview.setId(id);
                hotFaqOverviewService.update(hotFaqOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
            } catch (InternalServiceException e) {
                responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
                responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
                responseVO.setError(e.getMessage());
            }
        } else {
            responseVO.setCode(resCodeProperties.getReqFormatErrorCode());
            responseVO.setMessage(resCodeProperties.getReqFormatErrorMsg());
            responseVO.setError(error.getAllErrors().get(0).getDefaultMessage());
        }
        return responseVO;
    }

    /**
     * 删除实体
     *
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "删除指定对象数据", notes = "从数据表中删除指定对象数据")
    @DeleteMapping(value = "/{id}")
    public ResponseVO delete(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        HotFaqOverview hotFaqOverviewTemp;
        try {
            hotFaqOverviewTemp = hotFaqOverviewService.findById(id);
            hotFaqOverviewService.delete(hotFaqOverviewTemp);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
            responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }
}

