package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotAreaFqOverview;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.HotAreaFqOverviewService;
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
@RequestMapping("/yibot/v1/api/hotareafqoverview")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class HotAreaFqOverviewApiController {

    private HotAreaFqOverviewService hotAreaFqOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public HotAreaFqOverviewApiController(HotAreaFqOverviewService hotAreaFqOverviewService, ResCodeProperties resCodeProperties){
        this.hotAreaFqOverviewService = hotAreaFqOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询FAQ全国热点统计分析数据
     *
     * @param bId      业务标识
     * @param token    交互令牌
     * @param date     查询的日期
     * @param limitAsk 访问量下限设置
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取FAQ全国热点统计分析数据", notes = "根据条件查询FAQ全国热点统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "token", value = "交互令牌", dataType = "String", paramType = "query", defaultValue = "56b203057f50c6c4de"),
            @ApiImplicitParam(name = "date", value = "查询的日期", dataType = "String", paramType = "query", defaultValue = "20180820"),
            @ApiImplicitParam(name = "limitAsk", value = "访问量下限设置", dataType = "int", paramType = "query", defaultValue = "40"),
    })
    @GetMapping("/query")
    public ResponseVO queryByDateAndChanel(@NotNull String bId, @NotNull String date, String token, int limitAsk) {
        ResponseVO responseVO = new ResponseVO();
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            String dateFormate = CustomTimeUtil.changeDateFormate(date);
            //根据条件查询全国和各省的总量数据
            List<HotAreaFqOverview> hotOverviewTotalList = hotAreaFqOverviewService.findByAppidAndDateSignAndFaq(bId,dateFormate,GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);
            //根据条件查询每个省的热点FAQ数据
            List<HotAreaFqOverview> hotOverviewFaqList = hotAreaFqOverviewService.findHotFaqByAppidAndDateSign(bId,dateFormate);

            //返回结果给调用方
            if (null != hotOverviewTotalList && hotOverviewTotalList.size() > 0) {
                List<Map<String, Object>> mapList = new ArrayList<>();
                //处理提问总量数组，满意度率数组，数据数组
                hotOverviewTotalList.forEach(x -> {
                    Map<String, Object> mapOneTemp = new HashMap<>(1);
                    Map<String, Object> mapTwoTemp = new HashMap<>(3);
                    List<Map<String, Object>> mapListTemp = new ArrayList<>();
                    hotOverviewFaqList.forEach(y -> {
                        if (x.getAreaName().equals(y.getAreaName())) {
                            Map<String, Object> mapThreeTemp = new HashMap<>(3);
                            mapThreeTemp.put("q", y.getFaq());
                            mapThreeTemp.put("flu", y.getVisitTrend());
                            mapThreeTemp.put("views", y.getVisitNum());
                            mapListTemp.add(mapThreeTemp);
                        }
                    });
                    mapTwoTemp.put("ask", x.getVisitNum());
                    mapTwoTemp.put("askTrend", x.getVisitTrend());
                    mapTwoTemp.put("hot", mapListTemp);

                    mapOneTemp.put(x.getAreaName(), mapTwoTemp);
                    mapList.add(mapOneTemp);
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
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "获取数据列表", notes = "获取查询对象的数据列表")
    @GetMapping
    public ResponseVO queryList(){
        ResponseVO responseVO = new ResponseVO();
        List<HotAreaFqOverview> hotAreaFqOverviewList = hotAreaFqOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(hotAreaFqOverviewList));
        return responseVO;
    }

    /**
     * 按id查询实体
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "获取指定对象数据", notes = "以对象标识查询指定对象的数据")
    @GetMapping(value="/{id}")
    public ResponseVO query(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        HotAreaFqOverview hotAreaFqOverview;
        try {
            hotAreaFqOverview = hotAreaFqOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(hotAreaFqOverview));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 新增实体
     * @param hotAreaFqOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
//    @ApiImplicitParam(name = "hotAreaFqOverview", value = "对象实体数据",required = true, dataType = "HotAreaFqOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid HotAreaFqOverview hotAreaFqOverview, BindingResult error) {
         ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                hotAreaFqOverviewService.save(hotAreaFqOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
            } catch (InternalServiceException e) {
                responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
                responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
                responseVO.setError(e.getMessage());
            }
        }else{
            responseVO.setCode(resCodeProperties.getReqFormatErrorCode());
            responseVO.setMessage(resCodeProperties.getReqFormatErrorMsg());
            responseVO.setError(error.getAllErrors().get(0).getDefaultMessage());
        }
         return responseVO;
    }

    /**
     * 更新实体
     * @param hotAreaFqOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
//    @ApiImplicitParam(name = "hotAreaFqOverview", value = "对象实体数据",required = true, dataType = "HotAreaFqOverview")
    @PutMapping(value="/{id}")
    public ResponseVO update(@PathVariable Long id,@RequestBody @Valid HotAreaFqOverview hotAreaFqOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                hotAreaFqOverview.setId(id);
                hotAreaFqOverviewService.update(hotAreaFqOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
            } catch (InternalServiceException e) {
                responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
                responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
                responseVO.setError(e.getMessage());
            }
        }else{
            responseVO.setCode(resCodeProperties.getReqFormatErrorCode());
            responseVO.setMessage(resCodeProperties.getReqFormatErrorMsg());
            responseVO.setError(error.getAllErrors().get(0).getDefaultMessage());
        }
        return responseVO;
    }

    /**
     * 删除实体
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "删除指定对象数据", notes = "从数据表中删除指定对象数据")
    @DeleteMapping(value="/{id}")
    public ResponseVO delete(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        HotAreaFqOverview hotAreaFqOverviewTemp;
        try {
            hotAreaFqOverviewTemp = hotAreaFqOverviewService.findById(id);
            hotAreaFqOverviewService.delete(hotAreaFqOverviewTemp);
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

