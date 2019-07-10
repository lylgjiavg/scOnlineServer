package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname CategoryMapper
 * @Description TODO
 * @Date 2019/7/10 9:44
 * @Created by Jiavg
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询课程分类
     * @return
     */
    CategoryNode findList(); 
    
}
