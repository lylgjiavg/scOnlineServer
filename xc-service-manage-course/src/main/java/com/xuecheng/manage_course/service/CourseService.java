package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    CourseBaseRepository courseBaseRepository;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;

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
}
