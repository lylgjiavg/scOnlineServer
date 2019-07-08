package com.xuecheng.api.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname FsFilesControllerApi
 * @Description MongoDB文件上传
 * @Date 2019/7/4 8:41
 * @Created by Jiavg
 */
/*TODO 文件上传*/
@Api(value="MongoDB文件管理接口",description="MongoDB文件管理接口，提供文件的增、删、改、查")
public interface FsFilesControllerApi {

    @ApiOperation("添加模板文件到MongoDB")
    @ApiImplicitParams({
            @ApiImplicitParam(name="queryPageRequest",value="查询条件",required=false,dataType="QueryPageRequest")
    })
    String insertTemplate();
    
}
