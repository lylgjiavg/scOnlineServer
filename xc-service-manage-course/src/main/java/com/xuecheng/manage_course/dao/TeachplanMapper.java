package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname TeachplanMapper
 * @Description 
 * @Date 2019/7/9 9:03
 * @Created by Jiavg
 */
@Mapper
public interface TeachplanMapper {

    /**
     * 根据课程Id查询课程节点树数据
     * @param courseId
     * @return
     */
    TeachplanNode selectList(String courseId);
    
}
