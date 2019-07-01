package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsTemplateRepository
 * @Description 模板Repository
 * @Date 2019/6/27 10:22
 * @Created by Jiavg
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {
}
