package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @Classname CmsPageRepositoryTest
 * @Description TODO
 * @Date 2019/6/22 21:15
 * @Created by Jiavg
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    

    /**
     * 按照条件查询
     */
    @Test
    public void testFindAllByExample(){

        // 创建Example需要的条件
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ebb");
        // 创建Example的匹配规则
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        // 创建Example条件
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        // 创建分页查询条件
        Pageable pageable = PageRequest.of(0, 10);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        List<CmsPage> content = all.getContent();
        System.out.printf(content.toString());
    }


    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {

        List<CmsPage> list = cmsPageRepository.findAll();
        System.out.printf(list.toString());
    }

    /**
     * 分页查询
     */
    @Test
    public void testFindPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CmsPage> list = cmsPageRepository.findAll(pageable);
        System.out.printf(list.getContent().toString());
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        // 查询
        Optional<CmsPage> optional = cmsPageRepository.findById("5d0e520f40a9925b94a61f4e");
        if(optional.isPresent()){
            // 修改
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("Jiang");
            // 保存修改
            cmsPageRepository.save(cmsPage);
            System.out.printf(cmsPage.toString());
        }
    }

    /**
     * 根据别名查询
     */
    @Test
    public void testPageAliase(){

        CmsPage cmsPages = cmsPageRepository.findByPageAliase("Jiang");

        System.out.printf(cmsPages.toString());

    }

}
