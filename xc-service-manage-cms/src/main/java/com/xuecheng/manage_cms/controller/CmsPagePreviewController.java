package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @Classname CmsPageController
 * @Description TODO
 * @Date 2019/6/22 19:28
 * @Created by Jiavg
 */
@Controller
public class CmsPagePreviewController extends BaseController {

    @Autowired
    CmsPageService cmsPageService;

    @RequestMapping(value = "/cms/preview/{pageId}", method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId){
        String html = cmsPageService.getHtml(pageId);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(html.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
