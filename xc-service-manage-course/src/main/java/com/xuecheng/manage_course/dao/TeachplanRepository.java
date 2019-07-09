package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Classname TeachplanRepository
 * @Description TODO
 * @Date 2019/7/9 14:15
 * @Created by Jiavg
 */
public interface TeachplanRepository extends JpaRepository<Teachplan, String> {
    
    // 根据课程Id和父节点Id查询课程信息
    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
    
}
