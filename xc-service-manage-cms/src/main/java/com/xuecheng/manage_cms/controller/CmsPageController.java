package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CmsPageController
 * @Description TODO
 * @Date 2019/6/22 19:28
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    CmsPageService cmsPageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{pageId}")
    public CmsPageResult findByPageId(@PathVariable("pageId") String pageId) {
        
        return cmsPageService.findByPageId(pageId);
    }

    @Override
    @PutMapping("/update/{pageId}")
    public CmsPageResult updateByPageId(@PathVariable("pageId") String pageId, @RequestBody CmsPage cmsPage) {
        
        return cmsPageService.updateByPageId(pageId, cmsPage);
    }

    @Override
    @DeleteMapping("/delete/{pageId}")
    public ResponseResult deleteByPageId(@PathVariable("pageId") String pageId) {
        
        return cmsPageService.deleteByPageId(pageId);
    }

}
