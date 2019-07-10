package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname SysDicthinaryRepository
 * @Description TODO
 * @Date 2019/7/10 10:28
 * @Created by Jiavg
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary, String> {

    /**
     * 根据字典类型查询对应字典信息
     * @param dType
     * @return
     */
    SysDictionary findByDType(String dType);
    
}
