package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Classname CmsTemplateResult
 * @Description 模板数据响应对象
 * @Date 2019/6/27 10:12
 * @Created by Jiavg
 */
public class CmsTemplateResult extends ResponseResult {
    CmsTemplate cmsTemplate;
    public CmsTemplateResult(ResultCode resultCode, CmsTemplate cmsTemplate) {
        super(resultCode);
        this.cmsTemplate = cmsTemplate;
    }
}
