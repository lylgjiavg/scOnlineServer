package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsPageRepository
 * @Description 页面Repository
 * @Date 2019/6/22 21:13
 * @Created by Jiavg
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

    /**
     * 根据别名查找
     * @param pageAliase
     * @return
     */
    CmsPage findByPageAliase(String pageAliase);

    /**
     * 根据站点Id,页面名称,页面路径(唯一索引)查询页面信息
     * @param siteId    站点Id
     * @param pageName  页面名称
     * @param pageWebPath   页面路径
     * @return
     */
    CmsPage findBySiteIdAndPageNameAndPageWebPath(String siteId, String pageName, String pageWebPath);

}
