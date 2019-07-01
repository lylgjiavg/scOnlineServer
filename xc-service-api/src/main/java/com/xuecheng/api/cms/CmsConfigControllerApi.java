package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CmsConfigControllerApi
 * @Description TODO
 * @Date 2019/6/29 12:57
 * @Created by Jiavg
 */
@Api(value = "cms静态化模板数据管理接口", description = "cms静态化模板数据管理接口，提供静态化模板数据的增、删、改、查")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询静态化模板数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataType = "String"),
    })
    CmsConfig findById(String id);
    
}
