package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CmsPageControllerApi
 * @Description 页面管理
 * @Date 2019/6/22 18:25
 * @Created by Jiavg
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "queryPageRequest", value = "查询条件", required = false, dataType = "QueryPageRequest")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("添加页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cmsPage", value = "页面信息", required = true, dataType = "CmsPage")
    })
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("根据pageId查询页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageId", value = "页面Id(pageId)", required = true, paramType = "path", dataType = "String")
    })
    CmsPageResult findByPageId(String pageId);

    @ApiOperation("根据pageId修改页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageId", value = "页面Id(pageId)", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cmsPage", value = "页面信息", required = true, dataType = "CmsPage")
    })
    CmsPageResult updateByPageId(String pageId, CmsPage cmsPage);

    @ApiOperation("根据pageId删除页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageId", value = "页面Id(pageId)", required = true, paramType = "path", dataType = "String"),
    })
    ResponseResult deleteByPageId(String pageId);
    
}
