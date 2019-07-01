package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsPageRepository
 * @Description 页面Repository
 * @Date 2019/6/22 21:13
 * @Created by Jiavg
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {

}
