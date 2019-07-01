package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsSiteRepository
 * @Description 站点Repository
 * @Date 2019/6/26 13:05
 * @Created by Jiavg
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
