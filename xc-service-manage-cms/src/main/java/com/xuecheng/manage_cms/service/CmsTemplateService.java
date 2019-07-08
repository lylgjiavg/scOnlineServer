package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.CmsTemplateAndSite;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    CmsSiteRepository cmsSiteRepository;

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

    /**
     * 根据模板Id查询对应的站点信息
     * @param templateId 模板Id
     * @return  站点信息
     */
    public CmsSiteResult findById(String templateId){
        // 判断参数不为空
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        
        CmsSiteResult cmsSiteResult;
        
        Optional<CmsTemplate> optionalTemplate = cmsTemplateRepository.findById(templateId);
        if (optionalTemplate.isPresent()) {
            CmsTemplate cmsTemplate = optionalTemplate.get();
            // 根据模板查找对应的站点
            if (StringUtils.isNotEmpty(cmsTemplate.getSiteId())) {
                Optional<CmsSite> optionaltSite = cmsSiteRepository.findById(cmsTemplate.getSiteId());
                if (optionaltSite.isPresent()) {
                    CmsSite cmsSite = optionaltSite.get();
                    cmsSiteResult = new CmsSiteResult(CommonCode.SUCCESS, cmsSite);
                    
                    return cmsSiteResult;
                }
            }
        }
        cmsSiteResult = new CmsSiteResult(CommonCode.FAIL, null);
        return cmsSiteResult;
    }

    /**
     * 根据条件查询模板列表和对应的站点信息
     * @param page 页码
     * @param size  每页数据量
     * @param queryPageRequest  查询条件
     * @return 模板和对应的站点信息
     */
    public QueryResponseResult findTemplateAndSiteList(int page, int size, QueryPageRequest queryPageRequest){
        // 非法判断
        if(page <= 0){
            page = 1;
        }
        page -= 1;
        if(size <= 0){
            size = 10;
        }
        // 分页条件
        Pageable pageable = PageRequest.of(page, size);

        CmsTemplate cmsTemplateQuery = new CmsTemplate();
        // 添加站点Id查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsTemplateQuery.setSiteId(queryPageRequest.getSiteId());
        }
        // 添加模板名称查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateName())){
            cmsTemplateQuery.setTemplateName(queryPageRequest.getTemplateName());
        }
        
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());
        
        // 查询条件
        Example<CmsTemplate> example = Example.of(cmsTemplateQuery, exampleMatcher);

        QueryResponseResult queryResponseResult;

        Page<CmsTemplate> all = cmsTemplateRepository.findAll(example, pageable);
        List<CmsTemplate> cmsTemplateList = all.getContent();
        List<CmsTemplateAndSite> cmsTemplateAndSiteList = new ArrayList<>();
        for (CmsTemplate cmsTemplate: cmsTemplateList) {
            CmsTemplateAndSite cmsTemplateAndSite = new CmsTemplateAndSite();
            
            // 拷贝
            cmsTemplateAndSite.setTemplateId(cmsTemplate.getTemplateId());
            cmsTemplateAndSite.setSiteId(cmsTemplate.getSiteId());
            cmsTemplateAndSite.setTemplateFileId(cmsTemplate.getTemplateFileId());
            cmsTemplateAndSite.setTemplateName(cmsTemplate.getTemplateName());
            cmsTemplateAndSite.setTemplateParameter(cmsTemplate.getTemplateParameter());
            
            // 根据模板查找对应的站点
            CmsSiteResult cmsSiteResult = findById(cmsTemplate.getTemplateId());
            cmsTemplateAndSite.setSiteName(cmsSiteResult.getCmsSite().getSiteName());

            cmsTemplateAndSiteList.add(cmsTemplateAndSite);
        }
        QueryResult<CmsTemplateAndSite> queryResult = new QueryResult<>();
        queryResult.setList(cmsTemplateAndSiteList);

        queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        
        return queryResponseResult;
    }
    
}
