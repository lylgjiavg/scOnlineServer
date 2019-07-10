package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Classname CoursePicRepository
 * @Description TODO
 * @Date 2019/7/10 8:31
 * @Created by Jiavg
 */
public interface CoursePicRepository extends JpaRepository<CoursePic, String> {

    /**
     * 根据课程Id查询课程图片信息
     * @param courseId
     * @return
     */
    List<CoursePic> findByCourseid(String courseId);
    
}
