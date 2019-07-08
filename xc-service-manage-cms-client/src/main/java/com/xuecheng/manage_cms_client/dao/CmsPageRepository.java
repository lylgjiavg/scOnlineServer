package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsPageRepository
 * @Description TODO
 * @Date 2019/7/8 15:46
 * @Created by Jiavg
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
}
