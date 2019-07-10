package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Classname CategoryControllerApi
 * @Description TODO
 * @Date 2019/7/10 9:15
 * @Created by Jiavg
 */
@Api(value = "课程分类管理",description = "课程分类管理")
public interface CategoryControllerApi {

    @ApiOperation("查询分类")
    public CategoryNode findList();
    
}
