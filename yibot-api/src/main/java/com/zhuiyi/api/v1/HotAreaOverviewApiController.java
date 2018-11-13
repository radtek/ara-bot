package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotAreaOverview;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.HotAreaOverviewService;
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
@RequestMapping("/yibot/v1/api/hotareaoverview")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class HotAreaOverviewApiController {

    private HotAreaOverviewService hotAreaOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public HotAreaOverviewApiController(HotAreaOverviewService hotAreaOverviewService, ResCodeProperties resCodeProperties) {
        this.hotAreaOverviewService = hotAreaOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询地域热点上升统计分析数据
     *
     * @param bId          业务标识
     * @param token        交互令牌
     * @param date         查询的日期
     * @param cityLimitAsk 城市访问量下限设置
     * @param cityLimitFlu 城市访问量振幅下限设置
     * @param proLimitAsk  省份访问量下限设置
     * @param proLimitFlu  省份访问量振幅下限设置
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取地域热点上升统计分析数据", notes = "根据条件查询地域热点上升统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "token", value = "交互令牌", dataType = "String", paramType = "query", defaultValue = "56b203057f50c6c4de"),
            @ApiImplicitParam(name = "date", value = "查询的日期", dataType = "String", paramType = "query", defaultValue = "20180810"),
            @ApiImplicitParam(name = "cityLimitAsk", value = "城市访问量下限设置", dataType = "int", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "cityLimitFlu", value = "城市访问量振幅下限设置", dataType = "String", paramType = "query", defaultValue = "0.3"),
            @ApiImplicitParam(name = "proLimitAsk", value = "省份访问量下限设置", dataType = "int", paramType = "query", defaultValue = "200"),
            @ApiImplicitParam(name = "proLimitFlu", value = "省份访问量振幅下限设置", dataType = "String", paramType = "query", defaultValue = "0.4")
    })
    @GetMapping("/query")
    public ResponseVO queryByDateAndChanel(@NotNull String bId, @NotNull String date, String token, int cityLimitAsk, String cityLimitFlu, int proLimitAsk, String proLimitFlu) {
        ResponseVO responseVO = new ResponseVO();
        String endDate = CustomTimeUtil.changeDateFormate(date);
        String startDate = CustomTimeUtil.getFormatAnyDate(date, 30L);
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //根据条件查询城市热点振幅数据
            List<HotAreaOverview> hotCityOverviewList = hotAreaOverviewService.findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(bId, startDate, endDate,
                    cityLimitAsk, cityLimitFlu, GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE, GlobaSystemConstant.AREA_TYPE.CITY.getElementName());
            //根据条件查询城市热点振幅数据
            List<HotAreaOverview> hotProvinceOverviewList = hotAreaOverviewService.findByAppidAndDateSignBetweenAndVisitNumGreaterThanEqualAndVisitTrendGreaterThanEqualAndAreaNameIsNotAndAreaTypeOrderByVisitTrendDesc(bId, startDate, endDate,
                    cityLimitAsk, cityLimitFlu, GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE, GlobaSystemConstant.AREA_TYPE.PROVINCE.getElementName());
            //返回结果给调用方
            List<Map<String, Object>> mapList = new ArrayList<>();
            if (null != hotProvinceOverviewList && hotProvinceOverviewList.size() > 0) {
                hotProvinceOverviewList.forEach(x -> {
                    Map<String, Object> mapTemp = new HashMap<>(5);
                    mapTemp.put("ask", x.getVisitNum());
                    mapTemp.put("placeName", x.getAreaName());
                    mapTemp.put("type", x.getAreaType() - 1);
                    mapTemp.put("flu", x.getVisitTrend());
                    mapTemp.put("time", x.getDateSign());
                    mapList.add(mapTemp);
                });
            }
            if (null != hotCityOverviewList && hotCityOverviewList.size() > 0) {
                hotCityOverviewList.forEach(x -> {
                    Map<String, Object> mapTemp = new HashMap<>(5);
                    mapTemp.put("ask", x.getVisitNum());
                    mapTemp.put("placeName", x.getAreaName());
                    mapTemp.put("type", x.getAreaType() - 1);
                    mapTemp.put("flu", x.getVisitTrend());
                    mapTemp.put("time", x.getDateSign());
                    mapList.add(mapTemp);
                });
            }
            //处理成功
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(mapList);
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
        List<HotAreaOverview> hotAreaOverviewList = hotAreaOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(hotAreaOverviewList));
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
        HotAreaOverview hotAreaOverview;
        try {
            hotAreaOverview = hotAreaOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(hotAreaOverview));
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
     * @param hotAreaOverview 实体对象
     * @param error           实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
//    @ApiImplicitParam(name = "hotAreaOverview", value = "对象实体数据",required = true, dataType = "HotAreaOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid HotAreaOverview hotAreaOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                hotAreaOverviewService.save(hotAreaOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param hotAreaOverview 实体对象
     * @param error           实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
//    @ApiImplicitParam(name = "hotAreaOverview", value = "对象实体数据",required = true, dataType = "HotAreaOverview")
    @PutMapping(value = "/{id}")
    public ResponseVO update(@PathVariable Long id, @RequestBody @Valid HotAreaOverview hotAreaOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                hotAreaOverview.setId(id);
                hotAreaOverviewService.update(hotAreaOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
        HotAreaOverview hotAreaOverviewTemp;
        try {
            hotAreaOverviewTemp = hotAreaOverviewService.findById(id);
            hotAreaOverviewService.delete(hotAreaOverviewTemp);
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

