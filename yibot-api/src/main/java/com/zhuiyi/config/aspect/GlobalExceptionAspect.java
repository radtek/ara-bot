package com.zhuiyi.config.aspect;

import com.alibaba.fastjson.JSONException;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author: tree
 * @version: 1.0
 * date: 2018/6/25 21:28
 * @description:  全局异常处理类
 * own:
 */
@ControllerAdvice
public class GlobalExceptionAspect {

    @Autowired
    private ResCodeProperties resCodeProperties;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseVO handleControllerException(HttpServletRequest request, Throwable ex) {
        ResponseVO responseVO = new ResponseVO();
        //处理请求参数转换错误
        if(ex instanceof JSONException || ex instanceof HttpMessageNotReadableException){
            responseVO.setCode(resCodeProperties.getReqDataCheckFailCode());
            responseVO.setMessage(resCodeProperties.getReqDataCheckFailMsg());
            responseVO.setError(resCodeProperties.getReqDataCheckFailMsg());
        }else if(ex instanceof InternalServiceException){
            responseVO.setCode(resCodeProperties.getOperateFailErrorCode());
            responseVO.setMessage(resCodeProperties.getOperateFailErrorMsg());
            responseVO.setError(ex.getMessage());
        }else if(ex instanceof MissingServletRequestParameterException){
            responseVO.setCode(resCodeProperties.getReqDataCheckFailCode());
            responseVO.setMessage(resCodeProperties.getReqDataCheckFailMsg());
            responseVO.setError(ex.getMessage());
        }else{
            ex.printStackTrace();
            responseVO.setCode(resCodeProperties.getInternalServerErrorCode());
            responseVO.setMessage(resCodeProperties.getInternalServerErrorMsg());
            responseVO.setError(ex.getMessage());
        }
        responseVO.setPath(request.getRequestURI());
        responseVO.setTimestamp(LocalDateTime.now().toString());
        return responseVO;
    }
}
