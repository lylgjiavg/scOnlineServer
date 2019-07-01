package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname ExceptionCatch
 * @Description 异常捕获
 * @Date 2019/6/28 10:24
 * @Created by Jiavg
 */
// 控制器增强
@ControllerAdvice
public class ExceptionCatch {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();
    // 定义map,配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
    
    /**
     * 捕获 CustomException异常 
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        // 写入日志
        LOGGER.error("catch exception : {}\r\nexception: ",customException.getMessage(), customException);
        ResultCode resultCode = customException.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);
        
        return responseResult;
    }
    
    /**
     * 捕获 Exception异常 
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        // 写入日志
        LOGGER.error("catch exception : {}\r\nexception: ",exception.getMessage());
        if(EXCEPTIONS == null){
            // 构建错误异常类型Map
            EXCEPTIONS = builder.build();
        }
        // 从异常错误类型Map中找对应的错误代码
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            // 找到对应错误类型,返回
            return new ResponseResult(resultCode);
        }else {
            // 未找到对应错误类型,返回99999错误代码
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }
    
    
    
}
