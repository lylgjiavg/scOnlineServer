package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CmsSiteControllerApi
 * @Description TODO
 * @Date 2019/6/27 19:01
 * @Created by Jiavg
 */
@Api(value = "cms站点管理接口", description = "cms站点管理接口，提供站点的增、删、改、查")
public interface CmsSiteControllerApi {

    @ApiOperation("查询所有站点信息")
    QueryResponseResult findAllList();
    
}
