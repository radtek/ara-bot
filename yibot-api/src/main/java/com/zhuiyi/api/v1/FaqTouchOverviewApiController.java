package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.FaqTouchOverview;
import com.zhuiyi.model.dto.FaqTouchOverviewArrayDTO;
import com.zhuiyi.model.dto.FaqTouchOverviewDTO;
import com.zhuiyi.model.dto.FaqTouchRateArrayDTO;
import com.zhuiyi.model.dto.FaqTouchRateDTO;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.FaqTouchOverviewService;
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
import java.util.stream.Collectors;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/faqtouchoverview")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class FaqTouchOverviewApiController {

    private FaqTouchOverviewService faqTouchOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public FaqTouchOverviewApiController(FaqTouchOverviewService faqTouchOverviewService, ResCodeProperties resCodeProperties){
        this.faqTouchOverviewService = faqTouchOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询知识点统计分析数据
     *
     * @param faqId      查询的日期数据
     * @param faqType      查询的日期数据
     * @param beginTime 开始的时间
     * @param endTime   结束的时间
     * @param bId       业务标识
     * @param cid       渠道条件
     * @param eid       渠道条件
     * @param client    渠道条件
     * @param labels    渠道条件
     * @param im        渠道条件
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取知识点统计分析数据", notes = "根据条件查询知识点统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "faqId", value = "查询的FAQ标识", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "faqType", value = "查询的FAQ类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "cid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "client", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labels", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "im", value = "渠道条件", dataType = "String", paramType = "query")
    })
    @GetMapping("/query")
    public ResponseVO queryByDateAndChanel(Integer faqId, Integer faqType, String beginTime, String endTime, @NotNull String bId, String cid, String eid, String client, String labels, String im) {
        ResponseVO responseVO = new ResponseVO();
        FaqTouchOverview faqTouchOverviewFilter = new FaqTouchOverview();
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //设置渠道筛选条件对象
            CustomObjectUtil.getObjectFilter(faqTouchOverviewFilter, cid, eid, client, labels, im);
            //根据条件查询数据
            List<FaqTouchRateDTO> faqTouchOverviewDTOList;
            //按日期区间查询数据条件
            if (null != beginTime && null != endTime) {
                //初始一个结果数据对象
                Object resultData;
                if (faqId != null && faqType != null) {
                    faqTouchOverviewDTOList = faqTouchOverviewService.findByAppidAndFaqIdAndBizTypeAndDateSignBetween(bId,faqId,faqType, beginTime.substring(0, 10), endTime.substring(0, 10));
                } else {
                    List<String> dayArray = CustomTimeUtil.getSimDateSignBetween(beginTime + " 00:00:00", endTime + " 00:00:00", CustomTimeUtil.STD_DATE_FORMATE);
                    faqTouchOverviewDTOList = faqTouchOverviewService.findSumByAppidAndDateArray(bId, dayArray);
                }
                FaqTouchRateArrayDTO faqTouchOverviewDTO = new FaqTouchRateArrayDTO(new ArrayList<>(),new ArrayList<>());

                faqTouchOverviewDTOList.forEach(x -> {
                    HashMap<String, Object> valueMap = new HashMap<>(8);
                    faqTouchOverviewDTO.getDateArray().add(x.getDataSign());
                    valueMap.put("percentage", x.getPercentage());
                    valueMap.put("askCount", x.getAskCount());
                    valueMap.put("assentCount", x.getAssentCount());
                    valueMap.put("noAssentCount", x.getNoAssentCount());
                    valueMap.put("assentRate", x.getAssentRate());
                    valueMap.put("noAssentRate", x.getNoAssentRate());
                    valueMap.put("zrgCount", x.getZrgCount());
                    valueMap.put("zrgRate", x.getZrgRate());
                    faqTouchOverviewDTO.getValueArray().add(valueMap);
                });
                resultData = faqTouchOverviewDTO;
                //处理成功
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
                responseVO.setData(resultData);
            } else {
                responseVO.setCode(resCodeProperties.getReqDataMissCode());
                responseVO.setMessage(resCodeProperties.getReqDataMissMsg());
                responseVO.setError(resCodeProperties.getReqDataMissMsg());
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
     * 根据条件查询FAQ标准问统计分析数据
     *
     * @param faqId      查询的日期数据
     * @param faqType      查询的日期数据
     * @param beginTime 开始的时间
     * @param endTime   结束的时间
     * @param bId       业务标识
     * @param pageNo       当前页数
     * @param pageSize       每页数量
     * @param sortField       排序字段
     * @param sortType       排序类型
     * @param cid       渠道条件
     * @param eid       渠道条件
     * @param client    渠道条件
     * @param labels    渠道条件
     * @param im        渠道条件
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取FAQ标准问统计分析数据", notes = "根据条件查询FAQ标准问统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "faqId", value = "查询的FAQ标识", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "faqType", value = "查询的FAQ类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sortField", value = "排序字段", dataType = "String", paramType = "query", defaultValue = "touch_num"),
            @ApiImplicitParam(name = "sortType", value = "排序类型", dataType = "Integer", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "cid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "client", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labels", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "im", value = "渠道条件", dataType = "String", paramType = "query")
    })
    @GetMapping("/queryFaq")
    public ResponseVO queryFaqPageByDateAndChanel(Integer faqId, Integer faqType, String beginTime, String endTime, @NotNull String bId, Integer pageNo, Integer pageSize, String sortField, Integer sortType, String cid, String eid, String client, String labels, String im) {
        ResponseVO responseVO = new ResponseVO();
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //根据条件查询数据
            List<FaqTouchOverviewDTO> faqTouchOverviewDTOList;
            FaqTouchOverviewArrayDTO faqTouchOverviewArrayDTO = new FaqTouchOverviewArrayDTO();
            //按日期区间查询数据条件
            if (null != beginTime && null != endTime) {
                faqTouchOverviewDTOList = faqTouchOverviewService.findFaqPageByAppidAndDateSignBetween(bId,beginTime,endTime,faqId,faqType,labels,sortField,sortType,pageNo,pageSize);
                faqTouchOverviewArrayDTO.setCount(faqTouchOverviewDTOList.size());
                faqTouchOverviewArrayDTO.setRows(faqTouchOverviewDTOList.stream().skip((pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList()));
                //处理成功
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
                responseVO.setData(faqTouchOverviewArrayDTO);
            } else {
                responseVO.setCode(resCodeProperties.getReqDataMissCode());
                responseVO.setMessage(resCodeProperties.getReqDataMissMsg());
                responseVO.setError(resCodeProperties.getReqDataMissMsg());
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
    @ApiOperation(value = "获取数据列表", notes = "获取查询对象的数据列表")
    @GetMapping
    public ResponseVO queryList(){
        ResponseVO responseVO = new ResponseVO();
        List<FaqTouchOverview> faqTouchOverviewList = faqTouchOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(faqTouchOverviewList));
        return responseVO;
    }

    /**
     * 按id查询实体
     * @param id 实体对象id
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取指定对象数据", notes = "以对象标识查询指定对象的数据")
    @GetMapping(value="/{id}")
    public ResponseVO query(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        FaqTouchOverview faqTouchOverview;
        try {
            faqTouchOverview = faqTouchOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(faqTouchOverview));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 新增实体
     * @param faqTouchOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
    @ApiImplicitParam(name = "faqTouchOverview", value = "对象实体数据",required = true, dataType = "FaqTouchOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid FaqTouchOverview faqTouchOverview, BindingResult error) {
         ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                faqTouchOverviewService.save(faqTouchOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param faqTouchOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
    @ApiImplicitParam(name = "faqTouchOverview", value = "对象实体数据",required = true, dataType = "FaqTouchOverview")
    @PutMapping(value="/{id}")
    public ResponseVO update(@PathVariable Long id,@RequestBody @Valid FaqTouchOverview faqTouchOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                faqTouchOverview.setId(id);
                faqTouchOverviewService.update(faqTouchOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
        FaqTouchOverview faqTouchOverviewTemp;
        try {
            faqTouchOverviewTemp = faqTouchOverviewService.findById(id);
            faqTouchOverviewService.delete(faqTouchOverviewTemp);
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

