package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Classname CustomException
 * @Description 自定义异常
 * @Date 2019/6/28 10:14
 * @Created by Jiavg
 */
public class CustomException extends RuntimeException {
    // 异常代码
    private ResultCode resultCode;

    public CustomException(final ResultCode resultCode) {
        //异常信息为错误代码+异常信息
        super("错误代码：" + resultCode.code() + "错误信息：" + resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }
}
