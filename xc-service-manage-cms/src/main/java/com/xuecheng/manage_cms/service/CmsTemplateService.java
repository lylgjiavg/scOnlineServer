package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CmsTemplateService
 * @Description TODO
 * @Date 2019/6/27 12:30
 * @Created by Jiavg
 */
@Service
public class CmsTemplateService {

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    public QueryResponseResult findAllList(QueryPageRequest queryPageRequest){

        QueryResponseResult queryResponseResult;

        if(queryPageRequest.getSiteId() != null){
            CmsTemplate cmsTemplate = new CmsTemplate();
            cmsTemplate.setSiteId(queryPageRequest.getSiteId());

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("siteId", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<CmsTemplate> example = Example.of(cmsTemplate, exampleMatcher);
            List<CmsTemplate> cmsTemplateList = cmsTemplateRepository.findAll(example);

            QueryResult<CmsTemplate> queryResult = new QueryResult<>();
            queryResult.setList(cmsTemplateList);
            queryResult.setTotal(cmsTemplateList.size());

            queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        }else {
            List<CmsTemplate> cmsTemplateList = cmsTemplateRepository.findAll();

            QueryResult<CmsTemplate> queryResult = new QueryResult<>();
            queryResult.setList(cmsTemplateList);
            queryResult.setTotal(cmsTemplateList.size());
            
            queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        }

        return queryResponseResult;
    }
    
}
