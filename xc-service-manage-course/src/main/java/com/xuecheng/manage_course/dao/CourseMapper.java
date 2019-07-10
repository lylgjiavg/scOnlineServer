package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   
   CourseBase findCourseBaseById(String id);

   /**
    * 查询课程列表
    * @return
    */
   List<CourseBase> findCourseBaseList();
   
}
