package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 根据模板Id查询对应的站点信息
     * @param templateId 模板Id
     * @return  站点信息
     */
    @Override
    @GetMapping("/getSite/{templateId}")
    public CmsSiteResult findById(@PathVariable("templateId") String templateId) {
        
        return cmsTemplateService.findById(templateId);
    }

    /**
     * 根据条件查询模板列表和对应的站点信息
     * @param page 页码
     * @param size  每页数据量
     * @param queryPageRequest  查询条件
     * @return 模板和对应的站点信息 
     */
    @Override
    @GetMapping("/templateAndSite/list/{page}/{size}")
    public QueryResponseResult findTemplateAndSiteList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        
        return cmsTemplateService.findTemplateAndSiteList(page, size ,queryPageRequest);
    }

}
