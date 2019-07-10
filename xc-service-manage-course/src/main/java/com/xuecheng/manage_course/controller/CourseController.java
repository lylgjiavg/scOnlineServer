package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CourseController
 * @Description TODO
 * @Date 2019/7/9 10:40
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    /**
     * 查询课程计划
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        
        return courseService.findTeachplanList(courseId);
    }
    
    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        
        return courseService.addTeachplan(teachplan);
    }

    /**
     * 查询课程列表
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            CourseListRequest courseListRequest
    ) {
        
        return courseService.findCourseList(page, size, courseListRequest);
    }

    /**
     * 添加课程基础信息
     * @param courseBase
     * @return
     */
    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        
        return courseService.addCourseBase(courseBase);
    }

    /**
     * 获取课程基础信息
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        
        return courseService.getCourseBaseById(courseId);
    }

    /**
     * 更新课程基础信息
     * @param courseId
     * @param courseBase
     * @return
     */
    @Override
    @PutMapping("/coursebase/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId,@RequestBody CourseBase courseBase) {
        
        return courseService.updateCourseBase(courseId, courseBase);
    }

    /**
     * 获取课程营销信息
     * @param id
     * @return
     */
    @Override
    @GetMapping("/market/get/{id}")
    public CourseMarket getCourseMarketById(@PathVariable("id") String id) {
        
        return courseService.getCourseMarketById(id);
    }

    /**
     * 更新课程营销信息
     * @param id
     * @param courseMarket
     * @return
     */
    @Override
    @PutMapping("/market/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id,@RequestBody CourseMarket courseMarket) {
        
        return courseService.updateCourseMarket(id, courseMarket);
    }
    
}
