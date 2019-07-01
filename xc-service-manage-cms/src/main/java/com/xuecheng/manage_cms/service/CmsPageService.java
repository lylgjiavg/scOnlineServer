package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @Classname PageService
 * @Description TODO
 * @Date 2019/6/23 23:22
 * @Created by Jiavg
 */
@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 根据页面Id删除页面信息
     * @param pageId    页面Id
     * @return
     */
    public ResponseResult deleteByPageId(String pageId){
        
        ResponseResult responseResult;
        
        if(pageId != null){

            Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
            if (optional.isPresent()) {
                CmsPage cmsPage = optional.get();
                if (cmsPage != null) {
                    cmsPageRepository.deleteById(pageId);
                    responseResult = new ResponseResult(CommonCode.SUCCESS);
                    return responseResult;
                }
            }

        }
        responseResult = new ResponseResult(CommonCode.FAIL);
        
        return responseResult;
    }
    
    /**
     * 查询页面信息
     *
     * @param page             查询页码,第一页为1(不为0)
     * @param size             每页数据量
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        // 判断非空,如为空,初始化,防止后面出现空指针异常
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        // 判断是否合法
        if (page <= 0) {
            page = 1;
        }
        page -= 1;
        if (size <= 0) {
            size = 10;
        }

        // 创建Example条件, 根据站点Id,模板Id,页面别名查询
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        // 定义pageAliase为模糊匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);


        // 创建查询条件
        Pageable pageRequest = PageRequest.of(page, size);
        // 根据查询条件查询页面信息
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);

        // 创建查询结果对象,并对其数据进行赋值
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        // 创建查询相应对象
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }

    /**
     * 查询所有页面信息（根据站点Id查询所有站点名称）
     *
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findAllList(QueryPageRequest queryPageRequest) {
        // 非空验证
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        // 定义查询条件需要的查询信息
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        // 定义查询条件
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("siteId", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        // 根据查询条件查询页面信息
        List<CmsPage> all = cmsPageRepository.findAll(example);

        // 把页面信息的站点Id放入Set集合(去重)
        Set<String> cmsPageSet = new HashSet<>();
        for (CmsPage msPage : all) {
            cmsPageSet.add(msPage.getSiteId());
        }

        // 循环遍历站点Id,获得站点信息
        List<CmsSite> cmsSiteList = new ArrayList<>();
        for (String msPage : cmsPageSet) {
            Optional<CmsSite> cmsSite = cmsSiteRepository.findById(msPage);
            if (cmsSite.isPresent()) {
                cmsSiteList.add(cmsSite.get());
            }
        }

        // 组合站点信息,放入响应中返回出去
        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setList(cmsSiteList);
        queryResult.setTotal(cmsSiteList.size());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }

    /**
     * 新增页面信息
     *
     * @param cmsPage 页面信息
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage) {
        // 声明响应返回
        CmsPageResult cmsPageResult;
        
        // 参数非空验证
        if(cmsPage == null){
            
            // ExceptionCast.cast();
        }
        
        // 查询数据库中是否已经存在要添加的页面信息
        CmsPage mongoCmsPage = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(
                cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath()
        );
        // 判断页面信息
        if (mongoCmsPage != null) {
            // 数据库中已存在要添加的页面,抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        
        // 数据库中不存在要添加的页面,添加页面信息
        cmsPage.setPageId(null);
        CmsPage pageSave = cmsPageRepository.save(cmsPage);

        cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, pageSave);

        return cmsPageResult;
    }

    /**
     * 根据页面Id查询页面信息
     *
     * @param pageId 页面Id
     * @return
     */
    public CmsPageResult findByPageId(String pageId) {

        CmsPageResult cmsPageResult;

        if (pageId != null) {
            Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
            if (optionalCmsPage.isPresent()) {
                CmsPage cmsPage = optionalCmsPage.get();
                cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);

                return cmsPageResult;
            }
        }
        cmsPageResult = new CmsPageResult(CommonCode.FAIL, null);

        return cmsPageResult;
    }

    /**
     * 根据页面Id修改页面信息
     *
     * @param pageId  页面Id
     * @param cmsPage 页面信息
     * @return
     */
    public CmsPageResult updateByPageId(String pageId, CmsPage cmsPage) {

        CmsPageResult cmsPageResult;
        
        if (pageId != null) {
            Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
            if (optional.isPresent()) {
                CmsPage cmsPageUpdate = optional.get();
                // 更新模板id
                cmsPageUpdate.setTemplateId(cmsPage.getTemplateId());
                // 更新所属站点             
                cmsPageUpdate.setSiteId(cmsPage.getSiteId());
                // 更新页面别名             
                cmsPageUpdate.setPageAliase(cmsPage.getPageAliase());
                // 更新页面名称             
                cmsPageUpdate.setPageName(cmsPage.getPageName());
                // 更新访问路径             
                cmsPageUpdate.setPageWebPath(cmsPage.getPageWebPath());
                // 更新物理路径             
                cmsPageUpdate.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
                // 更新数据URL
                cmsPageUpdate.setDataUrl(cmsPage.getDataUrl());
                
                // 执行更新
                cmsPageRepository.save(cmsPageUpdate);

                cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPageUpdate);
                return cmsPageResult;
            }
        }

        cmsPageResult = new CmsPageResult(CommonCode.FAIL, null);
        return cmsPageResult;
    }

    /**
     * 页面静态化
     * @param pageId    页面Id
     * @return
     */
    public String getHtml(String pageId){
        
        // 获取DataURL,根据DataURL获得模型数据
        Map model = getModelByPageId(pageId);
        if(model == null){
            // 根据页面的数据url获取不到数据
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        
        // 获得模板信息
        String template = getTemplate(pageId);
        if(template == null){
            // 根据页面的数据url获取不到数据
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        // 执行静态化
        String staticHtml = geneteHtml(template, model);
        if(StringUtils.isEmpty(staticHtml)){
            // 生成的静态html为空！
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        
        return staticHtml;
    }

    /**
     * 根据模板和模板数据生成静态化页面
     * @param templateContent
     * @param map
     * @return
     */
    private String geneteHtml(String templateContent, Map map){

        Configuration configuration = new Configuration(Configuration.getVersion());

        // 定义模板加载器,加载模板字符串
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);
        // 配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);

        try {
            // 获得模板
            Template template = configuration.getTemplate("template", "utf-8");
            // 根据模板和模型数据生成静态化页面
            String templateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            return templateIntoString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 根据页面Id查询对应的模板
     * @param pageId
     * @return
     */
    private String getTemplate(String pageId){
        // 非空验证
        if(pageId == null){
            // 预览页面的页面id为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_PAGEIDISNULL);
        }

        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            // 获得模板Id
            String templateId = cmsPage.getTemplateId();
            if(StringUtils.isNotEmpty(templateId)){
                // 获取模板对应的GridFS的文件Id
                Optional<CmsTemplate> templateOptional = cmsTemplateRepository.findById(templateId);
                if(templateOptional.isPresent()){
                    CmsTemplate cmsTemplate = templateOptional.get();
                    String templateFileId = cmsTemplate.getTemplateFileId();
                    // 取出模板文件
                    GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
                    GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
                    GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
                    InputStream inputStream = null;
                    try {
                        inputStream = gridFsResource.getInputStream();
                        String content = IOUtils.toString(inputStream, "utf-8");
                        
                        return  content;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // TODO 模板Id为空,抛出异常
        }
        return null;
    }

    /**
     * 根据页面Id获得模型数据
     * @param pageId
     * @return
     */
    private Map getModelByPageId(String pageId){
        // 非空验证
        if(pageId == null){
            // 预览页面的页面id为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_PAGEIDISNULL);
        }
        
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            String dataUrl = cmsPage.getDataUrl();
            // 获取模型数据
            ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
            Map body = entity.getBody();
            
            return body;
        }
        
        // 根据页面id找不到对应的页面信息
        return null;
    }
    
}
