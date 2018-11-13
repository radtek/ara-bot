package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ProvinceOverview;
import com.zhuiyi.model.dto.ProvinceOverviewDTO;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.ProvinceOverviewService;
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
@RequestMapping("/yibot/v1/api/provinceoverview")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class ProvinceOverviewApiController {

    private ProvinceOverviewService provinceOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public ProvinceOverviewApiController(ProvinceOverviewService provinceOverviewService, ResCodeProperties resCodeProperties){
        this.provinceOverviewService = provinceOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询FAQ触达率统计数据
     *
     * @param date      查询的日期数据
     * @param startDate 开始的时间
     * @param endDate   结束的时间
     * @param bId       业务标识
     * @param cid       渠道条件
     * @param eid       渠道条件
     * @param client    渠道条件
     * @param labels    渠道条件
     * @param im        渠道条件
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取整体趋势统计数据", notes = "根据条件查询整体趋势统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "查询的日期数据", dataType = "String", paramType = "query", defaultValue = "2018-07-27 12:00:00"),
            @ApiImplicitParam(name = "startDate", value = "开始的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束的时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "cid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "client", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labels", value = "渠道条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "im", value = "渠道条件", dataType = "String", paramType = "query")
    })
    @GetMapping("/query")
    public ResponseVO queryByDateAndChanel(String date, String startDate, String endDate, @NotNull String bId, String cid, String eid, String client, String labels, String im) {
        ResponseVO responseVO = new ResponseVO();
        ProvinceOverview provinceOverviewFilter = new ProvinceOverview();
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //设置渠道筛选条件对象
            CustomObjectUtil.getObjectFilter(provinceOverviewFilter, cid, eid, client, labels, im);
            //根据条件查询数据
            List<ProvinceOverview> provinceOverviewList;
            //按日期查询优先，若日期和日期区间数据条件都无，则默认查询当天的数据
            if (null != date) {
                provinceOverviewList = provinceOverviewService.findByAppidAndDateSign(bId, date.substring(0, 10));
            } else if (null != startDate && null != endDate) {
                provinceOverviewList = provinceOverviewService.findByAppidAndDateSignBetween(bId, startDate.substring(0, 10), endDate.substring(0, 10));
            } else {
                provinceOverviewList = provinceOverviewService.findByAppidAndDateSign(bId, CustomTimeUtil.getFormatCurrentDate());
            }
            //根据渠道条件从指定数据集中筛选对应数据对象
            List<ProvinceOverview> provinceOverviewListTemp = (List<ProvinceOverview>) CustomObjectUtil.getObjectByChanel(provinceOverviewList, provinceOverviewFilter);
            //返回结果给调用方
            if (null != provinceOverviewListTemp && provinceOverviewListTemp.size() > 0) {
                if(provinceOverviewListTemp.size() == 1){
                    List<ProvinceOverviewDTO> provinceOverviewDTOList = JSONArray.parseArray(provinceOverviewListTemp.get(0).getData(),ProvinceOverviewDTO.class);
                    responseVO.setData(provinceOverviewDTOList);
                }else {
                    List<List<ProvinceOverviewDTO>> provinceOverviewDTOLists = new ArrayList<>();
                    List<String> dayList = CustomTimeUtil.getSimDateSignBetween(startDate, endDate, CustomTimeUtil.STD_DATE_FORMATE);
                    List<String> existDayList = provinceOverviewListTemp.stream().map(x -> x.getDateSign()).collect(Collectors.toList());
                    for (int i = 0; i < dayList.size(); i++) {
                        List<ProvinceOverviewDTO> provinceOverviewDTOList = new ArrayList<>();
                        if(existDayList.contains(dayList.get(i))){
                            for (int j = 0; j < provinceOverviewListTemp.size(); j++) {
                                if (dayList.get(i).equals(provinceOverviewListTemp.get(j).getDateSign())) {
                                    List<ProvinceOverviewDTO> provinceOverviewDTOListTemp = JSONArray.parseArray(provinceOverviewListTemp.get(j).getData(), ProvinceOverviewDTO.class);
                                    provinceOverviewDTOList.addAll(provinceOverviewDTOListTemp);
                                    provinceOverviewDTOLists.add(provinceOverviewDTOList);
                                }
                            }
                        }else{
                            provinceOverviewDTOLists.add(provinceOverviewDTOList);
                            continue;
                        }
                    }
                    responseVO.setData(provinceOverviewDTOLists);
                }
                //处理成功
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
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
        List<ProvinceOverview> provinceOverviewList = provinceOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(provinceOverviewList));
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
        ProvinceOverview provinceOverview;
        try {
            provinceOverview = provinceOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(provinceOverview));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 新增实体
     * @param provinceOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
//    @ApiImplicitParam(name = "provinceOverview", value = "对象实体数据",required = true, dataType = "ProvinceOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid ProvinceOverview provinceOverview, BindingResult error) {
         ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                provinceOverviewService.save(provinceOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param provinceOverview 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
//    @ApiImplicitParam(name = "provinceOverview", value = "对象实体数据",required = true, dataType = "ProvinceOverview")
    @PutMapping(value="/{id}")
    public ResponseVO update(@PathVariable Long id,@RequestBody @Valid ProvinceOverview provinceOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                provinceOverview.setId(id);
                provinceOverviewService.update(provinceOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
        ProvinceOverview provinceOverviewTemp;
        try {
            provinceOverviewTemp = provinceOverviewService.findById(id);
            provinceOverviewService.delete(provinceOverviewTemp);
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

