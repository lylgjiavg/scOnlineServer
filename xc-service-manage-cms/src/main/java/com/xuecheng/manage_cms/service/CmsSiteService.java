package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CmsSiteService
 * @Description TODO
 * @Date 2019/6/27 12:08
 * @Created by Jiavg
 */
@Service
public class CmsSiteService {
    
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     * 查询所有站点信息
     * @return
     */
    public QueryResponseResult findAllList(){

        List<CmsSite> all = cmsSiteRepository.findAll();

        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setList(all);
        queryResult.setTotal(all.size());
        
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        
        return queryResponseResult;
    }
    
}
