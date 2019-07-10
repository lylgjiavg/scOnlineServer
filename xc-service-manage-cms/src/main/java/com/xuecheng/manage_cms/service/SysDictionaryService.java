package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname SysDictionaryService
 * @Description TODO
 * @Date 2019/7/10 13:35
 * @Created by Jiavg
 */
@Service
public class SysDictionaryService {

    @Autowired
    SysDicthinaryRepository sysDicthinaryRepository;
    
    /**
     * 数据字典查询
     * @param type
     * @return
     */
    public SysDictionary getDictionaryByType(String type) {

        return sysDicthinaryRepository.findByDType(type);
    }
    
}
