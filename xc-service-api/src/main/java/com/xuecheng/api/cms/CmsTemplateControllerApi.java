package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CmsTemplateControllerApi
 * @Description 模板管理
 * @Date 2019/6/27 10:07
 * @Created by Jiavg
 */
@Api(value="cms模板管理接口",description="cms模板管理接口，提供模板的增、删、改、查")
public interface CmsTemplateControllerApi {

    @ApiOperation("查询所有模板信息（根据站点Id查询所有模板名称）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="queryPageRequest",value="查询条件",required=false,dataType="QueryPageRequest")
    })
    QueryResponseResult findAllList(QueryPageRequest queryPageRequest);

    @ApiOperation("根据模板Id查询对应的站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="templateId",value="模板Id", paramType = "path",required=false,dataType="String")
    })
    CmsSiteResult findById(String templateId);

    @ApiOperation("根据条件查询模板列表和对应的站点信息(分页查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name="queryPageRequest",value="查询条件",required=false,dataType="QueryPageRequest")
    })
    QueryResponseResult findTemplateAndSiteList(int page, int size,QueryPageRequest queryPageRequest);
    
}
