package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @Classname PageService
 * @Description TODO
 * @Date 2019/6/23 23:22
 * @Created by Jiavg
 */
@Service
public class CmsConfigService {

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    /**
     * 根据id查询静态化模板数据
     * @param id
     * @return
     */
    public CmsConfig findById(String id){

        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);

        if (optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        
        return null;
    }
    
}
