package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CmsSiteController
 * @Description TODO
 * @Date 2019/6/27 19:03
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {
    
    @Autowired
    CmsSiteService cmsSiteService;
    
    @Override
    @GetMapping("/list")
    public QueryResponseResult findAllList() {
        
        return cmsSiteService.findAllList();
    }
    
}
