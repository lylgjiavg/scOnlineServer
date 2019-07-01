package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Classname CmsSiteResult
 * @Description TODO
 * @Date 2019/6/27 18:34
 * @Created by Jiavg
 */
public class CmsSiteResult extends ResponseResult {
    CmsSite cmsSite;
    public CmsSiteResult(ResultCode resultCode, CmsSite cmsSite) {
        super(resultCode);
        this.cmsSite = cmsSite;
    }
}
