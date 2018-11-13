package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ZrgOverview;
import com.zhuiyi.model.dto.ZrgOverviewDTO;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.ZrgOverviewService;
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

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@RestController
@RequestMapping("/yibot/v1/api/zrgoverview")
@Api(value = "转人工数据统计服务API", description = "转人工数据统计相关接口定义")
@Slf4j
public class ZrgOverviewApiController {

    private ZrgOverviewService zrgOverviewService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public ZrgOverviewApiController(ZrgOverviewService zrgOverviewService, ResCodeProperties resCodeProperties) {
        this.zrgOverviewService = zrgOverviewService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 根据条件查询转人工统计分析数据
     *
     * @param bId       业务标识
     * @param dateRange 查询的时间区间
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取转人工统计分析数据", notes = "根据条件查询转人工统计分析数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bId", value = "业务标识", required = true, dataType = "String", paramType = "query", defaultValue = "11"),
            @ApiImplicitParam(name = "dateRange", value = "查询的时间区间", dataType = "String", paramType = "query", defaultValue = "20180808-20180820")
    })
    @GetMapping("/query")
    public ResponseVO queryByDateAndChanel(@NotNull String bId, @NotNull String dateRange) {
        ResponseVO responseVO = new ResponseVO();
        String[] dateArray = dateRange.split("-");
        String startDate = CustomTimeUtil.changeDateFormate(dateArray[0]);
        String endDate = CustomTimeUtil.changeDateFormate(dateArray[1]);
        try {
            //对前端传来的bId进行解析
            bId = CustomObjectUtil.solveBid(bId);
            //根据条件查询数据
            List<ZrgOverview> zrgOverviewList = zrgOverviewService.findByAppidAndDateSignBetween(bId, startDate, endDate);
            //返回结果给调用方
            if (null != zrgOverviewList && zrgOverviewList.size() > 0) {
                List<ZrgOverviewDTO> objectList = new ArrayList<>();
                //处理提问总量数组，转人工率数组，数据数组
                zrgOverviewList.forEach(x -> objectList.add(JSONObject.parseObject(x.getData(), ZrgOverviewDTO.class)));
                //处理成功
                responseVO.setCode(resCodeProperties.getSuccessCode());
                responseVO.setMessage(resCodeProperties.getSuccessMsg());
                responseVO.setData(objectList);
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
        List<ZrgOverview> zrgOverviewList = zrgOverviewService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(zrgOverviewList));
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
        ZrgOverview zrgOverview;
        try {
            zrgOverview = zrgOverviewService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(zrgOverview));
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
     * @param zrgOverview 实体对象
     * @param error       实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
//    @ApiImplicitParam(name = "zrgOverview", value = "对象实体数据",required = true, dataType = "ZrgOverview")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid ZrgOverview zrgOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                zrgOverviewService.save(zrgOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param zrgOverview 实体对象
     * @param error       实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
//    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
//    @ApiImplicitParam(name = "zrgOverview", value = "对象实体数据",required = true, dataType = "ZrgOverview")
    @PutMapping(value = "/{id}")
    public ResponseVO update(@PathVariable Long id, @RequestBody @Valid ZrgOverview zrgOverview, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if (!error.hasFieldErrors()) {
            try {
                zrgOverview.setId(id);
                zrgOverviewService.update(zrgOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
        ZrgOverview zrgOverviewTemp;
        try {
            zrgOverviewTemp = zrgOverviewService.findById(id);
            zrgOverviewService.delete(zrgOverviewTemp);
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

