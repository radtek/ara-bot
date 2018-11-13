package com.zhuiyi.api.v1;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.DialogBack;
import com.zhuiyi.model.vo.ResponseVO;
import com.zhuiyi.service.DialogBackService;
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
@RequestMapping("/yibot/v1/api/dialogback")
@Api(value = "数据统计服务API", description = "数据统计相关接口定义")
@Slf4j
public class DialogBackApiController {

    private DialogBackService dialogBackService;
    private ResCodeProperties resCodeProperties;

    @Autowired
    public DialogBackApiController(DialogBackService dialogBackService, ResCodeProperties resCodeProperties){
        this.dialogBackService = dialogBackService;
        this.resCodeProperties = resCodeProperties;
    }

    /**
     * 查询实体列表
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "获取数据列表", notes = "获取查询对象的数据列表")
    @GetMapping
    public ResponseVO queryList(){
        ResponseVO responseVO = new ResponseVO();
        List<DialogBack> dialogBackList = dialogBackService.findAll();
        responseVO.setCode(resCodeProperties.getSuccessCode());
        responseVO.setMessage(resCodeProperties.getSuccessMsg());
        responseVO.setData(JSONObject.toJSONString(dialogBackList));
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
        DialogBack dialogBack;
        try {
            dialogBack = dialogBackService.findById(id);
            responseVO.setCode(resCodeProperties.getSuccessCode());
            responseVO.setMessage(resCodeProperties.getSuccessMsg());
            responseVO.setData(JSONObject.toJSONString(dialogBack));
        } catch (InternalServiceException e) {
            responseVO.setCode(resCodeProperties.getReqContentNotExistCode());
            responseVO.setMessage(resCodeProperties.getReqContentNotExistMsg());
            responseVO.setError(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 新增实体
     * @param dialogBack 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "添加对象数据", notes = "添加对象数据到数据表中")
    @ApiImplicitParam(name = "dialogBack", value = "对象实体数据",required = true, dataType = "DialogBack")
    @PostMapping
    public ResponseVO create(@RequestBody @Valid DialogBack dialogBack, BindingResult error) {
         ResponseVO responseVO = new ResponseVO();
         //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                dialogBackService.save(dialogBack, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
     * @param dialogBack 实体对象
     * @param error 实体对象数据检查中的错误信息
     * @return ResponseVO 响应数据
     */
    @ApiOperation(value = "更新对象数据", notes = "更新对象数据到数据表中")
    @ApiImplicitParam(name = "dialogBack", value = "对象实体数据",required = true, dataType = "DialogBack")
    @PutMapping(value="/{id}")
    public ResponseVO update(@PathVariable Long id,@RequestBody @Valid DialogBack dialogBack, BindingResult error) {
        ResponseVO responseVO = new ResponseVO();
        //对实体对象数据进行检查
        if(!error.hasFieldErrors()){
            try {
                dialogBack.setId(id);
                dialogBackService.update(dialogBack, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
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
        DialogBack dialogBackTemp;
        try {
            dialogBackTemp = dialogBackService.findById(id);
            dialogBackService.delete(dialogBackTemp);
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

