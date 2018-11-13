package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.PartTrendOverview;
import com.zhuiyi.model.dto.PartTrendInfoDTO;
import com.zhuiyi.model.vo.RequestDayVO;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.PartTrendOverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/parttrendoverview")
@Api(value = "今日趋势统计服务API", description = "今日趋势统计相关接口定义")
@Slf4j
public class PartTrendOverviewApiController {

    private PartTrendOverviewService partTrendOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public PartTrendOverviewApiController(PartTrendOverviewService partTrendOverviewService, ResCodeProperties resCodeProperties){
        this.partTrendOverviewService = partTrendOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询部分指标今日趋势统计数据
     *
     * @param requestDayVO 今日趋势数据查询条件 其中渠道组合条件 如果业务配置了多渠道信息，用户可以按照渠道查询概览数据(详见 Yibot多渠道配置说明文档 )；
     *                       AI force 改版之后渠道参数都采用 json 里面的map key- value对方式传输， 目前一共有4个渠道(cid,eid,client,lables，im渠道后面也要支持，
     *                       可以现在加上)，{"cid":["user","test01"], "eid":["app","weixin"], "client":[], "labels":[], "im":[]}
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取今日趋势统计数据", notes = "根据条件查询今日趋势统计数据")
    @ApiImplicitParam(name = "requestDayVO", value = "今日趋势数据查询条件", required = true, dataType = "RequestDayVO")
    @PostMapping("/query")
    public ResponseVO queryByDateAndChanel(@RequestBody @Valid RequestDayVO requestDayVO, BindingResult result) {
        ResponseVO responseVO = new ResponseVO();
        PartTrendOverview partTrendOverviewFilter = new PartTrendOverview();
        try {
            if(!result.hasFieldErrors()){
                //对前端传来的bId进行解析
                requestDayVO.setBId( CustomObjectUtil.solveBid(requestDayVO.getBId()));
                //设置渠道筛选条件对象
                if (null != requestDayVO.getChannels()) {
                    CustomObjectUtil.getMapObjectFilter(partTrendOverviewFilter,requestDayVO.getChannels());
                } else {
                    partTrendOverviewFilter.setIsTotal(1);
                }
                List<PartTrendOverview> partTrendOverviewList;
                //按日期查询优先，若日期和日期区间数据条件都无，则默认查询当天的数据
                if (null != requestDayVO.getDateTime()) {
                    partTrendOverviewList = partTrendOverviewService.findByAppidAndDateSign(requestDayVO.getBId(),  requestDayVO.getDateTime().substring(0, 10));
                } else {
                    partTrendOverviewList = partTrendOverviewService.findByAppidAndDateSign(requestDayVO.getBId(), CustomTimeUtil.getFormatCurrentDate());
                }
                //根据渠道条件从指定数据集中筛选对应数据对象
                List<PartTrendOverview> partTrendOverviewListTemp = (List<PartTrendOverview>) CustomObjectUtil.getObjectByChanel(partTrendOverviewList, partTrendOverviewFilter);
                //返回结果给调用方
                if (null != partTrendOverviewListTemp && partTrendOverviewListTemp.size() > 0) {
                    //处理成功
                    responseVO.setCode(resCodeProperties.getSuccessCode());
                    responseVO.setMessage(resCodeProperties.getSuccessMsg());
                    List<PartTrendInfoDTO> partTrendInfoDTOList = JSONArray.parseArray(partTrendOverviewListTemp.get(0).getData(),PartTrendInfoDTO.class);
                    responseVO.setData(partTrendInfoDTOList);
                } else {
                    responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
                    responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
                    responseVO.setError(resCodeProperties.getReqContentNotExistMsg());
                }
            }else{
                responseVO.setCode(resCodeProperties.getReqDataMissCode());
                responseVO.setMessage(resCodeProperties.getReqDataMissMsg());
                responseVO.setError(result.getAllErrors().get(0).getDefaultMessage());
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
        List<PartTrendOverview> partTrendOverviewList = partTrendOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(partTrendOverviewList));
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
        PartTrendOverview partTrendOverview;
        try {
            partTrendOverview = partTrendOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(partTrendOverview));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }


    /**
     * 新增实体
     * @param partTrendOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
    @ApiImplicitParam(name = "partTrendOverview", value = "对象实体数据",required = true, dataType = "PartTrendOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid PartTrendOverview partTrendOverview, BindingResult error) {
         ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                partTrendOverviewService.save(partTrendOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param partTrendOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
    @ApiImplicitParam(name = "partTrendOverview", value = "对象实体数据",required = true, dataType = "PartTrendOverview")
    @PutMapping(value="/{id}")
    public ResponseVO update(@PathVariable Long id,@RequestBody @Valid PartTrendOverview partTrendOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                partTrendOverview.setId(id);
                partTrendOverviewService.update(partTrendOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
    @ApiOperation(value = "删除指定对象数据", notes = "从数据表中删除指定对象数据")
    @DeleteMapping(value="/{id}")
    public ResponseVO delete(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        PartTrendOverview partTrendOverviewTemp;
        try {
            partTrendOverviewTemp = partTrendOverviewService.findById(id);
            partTrendOverviewService.delete(partTrendOverviewTemp);
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

