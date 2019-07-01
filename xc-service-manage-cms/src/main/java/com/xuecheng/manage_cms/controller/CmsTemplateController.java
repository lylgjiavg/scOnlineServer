package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CmsTemplateController
 * @Description TODO
 * @Date 2019/6/27 10:20
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
    
    @Autowired
    CmsTemplateService cmsTemplateService;
    
    @Override
    @GetMapping("/list")
    public QueryResponseResult findAllList(QueryPageRequest queryPageRequest) {
        
        return cmsTemplateService.findAllList(queryPageRequest);
    }
    
}
