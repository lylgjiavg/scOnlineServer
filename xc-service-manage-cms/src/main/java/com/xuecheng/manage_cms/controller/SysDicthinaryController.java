package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SysDicthinaryController
 * @Description TODO
 * @Date 2019/7/10 10:16
 * @Created by Jiavg
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired
    SysDictionaryService sysDictionaryService;
    
    /**
     * 数据字典查询
     * @param type
     * @return
     */
    @Override
    @GetMapping("/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        
        return sysDictionaryService.getDictionaryByType(type);
    }
    
}
