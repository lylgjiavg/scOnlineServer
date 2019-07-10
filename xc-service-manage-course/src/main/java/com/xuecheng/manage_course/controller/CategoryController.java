package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CategoryController
 * @Description TODO
 * @Date 2019/7/10 9:24
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    CourseService courseService;
    
    /**
     * 查询分类
     * @return
     */
    @Override
    @GetMapping("/list")
    @ResponseBody
    public CategoryNode findList() {
        
        return courseService.findCategoryList();
    }
    
}
