package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Classname ExceptionCast
 * @Description 异常抛出
 * @Date 2019/6/28 10:22
 * @Created by Jiavg
 */
public class ExceptionCast {

    // 使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    } 
    
}
