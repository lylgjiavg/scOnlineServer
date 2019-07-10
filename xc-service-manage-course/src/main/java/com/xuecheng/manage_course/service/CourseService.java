package com.xuecheng.manage_course.service;

import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Classname CourseService
 * @Description TODO
 * @Date 2019/7/9 10:38
 * @Created by Jiavg
 */
@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CourseMarketRepository courseMarketRepository;

    /**
     * 根据课程Id查询课程节点树数据
     * @param courseId
     * @return
     */
    public TeachplanNode findTeachplanList(String courseId){
        
        return teachplanMapper.selectList(courseId);
    }

    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan){
        // 非法判断(课程Id,课程名称,课程描述),必填字段
        if(StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname()) ||
                StringUtils.isEmpty(teachplan.getDescription())
        ){
            return new ResponseResult(CommonCode.FAIL);
        }
        
        // 获得课程Id
        String courseid = teachplan.getCourseid();
        // 获得课程的父节点
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            // 如果课程父节点为空,则该节点的父节点为根节点
            parentid = getTeachplanRoot(courseid);
        }
        
        // 获得父节点的层级信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        String parentPrade = optional.get().getGrade();

        Teachplan teachplanNew = new Teachplan();
        // 保存课程信息
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setParentid(parentid);
        if(parentPrade.equals("1")){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }
        
        // 保存节点信息
        teachplanRepository.save(teachplanNew);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程根结点，如果没有则添加根结点
     * @param courseId
     * @return
     */
    public String getTeachplanRoot(String courseId){
        // 非法判断(查询课程是否存在)
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        // 获得课程信息
        CourseBase courseBase = optional.get();

        // 查询课程的根节点
        List<Teachplan> teachplans = teachplanRepository.findByCourseidAndParentid(courseId, "0");

        // 如果没有根节点,添加根节点信息
        if(teachplans == null || teachplans.size() == 0){
            Teachplan teachplanNew = new Teachplan();
            teachplanNew.setCourseid(courseBase.getId());
            teachplanNew.setPname(courseBase.getName());
            teachplanNew.setParentid("0");
            teachplanNew.setGrade("1");
            teachplanNew.setDescription(courseBase.getDescription());
            teachplanNew.setOrderby(1);
            teachplanNew.setStatus("0");
            
            // 保存新建节点信息
            teachplanRepository.save(teachplanNew);
            
            return teachplanNew.getId();
        }

        return teachplans.get(0).getId();
    }

    /**
     * 查询课程列表
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {
        // 非法判断
        if(page < 0){
            page = 0;
        }
        if(size <= 0){
            size = 7;
        }
        
        List<CourseInfo> courseInfoList = new ArrayList<>();
        
        // page和size被保存在ThreadLocal中,由PageHelper在Dao层进行拦截注入
        PageHelper.startPage(page, size);

        List<CourseBase> courseBaseList = courseMapper.findCourseBaseList();
        
        // 遍历课程信息,并查询相应的图片信息,组合课程信息并返回
        for (CourseBase courseBase: courseBaseList) {
            CourseInfo courseInfo = new CourseInfo();
            // 组合课程基本信息
            BeanUtils.copyProperties(courseBase, courseInfo);

            // 组合课程图片信息
            String courseBaseId = courseBase.getId();
            List<CoursePic> coursePics = coursePicRepository.findByCourseid(courseBaseId);
            if(coursePics != null && coursePics.size() != 0){
                courseInfo.setPic(coursePics.get(0).getPic());
            }
            
            courseInfoList.add(courseInfo);
        }

        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(courseInfoList);
        
        // 获得总记录数
        long count = courseBaseRepository.count();
        queryResult.setTotal(count);
        
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        
        return queryResponseResult;
    }

    /**
     * 查询课程分类
     * @return
     */
    public CategoryNode findCategoryList(){

        return categoryMapper.findList();
    }

    /**
     * 添加课程
     * @param courseBase
     * @return
     */
    @Transactional
    public ResponseResult addCourseBase(CourseBase courseBase) {
        // 非法判断
        if(courseBase == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 添加课程数据到数据库
        courseBaseRepository.save(courseBase);
        
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        
        return responseResult;
    }

    /**
     * 获取课程基础信息
     * @param courseId
     * @return
     */
    public CourseBase getCourseBaseById(String courseId) {
        // 非法判断
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }

        return optional.get();
    }

    /**
     * 更新课程基础信息
     * @param courseId
     * @param courseBase
     * @return
     */
    @Transactional
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {
        // 非法判断
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 查询课程基础信息是否已存在
        CourseBase courseBaseOld = this.getCourseBaseById(courseId);
        if (courseBaseOld == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 设置课程Id,更新数据信息
        courseBase.setId(courseId);
        courseBaseRepository.save(courseBase);
        
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        
        return responseResult;
    }

    /**
     * 获取课程营销信息
     * @param courseId
     * @return
     */
    public CourseMarket getCourseMarketById(String courseId) {
        // 判断此课程是否存在
        CourseBase courseBase = this.getCourseBaseById(courseId);
        if (courseBase == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);

        return optional.get();
    }

    /**
     * 更新课程营销信息
     * @param id
     * @param courseMarket
     * @return
     */
    @Transactional
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        // 非法判断
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Optional<CourseMarket> optional = courseMarketRepository.findById(id);
        if (optional.isPresent()) {
            CourseMarket courseMarketOld = optional.get();
            courseMarket.setPrice_old(courseMarketOld.getPrice());
        }

        // 设置id数据,更新课程营销信息
        courseMarket.setId(id);
        
        courseMarketRepository.save(courseMarket);
        
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        
        return responseResult;
    }
    
}
